package showBoxTemperatureFeature.domain

import application.logger.LoggerWrapper
import collectHumidityAndBoxTempFeature.domain.contracts.HumidityAndTemperatureRepository
import collectHumidityAndBoxTempFeature.domain.exceptions.BoxTemperatureSensorException
import org.koin.standalone.KoinComponent
import showBoxTemperatureFeature.domain.contract.ScreenController
import java.text.SimpleDateFormat
import java.util.*

class UpdateBoxTemperatureUseCase(private val repository: HumidityAndTemperatureRepository,
                                  private val screenController: ScreenController,
                                  private val logger: LoggerWrapper) : KoinComponent  {

    companion object {
        private const val MADRID_TIMEZONE = "Europe/Madrid"
    }

    fun updateBoxTemperature() {
        try {
            if(screenController.printBoxTemperature(repository.getCurrentTemperature(), buildDate())){
                logger.info(this::class.simpleName, "The box temperature was successfully updated")
            }
        } catch (e: BoxTemperatureSensorException) {
            logger.warning(this::class.simpleName, "There were an error when trying to update the " +
                    "box temperature")
        }
    }

    private fun buildDate(): String {
        val cal = Calendar.getInstance(TimeZone.getTimeZone(MADRID_TIMEZONE))
        return SimpleDateFormat("HH:mm  dd MMM yyyy").format(cal.time)
    }
}