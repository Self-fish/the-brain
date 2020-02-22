package showBoxHumidityFeature.domain

import showBoxHumidityFeature.domain.contract.UpdateBoxHumidityAction
import org.koin.standalone.KoinComponent

class UpdateBoxHumidityUseCase(private val action : UpdateBoxHumidityAction) : KoinComponent  {

    fun updateBoxHumidity() {
        action.updateBoxHumidity()
    }
}