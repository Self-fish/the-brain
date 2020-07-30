package showBoxHumidityFeature.domain

import application.logger.LoggerWrapper
import collectHumidityAndBoxTempFeature.domain.contracts.HumidityAndTemperatureRepository
import org.koin.standalone.KoinComponent
import showBoxHumidityFeature.domain.contract.controller.ScreenController
import collectHumidityAndBoxTempFeature.domain.exceptions.HumiditySensorException
import java.text.SimpleDateFormat
import java.util.*

class UpdateBoxHumidityUseCase(private val repository: HumidityAndTemperatureRepository,
                               private val screenController: ScreenController,
                               private val logger: LoggerWrapper) : KoinComponent  {

    companion object {
        private const val MADRID_TIMEZONE = "Europe/Madrid"
    }

    fun updateBoxHumidity() {
        try {
            if(screenController.printBoxHumidity(repository.getCurrentHumidity(), buildDate())){
                logger.info(this::class.simpleName, "The box humidity was successfully updated")
            }
        } catch (e: HumiditySensorException) {
            logger.warning(this::class.simpleName, "There were an error when trying to update the " +
                    "box humidity")
        }
    }

    private fun buildDate(): String {
        val cal = Calendar.getInstance(TimeZone.getTimeZone(MADRID_TIMEZONE))
        return SimpleDateFormat("HH:mm  dd MMM yyyy").format(cal.time)
    }
}