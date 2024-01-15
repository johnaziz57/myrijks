package com.example.myrijks.data.repo

import com.example.myrijks.data.api.RijksService
import com.example.myrijks.data.model.ArtCollectionResponse
import com.example.myrijks.data.model.ArtObjectDetailsResponse
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.test.runTest
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyString
import org.mockito.Mockito.spy
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class CollectionRepositoryTest {

    companion object {
        @BeforeClass
        @JvmStatic
        fun before() {
            RxAndroidPlugins.reset()
            RxJavaPlugins.reset()
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }

        @AfterClass
        @JvmStatic
        fun after() {
            RxAndroidPlugins.reset()
            RxJavaPlugins.reset()
        }
    }

    @Mock
    private lateinit var rijksService: RijksService

    private lateinit var collectionRepo: CollectionRepositoryImpl

    @Before
    fun setUp() {
        collectionRepo = spy(CollectionRepositoryImpl(rijksService))
    }

    @Test
    fun `test get collection`() = runTest {
        val artCollectionResponse = mock<ArtCollectionResponse>()
        `when`(rijksService.getCollection(sort = anyString(), pageIndex = anyInt())).thenReturn(
            artCollectionResponse
        )

        val response = collectionRepo.getCollection(1)
        assertEquals(artCollectionResponse, response)
    }

    @Test
    fun `test get art object details`() = runTest {
        val artCollectionResponse = mock<ArtObjectDetailsResponse>()
        `when`(rijksService.getArtObjectDetails(artObjectId = anyString())).thenReturn(
            artCollectionResponse
        )

        val response = collectionRepo.getArtObjectDetails("id")

        assertEquals(artCollectionResponse, response)
    }
}