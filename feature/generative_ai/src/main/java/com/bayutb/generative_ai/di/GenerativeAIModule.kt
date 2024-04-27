package com.bayutb.generative_ai.di

import com.bayutb.generative_ai.data.service.GenerativeAIService
import com.bayutb.generative_ai.data.source.GenerativeAIDataSource
import com.bayutb.generative_ai.domain.repository.GenerativeAIRepository
import com.bayutb.generative_ai.domain.usecase.GenerativeAIUseCase
import com.bayutb.network.di.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object GenerativeAIModule {
    @Provides
    fun provideGenerativeAIService(retrofit: Retrofit): GenerativeAIService {
        return retrofit.create(GenerativeAIService::class.java)
    }

    @Provides
    fun provideGenerativeAIRepository(generativeAIService: GenerativeAIService): GenerativeAIRepository {
        return GenerativeAIDataSource(generativeAIService)
    }

    @Provides
    fun provideGenerativeAIUseCase(generativeAIRepository: GenerativeAIRepository): GenerativeAIUseCase {
        return GenerativeAIUseCase(generativeAIRepository)
    }
}