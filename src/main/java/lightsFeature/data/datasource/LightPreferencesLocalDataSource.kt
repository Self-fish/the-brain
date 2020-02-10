package lightsFeature.data.datasource

import lightsFeature.domain.model.LightPreferences

open class LightPreferencesLocalDataSource {

    open fun getLightPreferences() = LightPreferences("00:25","00:30")

}