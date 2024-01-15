package com.example.myrijks.ui.feature.details

import androidx.lifecycle.Observer
import com.example.myrijks.domain.interactor.CollectionInteractor
import com.example.myrijks.domain.model.details.ArtDetailsEntity
import com.example.myrijks.domain.util.Result
import com.example.myrijks.testutils.MainDispatcherInstantRule
import com.example.myrijks.ui.viewmodel.TestSchedulerProviderImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {


    @get:Rule
    val instantExecutorRule = MainDispatcherInstantRule()

    @Mock
    lateinit var collectionInteractor: CollectionInteractor

    private val schedulerProvider = TestSchedulerProviderImpl()

    @Test
    fun getArtObjectDetails() = runTest {
        val detailsViewModel = DetailsViewModel(collectionInteractor, schedulerProvider)

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