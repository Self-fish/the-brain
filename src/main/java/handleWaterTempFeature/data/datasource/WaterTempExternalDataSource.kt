package handleWaterTempFeature.data.datasource

import handleWaterTempFeature.data.action.WaterTempAction

class WaterTempExternalDataSource(val action: WaterTempAction) {

    fun getWaterTemp() : Double {
        return (action.getWaterTemperature()).toDouble()
    }

}