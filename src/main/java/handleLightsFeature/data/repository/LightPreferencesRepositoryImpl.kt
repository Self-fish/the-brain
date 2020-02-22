package handleLightsFeature.data.repository

import handleLightsFeature.data.datasource.LightPreferencesLocalDataSource
import handleLightsFeature.domain.contracts.repository.LightPreferencesRepository
import handleLightsFeature.domain.model.LightPreferences
import org.koin.standalone.KoinComponent

class LightPreferencesRepositoryImpl(private val localDataSource: LightPreferencesLocalDataSource):
        KoinComponent, LightPreferencesRepository {

    override fun getLightsPreferences(): LightPreferences {
        return localDataSource.getLightPreferences()
    }
}