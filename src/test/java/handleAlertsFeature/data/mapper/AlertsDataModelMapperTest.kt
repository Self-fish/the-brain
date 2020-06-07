package handleAlertsFeature.data.mapper

import handleAlertsFeature.data.datamodel.AlertsDataModel
import handleAlertsFeature.data.datamodel.RepeatRate
import handleAlertsFeature.data.datamodel.StartingMoment
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mock
import org.mockito.Mockito
import java.time.DayOfWeek

internal class AlertsDataModelMapperTest {

    private val mapper = AlertsDataModelMapper()

    @Test
    @DisplayName("All the AlertsDataModel are mapped correctly ")
    fun allAlertsDataModelMappedCorrectly() {
        val alert1 = AlertsDataModel("0", 0L, "Mock Alert 1", "Mock Alert 1",
                listOf(), StartingMoment(DayOfWeek.MONDAY,22,0), RepeatRate.DAILY)
        val alert2 = AlertsDataModel("1", 0L, "Mock Alert 2", "Mock Alert 2",
                listOf(), StartingMoment(DayOfWeek.TUESDAY,22,15), RepeatRate.DAILY)
        val alert3 = AlertsDataModel("2", 0L, "Mock Alert 3", "Mock Alert 3",
                listOf(), StartingMoment(DayOfWeek.MONDAY,22,15), RepeatRate.DAILY)
        val alertDataModelList = listOf(alert1, alert2, alert3)
        val alertList = mapper.mapToAlertList(alertDataModelList)
        assertEquals(alertList.size, 3)
    }

    @Test
    @DisplayName("The Alert ID is mapped correctly")
    fun alertIDMappedCorrectly() {
        val alert1 = AlertsDataModel("ID 1", 0L, "Mock Alert 1", "Mock Alert 1",
                listOf(), StartingMoment(DayOfWeek.MONDAY,22,0), RepeatRate.DAILY)
        val alertDataModelList = listOf(alert1)
        val alertList = mapper.mapToAlertList(alertDataModelList)
        assertEquals(alertList[0].id, "ID 1")
    }

    @Test
    @DisplayName("The Alert text is mapped correctly")
    fun alertTextMappedCorrectly() {
        val alert1 = AlertsDataModel("ID 1", 0L, "Mock Alert 1", "Mock Alert 1",
                listOf(), StartingMoment(DayOfWeek.MONDAY,22,0), RepeatRate.DAILY)
        val alertDataModelList = listOf(alert1)
        val alertList = mapper.mapToAlertList(alertDataModelList)
        assertEquals(alertList[0].text, "Mock Alert 1")
    }

    @Test
    @DisplayName("The Alert nextNotification is mapped correctly")
    fun alertNextNotificationMappedCorrectly() {
        val alert1 = AlertsDataModel("ID 1", 0L, "Mock Alert 1", "Mock Alert 1",
                listOf(), StartingMoment(DayOfWeek.MONDAY,22,0), RepeatRate.DAILY)
        val alertDataModelList = listOf(alert1)
        val alertList = mapper.mapToAlertList(alertDataModelList)
        assertEquals(alertList[0].nextNotification.day, DayOfWeek.MONDAY.value)
        assertEquals(alertList[0].nextNotification.hour, 22)
        assertEquals(alertList[0].nextNotification.minute, 0)
    }

    @Test
    @DisplayName("The Alert lastSent is mapped correctly")
    fun alertLastSentMappedCorrectly() {
        val alert1 = AlertsDataModel("ID 1", 0L, "Mock Alert 1", "Mock Alert 1",
                listOf(123456789, 135792468), StartingMoment(DayOfWeek.MONDAY,22,0), RepeatRate.DAILY)
        val alertDataModelList = listOf(alert1)
        val alertList = mapper.mapToAlertList(alertDataModelList)
        assertEquals(alertList[0].lastSent, 135792468)
    }


    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}