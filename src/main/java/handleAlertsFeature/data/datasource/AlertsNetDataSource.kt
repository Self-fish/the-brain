package handleAlertsFeature.data.datasource

import application.logger.LoggerWrapper
import application.network.Either
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import handleAlertsFeature.data.datamodel.AlertsDataModel
import org.koin.standalone.KoinComponent

open class AlertsNetDataSource(private val logger: LoggerWrapper) : KoinComponent {

    companion object {
        const val API_HOST = "http://192.168.1.15:8080/alerts"
    }

    open fun getAllAlerts() : Either<AlertNetworkError, List<AlertsDataModel>> {
        val alertsList = arrayListOf<AlertsDataModel>()
        logger.finest(this::class.simpleName, "GET: $API_HOST")
        val (_, response, result) = API_HOST.httpGet().responseObject(AlertsDataModel.AlertArrayDeserializer())
        if(response.statusCode == 200) {
            logger.finest(this::class.simpleName, "Response code: ${response.statusCode}")
            logger.finest(this::class.simpleName, "Response: ${result.get()}")
            result.get().forEach { alertsDataModel ->
                alertsList.add(alertsDataModel)
            }
            return Either.Right(alertsList)
        }
        logger.warning(this::class.simpleName, "Response code: ${response.statusCode}")
        logger.warning(this::class.simpleName, "Response message: ${response.responseMessage}")
        return Either.Left(AlertNetworkError(response.statusCode, response.responseMessage))

    }

    open fun executeAlert(id: String) : Either<AlertNetworkError, AlertsDataModel> {
        logger.finest(this::class.simpleName, "POST: ${API_HOST.plus("/$id/execute")} for alert $id")
        val (_, response, result) = API_HOST.plus("/$id/execute").httpPost().
                responseObject(AlertsDataModel.AlertDeserializer())
        if(response.statusCode == 200) {
            logger.finest(this::class.simpleName, "Response code: ${response.statusCode}")
            logger.finest(this::class.simpleName, "Response: ${result.get()}")
            return Either.Right(result.get())
        }
        logger.warning(this::class.simpleName, "Response code: ${response.statusCode}")
        logger.warning(this::class.simpleName, "Response message: ${response.responseMessage}")
        return Either.Left(AlertNetworkError(response.statusCode, response.responseMessage))
    }



}
