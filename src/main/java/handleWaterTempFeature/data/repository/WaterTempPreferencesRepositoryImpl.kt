package handleWaterTempFeature.data.repository

import handleWaterTempFeature.domain.contracts.repository.WaterTempPreferencesRepository
import org.koin.standalone.KoinComponent

class WaterTempPreferencesRepositoryImpl : WaterTempPreferencesRepository, KoinComponent {

    private val DESIRED_TEMP = 26.0

    override fun getWaterTempPreferences(): Double {
       return DESIRED_TEMP
    }
}