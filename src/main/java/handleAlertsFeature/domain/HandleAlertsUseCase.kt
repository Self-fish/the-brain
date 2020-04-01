package handleAlertsFeature.domain

import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.`model `.AlertDate
import handleAlertsFeature.domain.contracts.AlertsRepository
import org.koin.standalone.KoinComponent
import java.time.LocalDateTime

class HandleAlertsUseCase(private val alertRepository: AlertsRepository) : KoinComponent {

    var lastSent = 0L
    private val PERIOD = 300000 //5 minutes

    fun handleAlerts() {
        if(shouldExecute()) {
            val nextAlert = alertRepository.getNextAlert()
            when {
                shouldSendNotification(nextAlert) -> {
                    alertRepository.sendAlert(nextAlert)
                    lastSent = System.currentTimeMillis()
                }
            }
        }
    }

    private fun shouldSendNotification(alert: Alert) = !alert.isPastAlert() &&
            !alert.hasRecentlySent() && alert.isNearToBeSent()

    private fun shouldExecute() = System.currentTimeMillis() - lastSent > PERIOD
}