package handleAlertsFeature.domain.`model `

data class AlertDate (
        var day: Int,
        var hour: Int,
        val minute: Int) {

    operator fun compareTo(alertDate: AlertDate): Int {
        return when {
            day < alertDate.day -> -1
            day > alertDate.day ->  1
            else -> return when {
                hour < alertDate.hour -> -1
                hour > alertDate.hour -> 1
                else -> return when {
                    minute < alertDate.minute -> -1
                    minute > alertDate.minute -> 1
                    else -> 0
                }
            }
        }
    }

    fun postponeOneHour() {
        if(hour == 23) {
            day++
            hour = 0
        } else {
            hour++
        }
    }


}

