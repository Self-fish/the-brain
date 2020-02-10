package configurationFeature.domain.contracts.action

interface UpdateConfigurationAction {

    companion object {
        const val CACHE_TTL = 900000 //15 minutes
        const val ACTION = "CON"
    }

    fun updateConfiguration(currentTime: Long)

}