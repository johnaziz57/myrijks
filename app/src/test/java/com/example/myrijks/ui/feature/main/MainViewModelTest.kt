package com.example.myrijks.ui.feature.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myrijks.domain.interactor.CollectionInteractor
import com.example.myrijks.ui.feature.getOrAwaitValue
import com.example.myrijks.ui.feature.main.model.ArtDataMap
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
import org.mockito.kotlin.verify

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

    @Test
    fun `load collection two times`() {
        val mainViewModel = MainViewModel(collectionInteractor, schedulerProvider)

        val map = mock<ArtDataMap>()
        `when`(collectionInteractor.getArtCollectionByMaker(anyInt())).thenReturn(Single.just(map))

        mainViewModel.loadNextCollection()
        verify(collectionInteractor).getArtCollectionByMaker(1)
        mainViewModel.loadNextCollection()
        verify(collectionInteractor).getArtCollectionByMaker(2)
    }

    @Test
    fun `load collection two times and with same maker`() {
        val mainViewModel = MainViewModel(collectionInteractor, schedulerProvider)

        val artViewData1 = mock<ArtViewData> {
            on { id } doReturn "1"
        }
        val map1 = mapOf("maker" to listOf(artViewData1))
        `when`(collectionInteractor.getArtCollectionByMaker(1)).thenReturn(Single.just(map1))

        val artViewData2 = mock<ArtViewData> {
            on { id } doReturn "2"
        }
        val map2 = mapOf("maker" to listOf(artViewData2))
        `when`(collectionInteractor.getArtCollectionByMaker(2)).thenReturn(Single.just(map2))

        mainViewModel.loadNextCollection()
        verify(collectionInteractor).getArtCollectionByMaker(1)
        mainViewModel.loadNextCollection()
        verify(collectionInteractor).getArtCollectionByMaker(2)

        val value = mainViewModel.collectionLiveData.getOrAwaitValue()

        assertEquals(3, value.size)
        val firstItem = value[0]
        assertTrue(firstItem is MakerItemWrapper)
        assertEquals("maker", (firstItem as MakerItemWrapper).item.maker)

        val secondItem = value[1]
        assertTrue(secondItem is ArtItemWrapper)
        assertEquals("1", (secondItem as ArtItemWrapper).item.id)

        val thirdTime = value[2]
        assertTrue(thirdTime is ArtItemWrapper)
        assertEquals("2", (thirdTime as ArtItemWrapper).item.id)
    }

    @Test
    fun `load collection two times and with different makers`() {
        val mainViewModel = MainViewModel(collectionInteractor, schedulerProvider)

        val artViewData1 = mock<ArtViewData> {
            on { id } doReturn "1"
        }
        val map1 = mapOf("maker1" to listOf(artViewData1))
        `when`(collectionInteractor.getArtCollectionByMaker(1)).thenReturn(Single.just(map1))

        val artViewData2 = mock<ArtViewData> {
            on { id } doReturn "2"
        }
        val map2 = mapOf("maker2" to listOf(artViewData2))
        `when`(collectionInteractor.getArtCollectionByMaker(2)).thenReturn(Single.just(map2))

        mainViewModel.loadNextCollection()
        verify(collectionInteractor).getArtCollectionByMaker(1)
        mainViewModel.loadNextCollection()
        verify(collectionInteractor).getArtCollectionByMaker(2)

        val value = mainViewModel.collectionLiveData.getOrAwaitValue()

        assertEquals(4, value.size)
        val firstMaker = value[0]
        assertTrue(firstMaker is MakerItemWrapper)
        assertEquals("maker1", (firstMaker as MakerItemWrapper).item.maker)

        val firstItem = value[1]
        assertTrue(firstItem is ArtItemWrapper)
        assertEquals("1", (firstItem as ArtItemWrapper).item.id)

        val secondMaker = value[2]
        assertTrue(secondMaker is MakerItemWrapper)
        assertEquals("maker2", (secondMaker as MakerItemWrapper).item.maker)

        val secondItem = value[3]
        assertTrue(secondItem is ArtItemWrapper)
        assertEquals("2", (secondItem as ArtItemWrapper).item.id)
    }
}