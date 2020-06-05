package handleAlertsFeature.domain.contracts

import handleAlertsFeature.domain.`model `.Alert


interface AlertsRepository {

    fun getNextAlert(): Alert?
    fun sendAlert(alert: Alert): Boolean

}