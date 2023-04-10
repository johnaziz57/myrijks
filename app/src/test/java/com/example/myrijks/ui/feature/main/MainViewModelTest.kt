package com.example.myrijks.ui.feature.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myrijks.domain.interactor.CollectionInteractor
import com.example.myrijks.ui.feature.getOrAwaitValue
import com.example.myrijks.ui.feature.main.model.ArtItemWrapper
import com.example.myrijks.ui.feature.main.model.ArtViewData
import com.example.myrijks.ui.feature.main.model.MakerItemWrapper
import com.example.myrijks.ui.viewmodel.TestSchedulerProviderImpl
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var collectionInteractor: CollectionInteractor

    private val schedulerProvider = TestSchedulerProviderImpl()

    @Test
    fun `load collection`() {
        val mainViewModel = MainViewModel(collectionInteractor, schedulerProvider)

        val artViewData = mock<ArtViewData> {
            on { principalOrFirstMaker } doReturn "maker"
        }
        val map = mapOf("maker" to listOf(artViewData))
        `when`(collectionInteractor.getArtCollectionByMaker(anyInt())).thenReturn(Single.just(map))

        mainViewModel.loadNextCollection()
        val value = mainViewModel.collectionLiveData.getOrAwaitValue()

        assertEquals(2, value.size)
        val firstItem = value[0]
        assertTrue(firstItem is MakerItemWrapper)
        assertEquals("maker", (firstItem as MakerItemWrapper).item.maker)

        val secondItem = value[1]
        assertTrue(secondItem is ArtItemWrapper)
        assertEquals("maker", (secondItem as ArtItemWrapper).item.principalOrFirstMaker)
    }
}