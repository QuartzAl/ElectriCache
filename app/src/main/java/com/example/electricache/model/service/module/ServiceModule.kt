package com.example.electricache.model.service.module

import com.example.electricache.model.service.AccountService
import com.example.electricache.model.service.StorageService
import com.example.electricache.model.service.impl.AccServiceImpl
import com.example.electricache.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccServiceImpl): AccountService

    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
}