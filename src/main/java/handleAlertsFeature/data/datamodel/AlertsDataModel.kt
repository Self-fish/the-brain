package handleAlertsFeature.data.datamodel

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class AlertsDataModel(val id: String, var creationDate: Long, val title: String, val text: String,
                           val executionHistory: List<Long>, val starts: StartingMoment, val repeatRate: RepeatRate) {

    class AlertArrayDeserializer: ResponseDeserializable<Array<AlertsDataModel>> {
        override fun deserialize(content: String): Array<AlertsDataModel>? = Gson().fromJson(content,
                Array<AlertsDataModel>::class.java)
    }

    class AlertDeserializer: ResponseDeserializable<AlertsDataModel> {
        override fun deserialize(content: String): AlertsDataModel? = Gson().fromJson(content,
                AlertsDataModel::class.java)
    }

}

data class StartingMoment(val day: DayOfWeek, val hour: Int,
                          val minute: Int)

enum class DayOfWeek {
    MON, TUE, WED, THU, FRI, SAT, SAN
}


enum class RepeatRate {
    DAILY, WEEKLY, MONTHLY
}