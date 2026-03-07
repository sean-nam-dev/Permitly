package com.sean.permitly.di

import com.sean.permitly.data.error.LocalErrorMapper
import com.sean.permitly.data.log.TimberLogger
import com.sean.permitly.domain.error.DataError
import com.sean.permitly.domain.error.ErrorMapper
import com.sean.permitly.domain.logger.Logger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindLogger(
        impl: TimberLogger
    ): Logger

    @Binds
    @Singleton
    abstract fun bindLocalErrorMapper(
        impl: LocalErrorMapper
    ): ErrorMapper<DataError.Local>
}