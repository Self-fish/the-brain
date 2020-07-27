import application.applicationModule
import application.logger.LoggerWrapper
import collectHumidityFeature.data.controller.DHT22Controller
import collectHumidityFeature.data.repository.HumidityAndTemperatureRepositoryImpl
import collectHumidityFeature.domain.CollectHumidityUseCase
import collectHumidityFeature.domain.contracts.HumidityAndTemperatureRepository
import com.pi4j.io.gpio.RaspiPin
import org.koin.dsl.module.module
import java.util.logging.Logger

val collectHumidityFeature = module {
    single { CollectHumidityUseCase(get(), get()) }
    single<HumidityAndTemperatureRepository> { HumidityAndTemperatureRepositoryImpl(initialiseDHT22(), get()) }
}

fun initialiseDHT22() = DHT22Controller(RaspiPin.GPIO_02.address,
        LoggerWrapper( Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)))