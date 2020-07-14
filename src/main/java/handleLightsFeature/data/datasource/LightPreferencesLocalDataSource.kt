package handleLightsFeature.data.datasource

import handleLightsFeature.domain.model.LightPreferences

open class LightPreferencesLocalDataSource {

    open fun getLightPreferences() = LightPreferences("15:00","22:59")

}