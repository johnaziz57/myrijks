package com.example.myrijks.domain.interactor

import com.example.myrijks.data.model.ArtCollection
import com.example.myrijks.data.model.ArtObject
import com.example.myrijks.data.repo.CollectionRepository
import com.example.myrijks.domain.mapper.ArtDataMapper
import com.example.myrijks.ui.feature.main.model.ArtViewData
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

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

    private var autoCloseable: AutoCloseable? = null

    @Mock
    private lateinit var collectionRepository: CollectionRepository

    @Mock
    private lateinit var artDataMapper: ArtDataMapper

    private lateinit var collectionInteractor: CollectionInteractorImpl


    @Before
    fun setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this)
        collectionInteractor =
            Mockito.spy(CollectionInteractorImpl(collectionRepository, artDataMapper))
    }

    @After
    fun tearDown() {
        autoCloseable?.close()
    }

    @Test
    fun `test get collection`() {
        val artObject = mock<ArtObject>()
        val artCollection = mock<ArtCollection> {
            on { artObjects } doReturn listOf(artObject)
        }

        val artViewData = mock<ArtViewData>()

        `when`(collectionRepository.getCollection()).thenReturn(Single.just(artCollection))
        `when`(artDataMapper.mapToArtViewData(artObject)).thenReturn(artViewData)

        val testObserver = collectionInteractor.getArtCollection().test()

        testObserver
            .assertNoErrors()
            .assertComplete()
        Assert.assertEquals(listOf(artViewData), testObserver.values()[0])
    }
}