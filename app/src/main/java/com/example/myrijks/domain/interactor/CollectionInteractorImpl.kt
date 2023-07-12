package com.example.myrijks.domain.interactor

import com.example.myrijks.data.repo.CollectionRepository
import com.example.myrijks.domain.mapper.ArtDataMapper
import com.example.myrijks.domain.mapper.ArtDetailsMapper
import com.example.myrijks.ui.feature.details.model.ArtDetailsViewData
import com.example.myrijks.ui.feature.main.model.ArtDataMap
import com.example.myrijks.ui.feature.main.model.ArtViewData
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

// TODO split the Interactor interface into two interactors
// One for the whole collection list and one for the
// Art object details
class CollectionInteractorImpl @Inject constructor(
    private val collectionRepository: CollectionRepository,
    private val artDataMapper: ArtDataMapper,
    private val artDetailsMapper: ArtDetailsMapper
) : CollectionInteractor {
    override fun getArtCollection(): Single<List<ArtViewData>> {
        return collectionRepository.getCollection(1)
            .map { it.artObjects.map(artDataMapper::mapToArtViewData) }
    }

    override fun getArtCollectionByMaker(pageIndex: Int): Single<ArtDataMap> {
        return collectionRepository.getCollection(pageIndex)
            .map { artCollection ->
                artCollection.artObjects.map(artDataMapper::mapToArtViewData)
                    .groupBy { artViewData -> artViewData.principalOrFirstMaker }
            }
    }

    override fun getArtObjectDetails(artObjectId: String): Single<ArtDetailsViewData> {
        return collectionRepository.getArtObjectDetails(artObjectId)
            .map { artObjectDetails ->
                artDetailsMapper.mapToArtDetailsViewData(artObjectDetails.artObject)
            }
    }
}