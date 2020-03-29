package handleAlertsFeature.data.repository

import handleAlertsFeature.data.datasource.AlertsLocalDataSource
import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.contracts.AlertsRepository
import java.time.LocalDateTime

class AlertsRepositoryImpl(private val localDataSource: AlertsLocalDataSource) : AlertsRepository {

    var alertsCache = localDataSource.getAllAlerts()

    override fun getNextAlert(): Alert {
        lateinit var nextAlert : Alert
        alertsCache.forEach {
            if(isAIncomingAlert(it)) {
                if(it < nextAlert){
                    nextAlert = it
                }
            }
        }
        return nextAlert
    }

    private fun isAIncomingAlert(alert: Alert) = LocalDateTime.now().dayOfWeek.value <= alert.nextNotification.day &&
            LocalDateTime.now().hour <= alert.nextNotification.hour &&
            LocalDateTime.now().minute <= alert.nextNotification.minute

    override fun sendAlert(alert: Alert) {

    }
}