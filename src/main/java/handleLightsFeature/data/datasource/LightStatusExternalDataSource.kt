package handleLightsFeature.data.datasource

import handleLightsFeature.domain.contracts.action.LightAction
import handleLightsFeature.domain.model.LightStatus
import org.koin.standalone.KoinComponent

open class LightStatusExternalDataSource(private val action: LightAction) : KoinComponent {

    open fun getLightStatus() : LightStatus {
        return LightStatus.valueOf(action.getLightStatus())
    }

}