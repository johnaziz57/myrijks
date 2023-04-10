package com.example.myrijks.domain.interactor

import com.example.myrijks.data.model.ArtCollectionResponse
import com.example.myrijks.data.model.ArtObject
import com.example.myrijks.data.repo.CollectionRepository
import com.example.myrijks.domain.mapper.ArtDataMapper
import com.example.myrijks.domain.mapper.ArtDetailsMapper
import com.example.myrijks.ui.feature.main.model.ArtViewData
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class CollectionInteractorTest {
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
    private lateinit var collectionRepository: CollectionRepository

    @Mock
    private lateinit var artDataMapper: ArtDataMapper

    @Mock
    private lateinit var artDetailsMapper: ArtDetailsMapper

    private lateinit var collectionInteractor: CollectionInteractorImpl


    @Before
    fun setUp() {
        collectionInteractor =
            Mockito.spy(
                CollectionInteractorImpl(
                    collectionRepository,
                    artDataMapper,
                    artDetailsMapper
                )
            )
    }

    @Test
    fun `test get collection`() {
        val artObject = mock<ArtObject>()
        val artCollectionResponse = mock<ArtCollectionResponse> {
            on { artObjects } doReturn listOf(artObject)
        }

        val artViewData = mock<ArtViewData> {
            on { principalOrFirstMaker } doReturn "artist"
        }

        `when`(collectionRepository.getCollection(pageIndex = anyInt())).thenReturn(
            Single.just(
                artCollectionResponse
            )
        )
        `when`(artDataMapper.mapToArtViewData(artObject)).thenReturn(artViewData)

        val testObserver = collectionInteractor.getArtCollectionByMaker(1).test()

        testObserver
            .assertNoErrors()
            .assertComplete()
        Assert.assertEquals(mapOf("artist" to listOf(artViewData)), testObserver.values()[0])
    }
}