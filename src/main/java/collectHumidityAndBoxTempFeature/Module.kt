import application.logger.LoggerWrapper
import collectHumidityAndBoxTempFeature.data.controller.DHT22Controller
import collectHumidityAndBoxTempFeature.data.repository.HumidityAndTemperatureRepositoryImpl
import collectHumidityAndBoxTempFeature.domain.CollectHumidityAndBoxTempUseCase
import collectHumidityAndBoxTempFeature.domain.contracts.HumidityAndTemperatureRepository
import com.pi4j.io.gpio.RaspiPin
import org.koin.dsl.module.module
import java.util.logging.Logger

val collectHumidityFeature = module {
    single { CollectHumidityAndBoxTempUseCase(get(), get()) }
    single<HumidityAndTemperatureRepository> { HumidityAndTemperatureRepositoryImpl(initialiseDHT22(), get(),
            2500) }
}

fun initialiseDHT22() = DHT22Controller(RaspiPin.GPIO_02.address,
        LoggerWrapper( Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)))