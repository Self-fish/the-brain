package handleWaterTempFeature.data.datasource

class WaterTempLocalDataSource {

    open var waterTemp: Double = 0.0
    open var lastUpdateTimeStamp : Long = 0

    fun updateCache(temp : Double) {
        waterTemp = temp
        lastUpdateTimeStamp = System.currentTimeMillis()
    }
}