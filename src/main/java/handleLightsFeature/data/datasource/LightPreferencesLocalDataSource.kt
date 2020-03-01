package handleLightsFeature.data.datasource

import handleLightsFeature.domain.model.LightPreferences

open class LightPreferencesLocalDataSource {

    open fun getLightPreferences() = LightPreferences("16:00","23:59")

}