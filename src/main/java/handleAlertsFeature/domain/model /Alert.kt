package handleAlertsFeature.domain.`model `

data class Alert(
        val id: Int,
        val text: String,
        val date: AlertDate,
        val nextNotification: AlertDate,
        val lastNotification: Long) {

    operator fun compareTo(alert: Alert): Int {
        return when {
            nextNotification.day < alert.nextNotification.day -> -1
            nextNotification.day > alert.nextNotification.day ->  1
            else -> return when {
                nextNotification.hour < alert.nextNotification.hour -> -1
                nextNotification.hour > alert.nextNotification.hour -> 1
                else -> return when {
                    nextNotification.minute < alert.nextNotification.minute -> -1
                    nextNotification.minute > alert.nextNotification.minute -> -1
                    else -> 0
                }
            }
        }
    }
}

