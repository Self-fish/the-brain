package lightsFeature.data.repository

import lightsFeature.data.datasource.LightPreferencesLocalDataSource
import lightsFeature.domain.contracts.repository.LightPreferencesRepository
import lightsFeature.domain.model.LightPreferences
import org.koin.standalone.KoinComponent

class LightPreferencesRepositoryImpl(private val localDataSource: LightPreferencesLocalDataSource):
        KoinComponent, LightPreferencesRepository {

    override fun getLightsPreferences(): LightPreferences {
        return localDataSource.getLightPreferences()
    }
}