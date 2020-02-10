package configurationFeature.domain

import configurationFeature.domain.contracts.action.UpdateConfigurationAction
import configurationFeature.domain.contracts.repository.ConfigurationRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito

internal class UpdateConfigurationUseCaseTest {

    companion object {
        const val MOCK_CURRENT_TIME = 1580640454L
    }

    val repository = Mockito.mock(ConfigurationRepository::class.java)
    val action = Mockito.mock(UpdateConfigurationAction::class.java)
    val useCase = UpdateConfigurationUseCase(action, repository)

    @Test
    @DisplayName("When update configuration then action should be executed with the current time")
    fun executeUpdateConfiguration() {
        Mockito.`when`(repository.getCurrentTime()).thenReturn(MOCK_CURRENT_TIME)
        useCase.updateConfiguration()
        Mockito.verify(repository, Mockito.times(1)).getCurrentTime()
        Mockito.verify(action, Mockito.times(1)).updateConfiguration(MOCK_CURRENT_TIME)
    }

    @AfterEach
    fun validate() {
        Mockito.validateMockitoUsage()
    }
}