package com.example.myrijks.domain.mapper

import com.example.myrijks.data.model.ArtObject
import com.example.myrijks.data.model.Image
import com.example.myrijks.domain.model.main.ArtEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class ArtDataMapperTest {

    private lateinit var artDataMapper: ArtDataMapper

    @Before
    fun setUp() {
        artDataMapper = ArtDataMapper()
    }

    @Test
    fun `test art data mapper`() {
        val image = mock<Image> {
            on { url } doReturn "url"
        }

        val artObject = mock<ArtObject> {
            on { id } doReturn "id"
            on { title } doReturn "title"
            on { objectNumber } doReturn "objectNumber"
            on { webImage } doReturn image
            on { principalOrFirstMaker } doReturn "maker"
        }

        val artViewData = artDataMapper.mapToArtEntity(artObject)
        val expectedArtEntity = ArtEntity(
            "id",
            "title",
            "objectNumber",
            "url",
            "maker"
        )
        assertEquals(expectedArtEntity, artViewData)
    }
}