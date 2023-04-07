package com.example.myrijks.domain.interactor

import com.example.myrijks.data.repo.CollectionRepository
import com.example.myrijks.domain.mapper.ArtDataMapper
import com.example.myrijks.ui.feature.main.model.ArtViewData
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

// TODO split the Interactor into two interactors
// One for the whole collection list and one for the
// Art object details
class CollectionInteractorImpl @Inject constructor(
    private val collectionRepository: CollectionRepository,
    private val artDataMapper: ArtDataMapper
) : CollectionInteractor {
    override fun getArtCollection(): Single<List<ArtViewData>> {
        return collectionRepository.getCollection()
            .map { it.artObjects.map(artDataMapper::mapToArtViewData) }
    }

    override fun getArtCollectionByAuthor(): Single<Map<String, List<ArtViewData>>> {
        return collectionRepository.getCollection()
            .map { artCollection ->
                artCollection.artObjects.map(artDataMapper::mapToArtViewData)
                    .groupBy { artViewData -> artViewData.principalOrFirstMaker }
            }
    }
}