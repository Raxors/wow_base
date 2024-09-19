package com.raxors.wowbase.di

import com.raxors.wowbase.domain.interactors.CreatureInteractor
import com.raxors.wowbase.domain.repository.WoWBaseRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class InteractorModule {

    @Provides
    fun provideCreatureInteractor(repo: WoWBaseRepo): CreatureInteractor = CreatureInteractor(repo)

}