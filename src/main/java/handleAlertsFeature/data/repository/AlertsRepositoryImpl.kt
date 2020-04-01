package handleAlertsFeature.data.repository

import handleAlertsFeature.data.action.AlertsAction
import handleAlertsFeature.data.datasource.AlertsLocalDataSource
import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.`model `.AlertDate
import handleAlertsFeature.domain.contracts.AlertsRepository
import org.koin.standalone.KoinComponent
import java.time.LocalDateTime

class AlertsRepositoryImpl(private val localDataSource: AlertsLocalDataSource,
                           private val action: AlertsAction) : AlertsRepository, KoinComponent {

    private val CACHE_TIME_TO_LIVE = 300000 //5 minutes

    override fun getNextAlert(): Alert {
        return orderAlerts().first()
    }

    private fun orderAlerts(): List<Alert> {
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
        return localDataSource.listOfAlerts.sortedWith(aux)
    }

    override fun sendAlert(alert: Alert) {
        when {
            action.sendAlert(alert) == AlertsAction.AlertActionResponse.LAT -> {
                alert.lastSent = System.currentTimeMillis()
                alert.nextNotification.postponeOneHour()
                localDataSource.modifyAlert(alert)
            }

            action.sendAlert(alert) == AlertsAction.AlertActionResponse.OK -> {
                alert.lastSent = System.currentTimeMillis()
                localDataSource.modifyAlert(alert)
            }
        }
    }
}