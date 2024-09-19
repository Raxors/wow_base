package com.raxors.wowbase.ui.creatures.creature_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.raxors.wowbase.core.base.BaseContentScreen
import com.raxors.wowbase.domain.models.creature.CreatureSearch
import com.raxors.wowbase.core.base.BaseScreen
import com.raxors.wowbase.ui.common.ErrorScreen
import com.raxors.wowbase.ui.common.LoadingScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable

@Serializable
object CreatureListScreen : BaseContentScreen<CreatureListScreenState, CreatureListViewModel>() {

    @Composable
    fun Screen(
        navController: NavHostController
    ) {
        ScreenContent<CreatureListViewModel>(navController) { viewModel, state ->
            val creatures = MutableStateFlow(state.creatures).collectAsLazyPagingItems()
            CreatureListContent(creatures, viewModel)
        }
    }
}

@Composable
fun CreatureListContent(
    creatures: LazyPagingItems<CreatureSearch>,
    viewModel: CreatureListViewModel
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = creatures.itemCount,
            key = creatures.itemKey { item -> item.id }
        ) { index ->
            index.let {
                viewModel.setLoading(false)
                val item = creatures[it]
                item?.let {
                    CreatureSearchItem(creature = it)
                }
            }
        }
        creatures.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    viewModel.setLoading(true)
                }

                loadState.refresh is LoadState.Error -> {
                    val error = creatures.loadState.refresh as LoadState.Error
                    viewModel.handleError(error.error)
                }

                loadState.append is LoadState.Loading -> {
                    /*LoadingNextPageItem(modifier = Modifier)*/
                }

                loadState.append is LoadState.Error -> {
                    val error = creatures.loadState.append as LoadState.Error
                    viewModel.handleError(error.error)
                }
            }
        }
    }
}

@Composable
fun CreatureSearchItem(creature: CreatureSearch) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(256.dp)
    ) {

        // TODO Сделать чтобы было красиво без хардкодных значений высот
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().clipToBounds(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth()
                    .height((128+48).dp),
                model = creature.imagePath,
                contentDescription = creature.name,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = creature.name ?: "",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = creature.type?.name ?: "",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
            Text(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                text = creature.family?.name ?: "",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1
            )
        }
    }
}