package com.project.libgen.di

import com.project.libgen.data.remote.BaseAuthenticator
import com.project.libgen.data.remote.FirebaseAuthenticator
import com.project.libgen.repository.AuthRepository
import com.project.libgen.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AuthRepositoryModule {

    @Singleton
    @Provides
    fun provideAuthenticator(): BaseAuthenticator {
        return FirebaseAuthenticator()
    }

    @Singleton
    @Provides
    fun provideAuthRepository(authenticator: BaseAuthenticator): AuthRepository {
        return AuthRepositoryImpl(authenticator)
    }
}