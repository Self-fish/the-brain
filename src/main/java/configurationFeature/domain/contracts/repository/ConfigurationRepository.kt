package configurationFeature.domain.contracts.repository

interface ConfigurationRepository {

    fun sendCurrentTime(): Boolean

}