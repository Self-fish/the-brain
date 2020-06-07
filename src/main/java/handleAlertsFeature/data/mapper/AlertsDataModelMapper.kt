package handleAlertsFeature.data.mapper

import handleAlertsFeature.data.datamodel.AlertsDataModel
import handleAlertsFeature.domain.`model `.Alert
import handleAlertsFeature.domain.`model `.AlertDate
import org.koin.standalone.KoinComponent

open class AlertsDataModelMapper: KoinComponent {

    fun mapToAlertList(alertsDataModelList: List<AlertsDataModel>) : List<Alert> {
        val alertsList = arrayListOf<Alert>()
        alertsDataModelList.forEach { alertsDataModel ->
            alertsList.add(mapToAlert(alertsDataModel))
        }
        return alertsList
    }

    private fun mapToAlert(alertsDataModel: AlertsDataModel): Alert {
        return Alert(alertsDataModel.id, alertsDataModel.text, AlertDate(alertsDataModel.starts.day.value,
                alertsDataModel.starts.hour, alertsDataModel.starts.minute),
                mapLastSent(alertsDataModel.executionHistory))
    }



    private fun mapLastSent(executionHistory: List<Long>) =
            if (executionHistory.isEmpty()) {
                0L
            } else {
                executionHistory.last()
            }
}

