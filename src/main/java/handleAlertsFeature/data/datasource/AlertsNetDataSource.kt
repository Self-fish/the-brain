package handleAlertsFeature.data.datasource

import application.network.Either
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import handleAlertsFeature.data.datamodel.AlertsDataModel
import org.koin.standalone.KoinComponent

open class AlertsNetDataSource : KoinComponent {

    companion object {
        const val API_HOST = "http://192.168.1.15:8080/alerts"
    }

    open fun getAllAlerts() : Either<AlertNetworkError, List<AlertsDataModel>> {
        val alertsList = arrayListOf<AlertsDataModel>()
        val (_, response, result) = API_HOST.httpGet().responseObject(AlertsDataModel.AlertArrayDeserializer())
        if(response.statusCode == 200) {
            result.get().forEach { alertsDataModel ->
                alertsList.add(alertsDataModel)
            }
            return Either.Right(alertsList)
        }
        return Either.Left(AlertNetworkError(response.statusCode, response.responseMessage))

    }

    open fun executeAlert(id: String) : Either<AlertNetworkError, AlertsDataModel> {
        val (_, response, result) = API_HOST.plus("/$id/execute").httpPost().
                responseObject(AlertsDataModel.AlertDeserializer())
        if(response.statusCode == 200) {
            return Either.Right(result.get())
        }
        return Either.Left(AlertNetworkError(response.statusCode, response.responseMessage))
    }



}
