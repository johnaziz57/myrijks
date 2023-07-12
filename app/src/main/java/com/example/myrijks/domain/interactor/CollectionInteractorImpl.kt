package com.example.myrijks.domain.interactor

import com.example.myrijks.domain.error.ErrorHandler
import com.example.myrijks.domain.mapper.ArtDataMapper
import com.example.myrijks.domain.mapper.ArtDetailsMapper
import com.example.myrijks.domain.model.details.ArtDetailsEntity
import com.example.myrijks.domain.model.main.ArtEntity
import com.example.myrijks.domain.model.main.ArtEntityMap
import com.example.myrijks.domain.repo.CollectionRepository
import com.example.myrijks.domain.util.Result
import com.example.myrijks.domain.util.toResult
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

// TODO split the Interactor interface into two interactors
// One for the whole collection list and one for the
// Art object details
class CollectionInteractorImpl @Inject constructor(
    private val collectionRepository: CollectionRepository,
    private val artDataMapper: ArtDataMapper,
    private val artDetailsMapper: ArtDetailsMapper,
    private val errorHandler: ErrorHandler,
) : CollectionInteractor {
    override fun getArtCollection(): Single<Result<List<ArtEntity>>> {
        return collectionRepository.getCollection(1)
            .map { it.artObjects.map(artDataMapper::mapToArtEntity) }
            .toResult(errorHandler)
    }

    override fun getArtCollectionByMaker(pageIndex: Int): Single<Result<ArtEntityMap>> {
        return collectionRepository.getCollection(pageIndex)
            .map { artCollection ->
                artCollection.artObjects.map(artDataMapper::mapToArtEntity)
                    .groupBy { artViewData -> artViewData.principalOrFirstMaker }
            }.toResult(errorHandler)
    }

    override fun getArtObjectDetails(artObjectId: String): Single<Result<ArtDetailsEntity>> {
        return collectionRepository.getArtObjectDetails(artObjectId)
            .map { artObjectDetails ->
                artDetailsMapper.mapToArtDetailsEntity(artObjectDetails.artObject)
            }.toResult(errorHandler)
    }
}