package handleAlertsFeature.data.datasource

import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.`model `.AlertDate

class AlertsLocalDataSource {

    fun getAllAlerts() : List<Alert> {
        val changeWaterAlert = Alert(0, "Change Water!",
                AlertDate(1, 20, 0 ), AlertDate(1, 20, 0), 0L)
        return listOf(changeWaterAlert)
    }

}