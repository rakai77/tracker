package com.example.tracker.di

import com.example.tracker.data.database.VehicleDatabase
import com.example.tracker.data.database.entity.VehicleRepositoryImpl
import com.example.tracker.domain.repository.VehicleRepository
import com.example.tracker.presentation.main.MainViewModel
import com.example.tracker.presentation.main.detail.DetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { VehicleDatabase.getDatabase(androidContext()).vehicleHistoryDao() }
    single<VehicleRepository> { VehicleRepositoryImpl(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}