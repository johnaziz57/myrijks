package com.example.myrijks.data.repo

import com.example.myrijks.data.api.RijksService
import com.example.myrijks.data.model.ArtCollection
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock

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

    private var autoCloseable: AutoCloseable? = null

    @Mock
    private lateinit var rijksService: RijksService

    private lateinit var collectionRepo: CollectionRepositoryImpl

    @Before
    fun setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this)
        collectionRepo = spy(CollectionRepositoryImpl(rijksService))
    }

    @After
    fun tearDown() {
        autoCloseable?.close()
    }

    @Test
    fun `test get collection`() {
        val collectionResponse = mock<ArtCollection>()
        `when`(rijksService.getCollection()).thenReturn(Single.just(collectionResponse))

        val testObserver = collectionRepo.getCollection().test()

        testObserver
            .assertNoErrors()
            .assertComplete()
        assertEquals(collectionResponse, testObserver.values()[0])
    }
}