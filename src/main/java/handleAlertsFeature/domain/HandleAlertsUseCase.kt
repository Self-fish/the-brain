package handleAlertsFeature.domain

import application.logger.LoggerWrapper
import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.contracts.AlertsRepository
import org.koin.standalone.KoinComponent

class HandleAlertsUseCase(private val alertRepository: AlertsRepository,
                          private val logger: LoggerWrapper) : KoinComponent {


    fun handleAlerts() {
        val nextAlert = alertRepository.getNextAlert()
        when {
            nextAlert != null && shouldSendNotification(nextAlert) -> {
                if(alertRepository.sendAlert(nextAlert)) {
                    logger.info(this::class.simpleName, "The alert ${nextAlert.id} was sent successfully")
                } else {
                    logger.warning(this::class.simpleName, "There was an error sending the " +
                            "alert ${nextAlert.id}")
                }
            }
        }
    }

    private fun shouldSendNotification(alert: Alert) : Boolean {
        if(!alert.isPastAlert()) {
            logger.fine(this::class.simpleName, "The alert ${alert.id} is future")
            if(!alert.hasRecentlySent()) {
                logger.fine(this::class.simpleName, "The alert ${alert.id} was not recently sent")
                if(alert.isNearToBeSent()) {
                    logger.fine(this::class.simpleName, "The alert ${alert.id} is going to happen soon")
                    return true
                } else {
                    logger.fine(this::class.simpleName, "The alert ${alert.id} is not going to happen soon")
                }
            } else {
                logger.fine(this::class.simpleName, "The alert ${alert.id} was recently sent")
            }
        } else {
            logger.fine(this::class.simpleName, "The alert ${alert.id} is past")
        }
        return false
    }

}