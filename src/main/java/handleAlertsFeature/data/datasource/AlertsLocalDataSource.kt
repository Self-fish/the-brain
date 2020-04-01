package handleAlertsFeature.data.datasource

import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.`model `.AlertDate
import org.koin.standalone.KoinComponent

class AlertsLocalDataSource : KoinComponent{

    private val changeWaterAlert = Alert(0, "Change Water!",
            AlertDate(1, 20, 0), 0L)
    private val changeWaterAlert2 = Alert(1, "Change Water2!",
            AlertDate(2, 23, 42), 0L)
    private val changeWaterAlert3 = Alert(2, "Change Water3!",
            AlertDate(2, 8, 0), 0L)
    var listOfAlerts = mutableListOf(changeWaterAlert3,changeWaterAlert,changeWaterAlert2)

    fun modifyAlert(alert: Alert) {
        listOfAlerts.find { it == alert }?.nextNotification = alert.nextNotification
    }

}