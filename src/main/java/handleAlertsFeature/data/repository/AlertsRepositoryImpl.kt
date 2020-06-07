package handleAlertsFeature.data.repository

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
                           private val mapper: AlertsDataModelMapper) : AlertsRepository, KoinComponent {

    override fun getNextAlert(): Alert? {
        return if (getOrderAlerts().isEmpty()) null
        else getOrderAlerts().first()
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
                when(netDataSource.executeAlert(alert.id)) {
                    is Either.Left -> false
                    is Either.Right -> true
                }
            } else -> false
        }
    }
}