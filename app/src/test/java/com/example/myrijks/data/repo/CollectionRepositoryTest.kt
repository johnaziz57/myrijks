package com.example.myrijks.data.repo

import com.example.myrijks.data.api.RijksService
import com.example.myrijks.data.model.ArtCollectionResponse
import com.example.myrijks.data.model.ArtObjectDetailsResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
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

        val result = collectionRepo.getCollection(1)

        assertEquals(artCollectionResponse, result)
    }

    @Test
    fun `test get art object details`() = runTest {
        val artCollectionResponse = mock<ArtObjectDetailsResponse>()
        `when`(rijksService.getArtObjectDetails(artObjectId = anyString())).thenReturn(
            artCollectionResponse
        )

        val result = collectionRepo.getArtObjectDetails("id")

        assertEquals(artCollectionResponse, result)
    }
}