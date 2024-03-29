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
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
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
    fun `test get collection`() = runTest {
        val artObject = mock<ArtObject>()
        val artCollectionResponse = mock<ArtCollectionResponse> {
            on { artObjects } doReturn listOf(artObject)
        }

        val artViewData = mock<ArtEntity> {
            on { principalOrFirstMaker } doReturn "artist"
        }

        `when`(collectionRepository.getCollection(pageIndex = anyInt())).thenReturn(
                artCollectionResponse
        )
        `when`(artDataMapper.mapToArtEntity(artObject)).thenReturn(artViewData)

        val response = collectionInteractor.getArtCollectionByMaker(1)

        Assert.assertEquals(Result.Success(mapOf("artist" to listOf(artViewData))), response)
    }

    @Test
    fun `test get art object details`() = runTest {
        val artObjectDetails = mock<ArtObjectDetails>()
        val artObjectDetailsResponse = mock<ArtObjectDetailsResponse> {
            on { artObject } doReturn artObjectDetails
        }

        val artDetailsViewData = mock<ArtDetailsEntity>()

        `when`(collectionRepository.getArtObjectDetails(artObjectId = anyString())).thenReturn(
            artObjectDetailsResponse
        )

        `when`(artDetailsMapper.mapToArtDetailsEntity(artObjectDetails)).thenReturn(
            artDetailsViewData
        )

        val response = collectionInteractor.getArtObjectDetails("id")

        Assert.assertEquals(Result.Success(artDetailsViewData), response)
    }
}