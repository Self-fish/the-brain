package lightsFeature.data.datasource

import lightsFeature.domain.contracts.action.LightAction
import lightsFeature.domain.model.LightStatus
import org.koin.standalone.KoinComponent

open class LightStatusExternalDataSource(private val action: LightAction) : KoinComponent {

    open fun getLightStatus() : LightStatus {
        return LightStatus.valueOf(action.getLightStatus())
    }

}