package com.example.fakeapichallenge.injection

import com.example.fakeapichallenge.Constants
import com.example.fakeapichallenge.data.remote.MovieApiService
import com.example.fakeapichallenge.data.remote.RemoteMovieRepository
import com.example.fakeapichallenge.data.remote.RemoteRepository
import com.example.fakeapichallenge.model.Movies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideConverterFactory() : GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory) : Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Singleton
    @Provides
    fun provideRetrofitMovieService(retrofit: Retrofit) : MovieApiService = retrofit.create(MovieApiService::class.java)

    @Singleton
    @Provides
    fun provideMovieRemoteRepository(movieApiService: MovieApiService): RemoteRepository<Movies> = RemoteMovieRepository(movieApiService)
}