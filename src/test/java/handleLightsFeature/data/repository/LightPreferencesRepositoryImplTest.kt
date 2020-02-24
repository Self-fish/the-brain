package handleLightsFeature.data.repository

import handleLightsFeature.data.datasource.LightPreferencesLocalDataSource
import handleLightsFeature.domain.model.LightPreferences
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.validateMockitoUsage
import kotlin.test.assertTrue

internal class LightPreferencesRepositoryImplTest {

    private val localDataSource = Mockito.mock(LightPreferencesLocalDataSource::class.java)
    private val preferencesRepository = LightPreferencesRepositoryImpl(localDataSource)

    @Test
    @DisplayName("Should retrieve information from Local Data Source")
    fun getLightsPreferences() {
        Mockito.`when`(localDataSource.getLightPreferences()).
                thenReturn(LightPreferences("09:00", "14:00"))
        preferencesRepository.getLightsPreferences()
        assertTrue(preferencesRepository.getLightsPreferences().startingHour == "09:00")
        assertTrue(preferencesRepository.getLightsPreferences().finishingHour == "14:00")

    }

    @AfterEach
    fun validate() {
        validateMockitoUsage()
    }
}