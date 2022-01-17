package com.project.libgen.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.project.libgen.data.model.Book
import com.project.libgen.data.remote.BookDto
import com.project.libgen.data.util.Mapper
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
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://libgen.rs/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideBookDetails(retrofit: Retrofit.Builder): BookDto {
        return retrofit
            .build()
            .create(BookDto::class.java)
    }
}