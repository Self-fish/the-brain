package lightsFeature.data.datasource

import lightsFeature.domain.model.LightPreferences

open class LightPreferencesLocalDataSource {

    open fun getLightPreferences() = LightPreferences("08:00","14:00")

}