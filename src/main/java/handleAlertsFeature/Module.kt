package handleAlertsFeature

import handleAlertsFeature.data.action.AlertsAction
import handleAlertsFeature.data.datasource.AlertsLocalDataSource
import handleAlertsFeature.data.repository.AlertsRepositoryImpl
import handleAlertsFeature.domain.HandleAlertsUseCase
import handleAlertsFeature.domain.contracts.AlertsRepository
import org.koin.dsl.module.module


val alertsModule = module {
    single { AlertsAction(get(), get()) }
    single { AlertsLocalDataSource() }
    single<AlertsRepository> { AlertsRepositoryImpl(get(), get()) }
    single { HandleAlertsUseCase(get()) }
}