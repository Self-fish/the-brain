package handleWaterTempFeature.data.datasource

import handleWaterTempFeature.data.action.WaterTempAction
import org.koin.standalone.KoinComponent

open class WaterTempExternalDataSource(private val action: WaterTempAction) : KoinComponent {

    open fun getWaterTemp() : Double {
        return (action.getWaterTemperature()).toDouble()
    }

}