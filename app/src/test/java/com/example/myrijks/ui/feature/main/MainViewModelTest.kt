package com.example.myrijks.ui.feature.main

import com.example.myrijks.domain.interactor.CollectionInteractor
import com.example.myrijks.domain.model.main.ArtEntity
import com.example.myrijks.domain.model.main.ArtEntityMap
import com.example.myrijks.domain.util.Result
import com.example.myrijks.testutils.MainDispatcherInstantRule
import com.example.myrijks.ui.feature.getOrAwaitValue
import com.example.myrijks.ui.feature.main.model.ArtItemWrapper
import com.example.myrijks.ui.feature.main.model.MakerItemWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = MainDispatcherInstantRule()

    @Mock
    lateinit var collectionInteractor: CollectionInteractor

    @Test
    fun `load collection`() = runTest {
        val mainViewModel = MainViewModel(collectionInteractor)

        val artViewData = mock<ArtEntity> {
            on { principalOrFirstMaker } doReturn "maker"
        }
        val map = mapOf("maker" to listOf(artViewData))
        `when`(collectionInteractor.getArtCollectionByMaker(anyInt())).thenReturn(
            Result.Success(map)
        )

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
    fun `load collection two times`() = runTest {
        val mainViewModel = MainViewModel(collectionInteractor)

        val map = mock<ArtEntityMap>()
        `when`(collectionInteractor.getArtCollectionByMaker(anyInt())).thenReturn(
            Result.Success(map)
        )

        mainViewModel.loadNextCollection()
        verify(collectionInteractor).getArtCollectionByMaker(1)
        mainViewModel.loadNextCollection()
        verify(collectionInteractor).getArtCollectionByMaker(2)
    }

    @Test
    fun `load collection two times and with same maker`() = runTest {
        val mainViewModel = MainViewModel(collectionInteractor)

        val artViewData1 = mock<ArtEntity> {
            on { id } doReturn "1"
        }
        val map1 = mapOf("maker" to listOf(artViewData1))
        `when`(collectionInteractor.getArtCollectionByMaker(1)).thenReturn(
            Result.Success(map1)
        )

        val artViewData2 = mock<ArtEntity> {
            on { id } doReturn "2"
        }
        val map2 = mapOf("maker" to listOf(artViewData2))
        `when`(collectionInteractor.getArtCollectionByMaker(2)).thenReturn(
            Result.Success(map2)
        )

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
    fun `load collection two times and with different makers`() = runTest {
        val mainViewModel = MainViewModel(collectionInteractor)

        val artViewData1 = mock<ArtEntity> {
            on { id } doReturn "1"
        }
        val map1 = mapOf("maker1" to listOf(artViewData1))
        `when`(collectionInteractor.getArtCollectionByMaker(1)).thenReturn(
            Result.Success(map1)
        )

        val artViewData2 = mock<ArtEntity> {
            on { id } doReturn "2"
        }
        val map2 = mapOf("maker2" to listOf(artViewData2))
        `when`(collectionInteractor.getArtCollectionByMaker(2)).thenReturn(
            Result.Success(map2)
        )

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