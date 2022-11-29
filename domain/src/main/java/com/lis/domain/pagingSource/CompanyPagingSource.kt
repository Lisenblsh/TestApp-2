package com.lis.domain.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lis.domain.converters.ResponseToModel
import com.lis.domain.models.CompanyListModel
import com.lis.domain.models.CompanyListModelItem

class CompanyPagingSource(private val responseToModel: ResponseToModel) :
    PagingSource<Int, CompanyListModelItem>() {
    override fun getRefreshKey(state: PagingState<Int, CompanyListModelItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.nextKey?.minus(1) ?: page.prevKey?.plus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CompanyListModelItem> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        val list = responseToModel.getCompanyList()

        val prevKey = if (page == 1) null else page - 1
        val nextKey = if (list.size < pageSize) null else page + 1

        return LoadResult.Page(list, prevKey,nextKey)
    }
}