package com.satya.di

import com.satya.data.remote.ApiService
import com.satya.data.repository.UserRepositoryImpl
import com.satya.domain.repository.UserRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder().addInterceptor(logger).build()
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")// We can configure in gradel with
            // prduct flavour with BASE Host
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(api: ApiService): UserRepository = UserRepositoryImpl(api)
}
