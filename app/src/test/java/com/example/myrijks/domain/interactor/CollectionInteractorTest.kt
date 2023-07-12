package com.example.myrijks.domain.interactor

import com.example.myrijks.data.model.ArtCollectionResponse
import com.example.myrijks.data.model.ArtObject
import com.example.myrijks.data.model.ArtObjectDetails
import com.example.myrijks.data.model.ArtObjectDetailsResponse
import com.example.myrijks.domain.error.ErrorHandler
import com.example.myrijks.domain.mapper.ArtDataMapper
import com.example.myrijks.domain.mapper.ArtDetailsMapper
import com.example.myrijks.domain.model.details.ArtDetailsEntity
import com.example.myrijks.domain.model.main.ArtEntity
import com.example.myrijks.domain.repo.CollectionRepository
import com.example.myrijks.domain.util.Result
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
import org.mockito.Mockito.anyString
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

    @Mock
    private lateinit var errorHandler: ErrorHandler

    private lateinit var collectionInteractor: CollectionInteractorImpl


    @Before
    fun setUp() {
        collectionInteractor =
            Mockito.spy(
                CollectionInteractorImpl(
                    collectionRepository,
                    artDataMapper,
                    artDetailsMapper,
                    errorHandler
                )
            )
    }

    @Test
    fun `test get collection`() {
        val artObject = mock<ArtObject>()
        val artCollectionResponse = mock<ArtCollectionResponse> {
            on { artObjects } doReturn listOf(artObject)
        }

        val artViewData = mock<ArtEntity> {
            on { principalOrFirstMaker } doReturn "artist"
        }

        `when`(collectionRepository.getCollection(pageIndex = anyInt())).thenReturn(
            Single.just(
                artCollectionResponse
            )
        )
        `when`(artDataMapper.mapToArtEntity(artObject)).thenReturn(artViewData)

        val testObserver = collectionInteractor.getArtCollectionByMaker(1).test()

        testObserver
            .assertNoErrors()
            .assertComplete()
        Assert.assertEquals(Result.Success(mapOf("artist" to listOf(artViewData))), testObserver.values()[0])
    }

    @Test
    fun `test get art object details`() {
        val artObjectDetails = mock<ArtObjectDetails>()
        val artObjectDetailsResponse = mock<ArtObjectDetailsResponse> {
            on { artObject } doReturn artObjectDetails
        }

        val artDetailsViewData = mock<ArtDetailsEntity>()

        `when`(collectionRepository.getArtObjectDetails(artObjectId = anyString())).thenReturn(
            Single.just(artObjectDetailsResponse)
        )

        `when`(artDetailsMapper.mapToArtDetailsEntity(artObjectDetails)).thenReturn(
            artDetailsViewData
        )

        val testObserver = collectionInteractor.getArtObjectDetails("id").test()

        testObserver
            .assertNoErrors()
            .assertComplete()
        Assert.assertEquals(Result.Success(artDetailsViewData), testObserver.values()[0])
    }
}