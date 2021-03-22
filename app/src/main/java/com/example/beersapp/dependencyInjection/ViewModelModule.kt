package com.example.beersapp.dependencyInjection

import com.example.beersapp.data.DataSourceImpl
import com.example.beersapp.domain.DataSource
import com.example.beersapp.domain.Repository
import com.example.beersapp.domain.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ViewModelModule {

    @Binds //Indicate the class witch interface "Repository" is implemented
    abstract fun bindRepositoryImpl(repositoryImpl: RepositoryImpl): Repository

    @Binds //Indicate the class witch interface "DataSource" is implemented
    abstract fun bindDataSourceImpl(dataSourceImpl: DataSourceImpl): DataSource
}