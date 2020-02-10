package lightsFeature.domain.contracts.repository

import lightsFeature.domain.model.LightPreferences

interface LightPreferencesRepository {

    fun getLightsPreferences() : LightPreferences

}