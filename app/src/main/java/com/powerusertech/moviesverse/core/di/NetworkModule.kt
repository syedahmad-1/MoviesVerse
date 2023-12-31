package com.powerusertech.moviesverse.core.di

import com.powerusertech.moviesverse.core.utils.Constants.BASE_URL
import com.powerusertech.moviesverse.data.models.moviedetails.MovieDetailsResponse
import com.powerusertech.moviesverse.data.network.api.MovieApi
import com.powerusertech.moviesverse.data.network.api.SearchApi
import com.powerusertech.moviesverse.data.network.api.TrendingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun providesTrendingApi(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient
    ): TrendingApi {
        return retrofitBuilder.client(okHttpClient).build()
            .create(TrendingApi::class.java)
    }

    @Singleton
    @Provides
    fun proviesSearchApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): SearchApi {
        return retrofitBuilder.client(okHttpClient).build()
            .create(SearchApi::class.java)
    }
    @Singleton
    @Provides
    fun movieApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): MovieApi {
        return retrofitBuilder.client(okHttpClient).build()
            .create(MovieApi::class.java)
    }
}