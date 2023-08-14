package com.example.pr0812.data.module

import com.example.pr0812.data.db.SearchBookDataSource
import com.example.pr0812.data.repository.SearchBookRepositoryImpl
import com.example.pr0812.domain.repository.ISearchBookRepository
import com.example.pr0812.domain.usecase.ISearchBookUseCase
import com.example.pr0812.domain.usecase.SearchBookUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SearchDataModule {

    @Provides
    @Singleton
    fun provideSearchBookRepository(): ISearchBookRepository {
        return SearchBookRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSearchBookCase(iSearchBookRepository: ISearchBookRepository): ISearchBookUseCase {
        return SearchBookUseCaseImpl(iSearchBookRepository)
    }

}