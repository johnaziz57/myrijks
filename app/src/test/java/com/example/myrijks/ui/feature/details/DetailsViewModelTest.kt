package com.example.myrijks.ui.feature.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.myrijks.domain.interactor.CollectionInteractor
import com.example.myrijks.domain.model.details.ArtDetailsEntity
import com.example.myrijks.domain.util.Result
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.test.runTest
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

    @Test
    fun getArtObjectDetails() = runTest{
        val detailsViewModel = DetailsViewModel(collectionInteractor,)

        val artDetailsEntity = mock<ArtDetailsEntity>()
        `when`(collectionInteractor.getArtObjectDetails(anyString())).thenReturn(
                Result.Success(artDetailsEntity)

        )

        val observer = mock<Observer<ArtDetailsEntity>>()

        detailsViewModel.artDetailsLiveData.observeForever(observer)
        detailsViewModel.getArtObjectDetails("id")

        verify(observer).onChanged(artDetailsEntity)
    }
}