package com.muhammedesadcomert.rickandmorty.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.muhammedesadcomert.rickandmorty.data.dto.RickAndMortyResponse
import com.muhammedesadcomert.rickandmorty.domain.mapper.DomainMapper
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RickAndMortyPagingSource<Dto, DomainModel : Any> @Inject constructor(
    private val mapper: DomainMapper<Dto, DomainModel>,
    private val block: suspend (Int) -> Response<RickAndMortyResponse<Dto>>
) : PagingSource<Int, DomainModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DomainModel> =
        try {
            val nextPage = params.key ?: 1
            val response = block(nextPage).body()
            val data = response?.results
            LoadResult.Page(
                data = mapper.toDomainList(data),
                prevKey = if (nextPage >= 1) nextPage - 1 else null,
                nextKey = if (response?.info?.next != null) nextPage + 1 else null
            )
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