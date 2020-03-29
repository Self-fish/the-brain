package handleAlertsFeature.domain

import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.`model `.AlertDate
import handleAlertsFeature.domain.contracts.AlertsRepository
import java.time.LocalDateTime

class HandleAlertsUseCase(private val alertRepository: AlertsRepository) {

    fun handleAlerts() {
        val nextAlert = alertRepository.getNextAlert()
        when {
            shouldSendNotification(nextAlert) -> {
                alertRepository.sendAlert(nextAlert)
            }
        }
    }

    private fun shouldSendNotification(alert: Alert) =
            AlertDate(LocalDateTime.now().dayOfWeek.value, LocalDateTime.now().hour, LocalDateTime.now().minute) > alert.nextNotification

}