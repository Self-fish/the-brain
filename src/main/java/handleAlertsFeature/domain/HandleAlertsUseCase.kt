package handleAlertsFeature.domain

import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.contracts.AlertsRepository
import org.koin.standalone.KoinComponent

class HandleAlertsUseCase(private val alertRepository: AlertsRepository) : KoinComponent {


    fun handleAlerts() {
        val nextAlert = alertRepository.getNextAlert()
        when {
            nextAlert != null && shouldSendNotification(nextAlert) -> {
                alertRepository.sendAlert(nextAlert)
            }
        }
    }

    private fun shouldSendNotification(alert: Alert) =  !alert.isPastAlert() &&
            !alert.hasRecentlySent() && alert.isNearToBeSent()

}