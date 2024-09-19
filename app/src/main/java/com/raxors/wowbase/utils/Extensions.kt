package com.raxors.wowbase.utils

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

fun LazyPagingItems<*>.isError(): Boolean =
    loadState.refresh is LoadState.Error && itemSnapshotList.isEmpty()

fun LazyPagingItems<*>.isLoading(): Boolean =
    loadState.refresh is LoadState.Loading && itemSnapshotList.isEmpty()