package net.thebookofcode.www.infinitescrolling.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.thebookofcode.www.infinitescrolling.PixabayApi
import net.thebookofcode.www.infinitescrolling.data.MainRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(
        api: PixabayApi
    ): MainRepository {
        return MainRepository(api)
    }
}