package handleAlertsFeature

import handleAlertsFeature.data.action.AlertsAction
import handleAlertsFeature.data.datasource.AlertsNetDataSource
import handleAlertsFeature.data.mapper.AlertsDataModelMapper
import handleAlertsFeature.data.repository.AlertsRepositoryImpl
import handleAlertsFeature.domain.HandleAlertsUseCase
import handleAlertsFeature.domain.contracts.AlertsRepository
import org.koin.dsl.module.module


val alertsModule = module {
    single { AlertsAction(get(), get()) }
    single { AlertsNetDataSource(get()) }
    single { AlertsDataModelMapper() }
    single<AlertsRepository> { AlertsRepositoryImpl(get(), get(), get(), get()) }
    single { HandleAlertsUseCase(get(), get()) }
}