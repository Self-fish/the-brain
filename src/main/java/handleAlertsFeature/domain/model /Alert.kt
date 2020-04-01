package handleAlertsFeature.domain.`model `

import java.time.LocalDate
import java.time.LocalDateTime

data class Alert(
        val id: Int,
        val text: String,
        var nextNotification: AlertDate,
        var lastSent: Long) {

    operator fun compareTo(alert: Alert): Int {
        return nextNotification.compareTo(alert.nextNotification)
    }

    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        return this.id == (other as Alert).id
    }

    fun isPastAlert() = nextNotification < AlertDate(LocalDateTime.now().dayOfWeek.value,
                LocalDateTime.now().hour, LocalDateTime.now().minute)

    fun hasRecentlySent() = lastSent!= 0L && lastSent+1800000 > System.currentTimeMillis()

    fun isNearToBeSent() = nextNotification.day == LocalDateTime.now().dayOfWeek.value &&
            nextNotification.hour == LocalDateTime.now().hour && nextNotification.minute < LocalDateTime.now().minute+5

}

