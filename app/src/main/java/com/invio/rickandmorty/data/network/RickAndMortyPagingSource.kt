package com.invio.rickandmorty.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.invio.rickandmorty.data.dto.RickAndMortyResponse
import com.invio.rickandmorty.domain.mapper.DomainMapper
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RickAndMortyPagingSource<Dto, DomainModel : Any> @Inject constructor(
    private val domainMapper: DomainMapper<Dto, DomainModel>,
    private val block: suspend (Int) -> Response<RickAndMortyResponse<Dto>>
) : PagingSource<Int, DomainModel>() {
    override val jumpingSupported: Boolean
        get() = super.jumpingSupported
    override val keyReuseSupported: Boolean
        get() = super.keyReuseSupported

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainModel> =
        try {
            val nextPage = params.key ?: 1
            val response = block(nextPage).body()
            val data = response?.results.orEmpty()
            if (data.isNotEmpty()) {
                LoadResult.Page(
                    data = domainMapper.toDomainList(data.filterNotNull()),
                    prevKey = if (nextPage >= 1) nextPage - 1 else null,
                    nextKey = if (response?.info?.next != null) nextPage + 1 else null
                )
            } else {
                LoadResult.Error(Exception("Response data is empty"))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: NoInternetException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, DomainModel>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
}