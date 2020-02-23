package handleWaterTempFeature.data.datasource

import org.koin.standalone.KoinComponent

open class WaterTempLocalDataSource : KoinComponent {

    open var waterTemp: Double = 0.0
    open var lastUpdateTimeStamp : Long = 0

    fun updateCache(temp : Double) {
        waterTemp = temp
        lastUpdateTimeStamp = System.currentTimeMillis()
    }
}