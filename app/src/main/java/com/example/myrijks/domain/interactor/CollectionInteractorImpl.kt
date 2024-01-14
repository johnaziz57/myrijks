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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
    override suspend fun getArtCollection(): Result<List<ArtEntity>> {
        return repositoryCall {
            collectionRepository.getCollection(1)
                .artObjects
                .map(artDataMapper::mapToArtEntity)
        }
    }

    override suspend fun getArtCollectionByMaker(pageIndex: Int): Result<ArtEntityMap> {
        return repositoryCall {
            collectionRepository.getCollection(pageIndex)
                .artObjects
                .map(artDataMapper::mapToArtEntity)
                .groupBy { artViewData -> artViewData.principalOrFirstMaker }

        }
    }

    override fun getArtObjectDetails(artObjectId: String): Single<Result<ArtDetailsEntity>> {
        return collectionRepository.getArtObjectDetails(artObjectId)
            .map { artObjectDetails ->
                artDetailsMapper.mapToArtDetailsEntity(artObjectDetails.artObject)
            }.toResult(errorHandler)
    }

    private suspend fun <T> repositoryCall(call: suspend () -> T): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(call.invoke())
            } catch (t: Throwable) {
                Result.Error(errorHandler.mapToError(t))
            }
        }
    }
}