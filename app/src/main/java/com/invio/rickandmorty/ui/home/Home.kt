package com.invio.rickandmorty.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.invio.rickandmorty.R
import com.invio.rickandmorty.domain.model.Character
import com.invio.rickandmorty.domain.model.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(viewModel: HomeViewModel = hiltViewModel(), navigateToCharacterDetail: (Int) -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
            )
        }
    ) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            val (locations, characters) = createRefs()
            val characterLazyPagingItems = viewModel.getCharacters().collectAsLazyPagingItems()
            val locationLazyPagingItems = viewModel.getLocations().collectAsLazyPagingItems()

            LocationRow(
                modifier = Modifier.constrainAs(locations) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                locationLazyPagingItems = locationLazyPagingItems,
                onButtonClick = {
//                    viewModel.getCharactersByLocation(it)
                }
            )

            CharacterColumn(
                Modifier.constrainAs(characters) {
                    top.linkTo(locations.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                characterLazyPagingItems,
                onCardClick = { id ->
                    navigateToCharacterDetail(id)
                }
            )
        }
    }
}

@Composable
fun LocationRow(
    modifier: Modifier,
    locationLazyPagingItems: LazyPagingItems<Location>,
    onButtonClick: (Int) -> Unit
) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        items(locationLazyPagingItems, key = { it.id }) { location ->
            LocationButton(text = location?.name.orEmpty()) {
                onButtonClick(location?.id ?: 0)
            }
        }
    }
}

@Composable
fun CharacterColumn(
    modifier: Modifier,
    characterLazyPagingItems: LazyPagingItems<Character>,
    onCardClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        items(
            characterLazyPagingItems,
            key = {
                it.id
            },
        ) { character ->
            CharacterCard(
                name = character?.name.orEmpty(),
                imageUrl = character?.image.orEmpty(),
                onClick = {
                    onCardClick(character?.id ?: 0)
                },
            )
        }
    }
}

@Composable
fun LocationButton(text: String, onButtonClick: () -> Unit) {
    Button(onClick = onButtonClick) {
        Text(text = text)
    }
}

@Composable
fun CharacterCard(name: String, imageUrl: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                onClick()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            AsyncImage(
                modifier = Modifier.size(100.dp),
                model = imageUrl,
                contentDescription = stringResource(R.string.character_image),
                contentScale = ContentScale.Crop
            )
            Text(text = name)
        }
    }
}