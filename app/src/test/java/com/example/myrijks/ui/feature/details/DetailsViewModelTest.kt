package com.example.myrijks.ui.feature.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.myrijks.domain.interactor.CollectionInteractor
import com.example.myrijks.domain.model.details.ArtDetailsEntity
import com.example.myrijks.domain.util.Result
import com.example.myrijks.ui.viewmodel.TestSchedulerProviderImpl
import io.reactivex.rxjava3.core.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var collectionInteractor: CollectionInteractor

    private val schedulerProvider = TestSchedulerProviderImpl()

    @Test
    fun getArtObjectDetails() {
        val detailsViewModel = DetailsViewModel(collectionInteractor, schedulerProvider)

        val artDetailsEntity = mock<ArtDetailsEntity>()
        `when`(collectionInteractor.getArtObjectDetails(anyString())).thenReturn(
            Single.just(
                Result.Success(artDetailsEntity)
            )
        )

        val observer = mock<Observer<ArtDetailsEntity>>()

        detailsViewModel.artDetailsLiveData.observeForever(observer)
        detailsViewModel.getArtObjectDetails("id")

        verify(observer).onChanged(artDetailsEntity)
    }
}