package handleAlertsFeature.data.mapper

import handleAlertsFeature.data.datamodel.AlertsDataModel
import handleAlertsFeature.data.datamodel.DayOfWeek
import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.`model `.AlertDate
import org.koin.standalone.KoinComponent

class AlertsDataModelMapper : KoinComponent {

    fun mapToAlertList(alertsDataModelList: List<AlertsDataModel>) : List<Alert> {
        val alertsList = arrayListOf<Alert>()
        alertsDataModelList.forEach { alertsDataModel ->
            alertsList.add(mapToAlert(alertsDataModel))
        }
        return alertsList
    }

    private fun mapToAlert(alertsDataModel: AlertsDataModel): Alert {
        return Alert(alertsDataModel.id, alertsDataModel.text, AlertDate(mapDayOfWeek(alertsDataModel.starts.day),
                alertsDataModel.starts.hour, alertsDataModel.starts.minute),
                mapLastSent(alertsDataModel.executionHistory))
    }

    private fun mapDayOfWeek(dayOfWeek: DayOfWeek) =
            when (dayOfWeek) {
                DayOfWeek.MON -> 1
                DayOfWeek.TUE -> 2
                DayOfWeek.WED -> 3
                DayOfWeek.THU -> 4
                DayOfWeek.FRI -> 5
                DayOfWeek.SAT -> 6
                else -> 7

            }


    private fun mapLastSent(executionHistory: List<Long>) =
            if (executionHistory.isEmpty()) {
                0L
            } else {
                executionHistory.last()
            }
}

