package com.project.libgen.di

import com.project.libgen.data.remote.BookDto
import com.project.libgen.data.remote.LibGenApi
import com.project.libgen.repository.LibGenBookRepository
import com.project.libgen.repository.LibGenBookRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LibGenApiModule {

    @Singleton
    @Provides
    fun provideBookDetails(retrofit: Retrofit.Builder): BookDto {
        return retrofit
            .build()
            .create(BookDto::class.java)
    }


    @Provides
    @Singleton
    fun provideLibGenApi(): LibGenApi {
        return Retrofit.Builder()
            .baseUrl("https://libgen.rs/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibGenApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLibGenBookRepository(api: LibGenApi): LibGenBookRepository {
        return LibGenBookRepositoryImpl(api)
    }

}