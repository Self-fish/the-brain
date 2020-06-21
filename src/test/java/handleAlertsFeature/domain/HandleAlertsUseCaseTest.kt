package handleAlertsFeature.domain

import application.logger.LoggerWrapper
import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.`model `.AlertDate
import handleAlertsFeature.domain.contracts.AlertsRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import java.time.LocalDateTime
import java.util.logging.Logger

internal class HandleAlertsUseCaseTest {

    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val alertsRepository = Mockito.mock(AlertsRepository::class.java)
    private val useCAse = HandleAlertsUseCase(alertsRepository, loggerWrapper)

    @Test
    @DisplayName("A near incoming alert not already sent is sent")
    fun handleAlertsWhenNearIncomingAlertNotSent() {
        val mockAlert = Alert("0", "Mock Alert", AlertDate(LocalDateTime.now().dayOfWeek.value,
                LocalDateTime.now().hour, LocalDateTime.now().minute+1), 0)
        Mockito.`when`(alertsRepository.getNextAlert()).thenReturn(mockAlert)
        useCAse.handleAlerts()
        Mockito.verify(alertsRepository, Mockito.times(1)).sendAlert(mockAlert)
    }

    @Test
    @DisplayName("A not near incoming alert not already sent is not sent")
    fun handleAlertsWhenNotNearIncomingAlertNotSent() {
        val mockAlert = Alert("0", "Mock Alert", AlertDate(LocalDateTime.now().dayOfWeek.value,
                LocalDateTime.now().hour, LocalDateTime.now().minute+15), 0)
        Mockito.`when`(alertsRepository.getNextAlert()).thenReturn(mockAlert)
        useCAse.handleAlerts()
        Mockito.verify(alertsRepository, Mockito.times(0)).sendAlert(mockAlert)
    }

    @Test
    @DisplayName("A past alert is not sent")
    fun handleAlertsWhenPastAlert() {
        val mockAlert = Alert("0", "Mock Alert", AlertDate(LocalDateTime.now().dayOfWeek.value,
                LocalDateTime.now().hour, LocalDateTime.now().minute-5), 0)
        Mockito.`when`(alertsRepository.getNextAlert()).thenReturn(mockAlert)
        useCAse.handleAlerts()
        Mockito.verify(alertsRepository, Mockito.times(0)).sendAlert(mockAlert)
    }

    @Test
    @DisplayName("A near incoming alert already sent is not sent")
    fun handleAlertsWhenNearIncomingAlertSent() {
        val mockAlert = Alert("0", "Mock Alert", AlertDate(LocalDateTime.now().dayOfWeek.value,
                LocalDateTime.now().hour, LocalDateTime.now().minute+4), System.currentTimeMillis()-25)
        Mockito.`when`(alertsRepository.getNextAlert()).thenReturn(mockAlert)
        useCAse.handleAlerts()
        Mockito.verify(alertsRepository, Mockito.times(0)).sendAlert(mockAlert)
    }

    @Test
    @DisplayName("A near incoming alert but not for today is not sent")
    fun handleAlertsWhenNearIncomingAlertNotForTodayNotSent() {
        val mockAlert = Alert("0", "Mock Alert", AlertDate(LocalDateTime.now().dayOfWeek.value+1,
                LocalDateTime.now().hour, LocalDateTime.now().minute+4), 0)
        Mockito.`when`(alertsRepository.getNextAlert()).thenReturn(mockAlert)
        useCAse.handleAlerts()
        Mockito.verify(alertsRepository, Mockito.times(0)).sendAlert(mockAlert)
    }

    @Test
    @DisplayName("A near incoming alert but for today but in different hour is not sent")
    fun handleAlertsWhenNearIncomingAlertForTodayDifferentHourNotSent() {
        val mockAlert = Alert("0", "Mock Alert", AlertDate(LocalDateTime.now().dayOfWeek.value,
                LocalDateTime.now().hour+1, LocalDateTime.now().minute+4), 0)
        Mockito.`when`(alertsRepository.getNextAlert()).thenReturn(mockAlert)
        useCAse.handleAlerts()
        Mockito.verify(alertsRepository, Mockito.times(0)).sendAlert(mockAlert)
    }

    @Test
    @DisplayName("A near incoming alert already sent but more than 30 minutes ago is sent")
    fun handleAlertsWhenNearIncomingAlertAlreadySentMoreThan30MinutesIsSent() {
        val mockAlert = Alert("0", "Mock Alert", AlertDate(LocalDateTime.now().dayOfWeek.value,
                LocalDateTime.now().hour, LocalDateTime.now().minute+4), System.currentTimeMillis()-1900000)
        Mockito.`when`(alertsRepository.getNextAlert()).thenReturn(mockAlert)
        useCAse.handleAlerts()
        Mockito.verify(alertsRepository, Mockito.times(1)).sendAlert(mockAlert)
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}