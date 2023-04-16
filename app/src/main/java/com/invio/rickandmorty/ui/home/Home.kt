package com.invio.rickandmorty.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.invio.rickandmorty.R
import com.invio.rickandmorty.domain.model.Character
import com.invio.rickandmorty.domain.model.Location
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(viewModel: HomeViewModel = hiltViewModel(), navigateToCharacterDetail: (Int) -> Unit) {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
            )
        },
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                hostState = snackBarHostState
            ) { data ->
                Snackbar(
                    action = {
                        TextButton(
                            onClick = {
                                viewModel.run {
                                    getCharacters()
                                    getLocations()
                                }
                            },
                        ) {
                            Text(text = stringResource(R.string.retry))
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = data.visuals.message
                    )
                }
            }
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
            val (locationRow, characterColumn, characterProgress) = createRefs()
            val characterUiState by viewModel.characters.collectAsStateWithLifecycle()
            val locationLazyPagingItems = viewModel.getLocations().collectAsLazyPagingItems()

            LocationRow(
                modifier = Modifier.constrainAs(locationRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                locationLazyPagingItems = locationLazyPagingItems,
                onButtonClick = {
                    viewModel.getMultipleCharacters(it)
                }
            )

            characterUiState.let { uiState ->
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.constrainAs(characterProgress) {
                            top.linkTo(locationRow.bottom)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    )
                } else if (uiState.errorMessage.isNotEmpty()) {
                    snackBarHostState.ShowSnackBar(errorMessage = uiState.errorMessage)
                } else if (uiState.data.isNotEmpty()) {
                    CharacterColumn(
                        Modifier.constrainAs(characterColumn) {
                            top.linkTo(locationRow.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        uiState.data,
                        onCardClick = { id ->
                            navigateToCharacterDetail(id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LocationRow(
    modifier: Modifier,
    locationLazyPagingItems: LazyPagingItems<Location>,
    onButtonClick: (List<String>) -> Unit
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
                onButtonClick(location?.residents.orEmpty())
            }
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
fun CharacterColumn(
    modifier: Modifier,
    characters: List<Character>,
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
        items(characters) { character ->
            CharacterCard(
                name = character.name,
                imageUrl = character.image,
                onClick = {
                    onCardClick(character.id)
                },
            )
        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnackbarHostState.ShowSnackBar(errorMessage: String) {
    LaunchedEffect(Unit) {
        launch {
            this@ShowSnackBar.showSnackbar(
                object : SnackbarVisuals {
                    override val actionLabel: String
                        get() = ""
                    override val duration: SnackbarDuration
                        get() = SnackbarDuration.Indefinite
                    override val message: String
                        get() = errorMessage
                    override val withDismissAction: Boolean
                        get() = false
                }
            )
        }
    }
}