package handleLightsFeature.domain.contracts.repository

import handleLightsFeature.domain.model.LightPreferences

interface LightPreferencesRepository {

    fun getLightsPreferences() : LightPreferences

}