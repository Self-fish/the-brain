package handleWaterTempFeature.data.repository

import handleWaterTempFeature.domain.contracts.repository.WaterTempPreferencesRepository

class WaterTempPreferencesRepositoryImpl : WaterTempPreferencesRepository {

    private val DESIRED_TEMP = 26.0

    override fun getWaterTempPreferences(): Double {
       return DESIRED_TEMP
    }
}