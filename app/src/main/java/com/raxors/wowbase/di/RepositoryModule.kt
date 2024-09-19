package com.raxors.wowbase.di

import com.raxors.wowbase.data.api.WoWApi
import com.raxors.wowbase.data.repository.WoWBaseRepoImpl
import com.raxors.wowbase.domain.repository.WoWBaseRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideWoWBaseRepo(api: WoWApi): WoWBaseRepo = WoWBaseRepoImpl(api)

}