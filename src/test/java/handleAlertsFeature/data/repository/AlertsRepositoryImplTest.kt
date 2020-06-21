package handleAlertsFeature.data.repository

import application.logger.LoggerWrapper
import application.network.Either
import application.usb.UsbController
import handleAlertsFeature.data.action.AlertsAction
import handleAlertsFeature.data.datamodel.AlertsDataModel
import handleAlertsFeature.data.datamodel.RepeatRate
import handleAlertsFeature.data.datamodel.StartingMoment
import handleAlertsFeature.data.datasource.AlertNetworkError
import handleAlertsFeature.data.datasource.AlertsNetDataSource
import handleAlertsFeature.data.mapper.AlertsDataModelMapper
import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.`model `.AlertDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.util.logging.Logger

internal class AlertsRepositoryImplTest {


    private val logger = Mockito.mock(Logger::class.java)
    private val loggerWrapper = LoggerWrapper(logger)
    private val usbController = Mockito.mock(UsbController::class.java)
    private val action = AlertsAction(loggerWrapper, usbController)
    private val netDataSource = Mockito.mock(AlertsNetDataSource::class.java)
    private val mapper = AlertsDataModelMapper()
    private val repository = AlertsRepositoryImpl(netDataSource, action, mapper, loggerWrapper)


    @Test
    @DisplayName("Order is good when all the alerts are incoming")
    fun getNextAlertWhenAllIncoming() {
        val hour = LocalDateTime.now().hour
        val alert1 = AlertsDataModel("0", 0L, "Mock Alert 1", "Mock Alert 1",
                listOf(), StartingMoment(DayOfWeek.MONDAY,hour+2,0), RepeatRate.DAILY)
        val alert2 = AlertsDataModel("1", 0L, "Mock Alert 2", "Mock Alert 2",
                listOf(), StartingMoment(DayOfWeek.TUESDAY,22,15), RepeatRate.DAILY)
        val alert3 = AlertsDataModel("2", 0L, "Mock Alert 3", "Mock Alert 3",
                listOf(), StartingMoment(DayOfWeek.MONDAY,hour+1,15), RepeatRate.DAILY)
        val alertList = listOf(alert1, alert2, alert3)
        val eitherReturn: Either<AlertNetworkError, List<AlertsDataModel>> = Either.Right(alertList)
        Mockito.`when`(netDataSource.getAllAlerts()).thenReturn(eitherReturn)
        assertEquals(repository.getNextAlert()?.id, "2")
    }

    @Test
    @DisplayName("When an Alert Is Past It Should Not Be The Next")
    fun getNextAlertWhenOneIsPast() {
        val hour = LocalDateTime.now().hour
        val today = LocalDateTime.now().dayOfWeek
        val alert1 = AlertsDataModel("0", 0L, "Mock Alert 1", "Mock Alert 1",
                listOf(), StartingMoment(today - 1,hour+2,0), RepeatRate.DAILY)
        val alert2 = AlertsDataModel("1", 0L, "Mock Alert 2", "Mock Alert 2",
                listOf(), StartingMoment(today + 1,22,15), RepeatRate.DAILY)
        val alert3 = AlertsDataModel("2", 0L, "Mock Alert 3", "Mock Alert 3",
                listOf(), StartingMoment(today,hour+1,15), RepeatRate.DAILY)
        val alertList = listOf(alert1, alert2, alert3)
        val eitherReturn: Either<AlertNetworkError, List<AlertsDataModel>> = Either.Right(alertList)
        Mockito.`when`(netDataSource.getAllAlerts()).thenReturn(eitherReturn)
        assertEquals(repository.getNextAlert()?.id, "2")
    }

    @Test
    @DisplayName("When An Alert Is Right Now Should Be TheNext")
    fun getNextAlertWhenOneRightNOw() {
        val day = LocalDateTime.now().dayOfWeek
        val hour = LocalDateTime.now().hour
        val minute = LocalDateTime.now().minute
        val alert1 = AlertsDataModel("0", 0L, "Mock Alert 1", "Mock Alert 1",
                listOf(), StartingMoment(day - 1,hour+2,0), RepeatRate.DAILY)
        val alert2 = AlertsDataModel("1", 0L, "Mock Alert 2", "Mock Alert 2",
                listOf(), StartingMoment(day,hour,minute+1), RepeatRate.DAILY)
        val alert3 = AlertsDataModel("2", 0L, "Mock Alert 3", "Mock Alert 3",
                listOf(), StartingMoment(day,hour,minute), RepeatRate.DAILY)
        val alertList = listOf(alert1, alert2, alert3)
        val eitherReturn: Either<AlertNetworkError, List<AlertsDataModel>> = Either.Right(alertList)
        Mockito.`when`(netDataSource.getAllAlerts()).thenReturn(eitherReturn)
        assertEquals(repository.getNextAlert()?.id, "2")
    }


    @Test
    fun sendAlertWhenOKResponse() {
        val alert1 = Alert("0", "Mock Alert 1", AlertDate(1,22,0), 0)
        Mockito.`when`(usbController.readCommand()).thenReturn("OK")
        val mockAlertDataModel = AlertsDataModel("0", 0L, "Mock Alert 1", "Mock Alert 1",
                listOf(), StartingMoment(DayOfWeek.MONDAY,22,0), RepeatRate.DAILY)
        val eitherResponse: Either<AlertNetworkError, AlertsDataModel> = Either.Right(mockAlertDataModel)
        Mockito.`when`(netDataSource.executeAlert(alert1.id)).thenReturn(eitherResponse)
        repository.sendAlert(alert1)
        Mockito.verify(netDataSource, Mockito.times(1)).executeAlert(alert1.id)
    }


    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}