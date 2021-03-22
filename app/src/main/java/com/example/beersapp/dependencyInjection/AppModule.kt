package com.example.beersapp.dependencyInjection

import android.content.Context
import androidx.room.Room
import com.example.beersapp.AppDatabase
import com.example.beersapp.ui.MainAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "Bar"
        ).build()

    @Singleton
    @Provides
    fun provideItemDao(db: AppDatabase) = db.itemDao()

    @Singleton
    @Provides
    fun provideMainAdapter(
        @ApplicationContext context: Context
    ) = MainAdapter(context)
}