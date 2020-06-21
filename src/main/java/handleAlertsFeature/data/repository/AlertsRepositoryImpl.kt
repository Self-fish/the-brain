package handleAlertsFeature.data.repository

import application.logger.LoggerWrapper
import application.network.Either
import handleAlertsFeature.data.action.AlertsAction
import handleAlertsFeature.data.datasource.AlertsNetDataSource
import handleAlertsFeature.data.mapper.AlertsDataModelMapper
import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.`model `.AlertDate
import handleAlertsFeature.domain.contracts.AlertsRepository
import org.koin.standalone.KoinComponent
import java.time.LocalDateTime

class AlertsRepositoryImpl(private val netDataSource: AlertsNetDataSource,
                           private val action: AlertsAction,
                           private val mapper: AlertsDataModelMapper,
                           private val logger: LoggerWrapper) : AlertsRepository, KoinComponent {

    override fun getNextAlert(): Alert? {
        return if (getOrderAlerts().isEmpty()) {
            logger.fine(this::class.simpleName, "There are not alerts")
            null
        }
        else {
            val nearestAlert = getOrderAlerts().first()
            logger.fine(this::class.simpleName, "The nearest alert is ${nearestAlert.id}")
            nearestAlert
        }
    }

    private fun getOrderAlerts(): List<Alert> {
        val aux = Comparator<Alert> { a, b ->
            val today = AlertDate(LocalDateTime.now().dayOfWeek.value, LocalDateTime.now().hour,
                    LocalDateTime.now().minute)
            when {
                a.nextNotification == today -> -1
                b.nextNotification == today -> 1
                a.nextNotification < today && b.nextNotification >= today -> 1
                b.nextNotification < today && a.nextNotification >= today -> -1
                a > b -> 1
                a < b -> -1
                else -> 0
            }
        }

        return when(val getAllAlertResponse = netDataSource.getAllAlerts()) {
            is Either.Left -> listOf()
            is Either.Right-> mapper.mapToAlertList(getAllAlertResponse.right).sortedWith(aux)
        }
    }

    override fun sendAlert(alert: Alert): Boolean {
        return when {
            action.sendAlert(alert) == AlertsAction.AlertActionResponse.OK -> {
                logger.fine(this::class.simpleName, "The alert ${alert.id} was received by the-body")
                when(netDataSource.executeAlert(alert.id)) {
                    is Either.Left -> {
                        logger.warning(this::class.simpleName, "There were an error when executing the alert " +
                                alert.id)
                        false
                    }
                    is Either.Right -> {
                        logger.fine(this::class.simpleName, "The alert ${alert.id} was executed correctly")
                        true
                    }
                }
            } else -> {
                logger.warning(this::class.simpleName, "There were an error sending the alert ${alert.id}" +
                        "to the-body")
                false
            }
        }
    }
}