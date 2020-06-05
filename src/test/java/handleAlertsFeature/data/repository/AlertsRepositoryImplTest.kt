package handleAlertsFeature.data.repository

import application.logger.LoggerWrapper
import application.usb.UsbController
import handleAlertsFeature.data.action.AlertsAction
import handleAlertsFeature.data.datasource.AlertsNetDataSource
import handleAlertsFeature.data.mapper.AlertsDataModelMapper
import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.`model `.AlertDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import java.time.LocalDateTime
import java.util.logging.Logger

internal class AlertsRepositoryImplTest {

    private var netDataSource = Mockito.mock(AlertsNetDataSource::class.java)
    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val usbController = Mockito.mock(UsbController::class.java)
    private val action = AlertsAction(loggerWrapper, usbController)
    private val mapper = Mockito.mock(AlertsDataModelMapper::class.java)
    private val repository = AlertsRepositoryImpl(netDataSource, action, mapper)


    @Test
    @DisplayName("Order is good when all the alerts are incoming")
    fun getNextAlertWhenAllIncoming() {
        val day = LocalDateTime.now().dayOfWeek.value
        val hour = LocalDateTime.now().hour
        val minute = LocalDateTime.now().minute
        val alert1 = Alert("0", "Mock Alert 1", AlertDate(day,hour+2,0), 0)
        val alert2 = Alert("1", "Mock Alert 2", AlertDate(day+1,22,15), 0)
        val alert3 = Alert("2", "Mock Alert 3", AlertDate(day,hour+1,15), 0)
        //localDataSource.listOfAlerts = mutableListOf(alert1, alert2, alert3)
        assertEquals(repository.getNextAlert()?.id, 2)
    }

    @Test
    @DisplayName("WhenAnAlertIsPastItShouldNotBeTheNext")
    fun getNextAlertWhenOneIsPast() {
        val day = LocalDateTime.now().dayOfWeek.value
        val hour = LocalDateTime.now().hour
        val minute = LocalDateTime.now().minute
        val alert1 = Alert("0", "Mock Alert 1", AlertDate(day-1,hour+2,0), 0)
        val alert2 = Alert("1", "Mock Alert 2", AlertDate(day+1,22,15), 0)
        val alert3 = Alert("2", "Mock Alert 3", AlertDate(day,hour+1,15), 0)
        //localDataSource.listOfAlerts = mutableListOf(alert1, alert2, alert3)
        assertEquals(repository.getNextAlert()?.id, 2)
    }

    @Test
    @DisplayName("WhenAnAlertIsRightNowShouldBeTheNext")
    fun getNextAlertWhenOneRightNOw() {
        val day = LocalDateTime.now().dayOfWeek.value
        val hour = LocalDateTime.now().hour
        val minute = LocalDateTime.now().minute
        val alert1 = Alert("0", "Mock Alert 1", AlertDate(day-1,hour+2,0), 0)
        val alert2 = Alert("1", "Mock Alert 2", AlertDate(day,hour,minute+1), 0)
        val alert3 = Alert("2", "Mock Alert 3", AlertDate(day,hour,minute), 0)
        //localDataSource.listOfAlerts = mutableListOf(alert1, alert2, alert3)
        assertEquals(repository.getNextAlert()?.id, 2)
    }

    @Test
    fun sendAlertWhenLATResponse() {
        val alert1 = Alert("0", "Mock Alert 1", AlertDate(1,22,0), 0)
        Mockito.`when`(usbController.readCommand()).thenReturn("LAT")
        repository.sendAlert(alert1)
        assertFalse(alert1.lastSent == 0L)
        assertTrue(alert1.nextNotification.hour == 23)
    }

    @Test
    fun sendAlertWhenOKResponse() {
        val alert1 = Alert("0", "Mock Alert 1", AlertDate(1,22,0), 0)
        Mockito.`when`(usbController.readCommand()).thenReturn("OK")
        repository.sendAlert(alert1)
        assertFalse(alert1.lastSent == 0L)
        assertTrue(alert1.nextNotification.hour == 22)
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}