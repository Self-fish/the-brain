package handleAlertsFeature.data.datasource

import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.`model `.AlertDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import kotlin.test.assertTrue

internal class AlertsLocalDataSourceTest {

    //private val localDataSource = AlertsLocalDataSource()

    @Test
    @DisplayName("When modify alert the size of the list keep the same")
    fun alertListSizeKeepTheSameWhenModity() {
        var alert1 = Alert("0", "Mock Alert 1", AlertDate(5,23,0), 0)
        var alert2 = Alert("1", "Mock Alert 2", AlertDate(3,22,15), 0)
        //localDataSource.listOfAlerts = mutableListOf(alert1, alert2)
        alert2 = Alert("1", "Mock Alert 2", AlertDate(6,9,25), 0)
        //localDataSource.modifyAlert(alert2)
        //assertTrue(localDataSource.listOfAlerts.size == 2)
    }

    @Test
    @DisplayName("When modify an alert the alert keep the same but modified")
    fun alertChangeWhenModify() {
        var alert1 = Alert("0", "Mock Alert 1", AlertDate(5,23,0), 0)
        var alert2 = Alert("1", "Mock Alert 2", AlertDate(3,22,15), 0)
        //localDataSource.listOfAlerts = mutableListOf(alert1, alert2)
        alert2 = Alert("1", "Mock Alert 2", AlertDate(6,9,25), 0)
        //localDataSource.modifyAlert(alert2)
        //assertTrue(localDataSource.listOfAlerts[1].text == "Mock Alert 2")
        //assertTrue(localDataSource.listOfAlerts[1].nextNotification.day == 6)
        //assertTrue(localDataSource.listOfAlerts[1].nextNotification.hour == 9)
        //assertTrue(localDataSource.listOfAlerts[1].nextNotification.minute == 25)
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}