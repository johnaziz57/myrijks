package com.example.myrijks.ui.feature.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.myrijks.domain.interactor.CollectionInteractor
import com.example.myrijks.ui.feature.details.model.ArtDetailsViewData
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var collectionInteractor: CollectionInteractor

    private val schedulerProvider = com.example.myrijks.ui.viewmodel.TestSchedulerProviderImpl()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getArtObjectDetails() {
        val detailsViewModel = DetailsViewModel(collectionInteractor, schedulerProvider)

        val artDetailsViewData = mock<ArtDetailsViewData>()
        `when`(collectionInteractor.getArtObjectDetails(anyString())).thenReturn(
            Single.just(
                artDetailsViewData
            )
        )

        val observer = mock<Observer<ArtDetailsViewData>>()

        detailsViewModel.artDetailsLiveData.observeForever(observer)
        detailsViewModel.getArtObjectDetails("id")

        verify(observer).onChanged(artDetailsViewData)
    }
}