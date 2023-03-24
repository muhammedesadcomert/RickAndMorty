package com.invio.rickandmorty.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.invio.rickandmorty.R
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
            val (locations, characters, characterProgress) = createRefs()
            val locationUiState by viewModel.locations.collectAsStateWithLifecycle()
            val characterUiState by viewModel.characters.collectAsStateWithLifecycle()

            locationUiState.let { uiState ->
                if (uiState.errorMessage.isNotEmpty()) {
                    snackBarHostState.ShowSnackBar(errorMessage = uiState.errorMessage)
                } else {
                    LazyRow(
                        modifier = Modifier.constrainAs(locations) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.CenterHorizontally
                        )
                    ) {
                        if (uiState.isLoading) {
                            item {
                                CircularProgressIndicator()
                            }
                        } else if (uiState.data.isNotEmpty()) {
                            items(uiState.data) { location ->
                                LocationButton(text = location.name)
                            }
                        }
                    }
                }
            }

            characterUiState.let { uiState ->
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.constrainAs(characterProgress) {
                            top.linkTo(locations.bottom)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    )
                } else if (uiState.errorMessage.isNotEmpty()) {
                    snackBarHostState.ShowSnackBar(errorMessage = uiState.errorMessage)
                } else if (uiState.data.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .constrainAs(characters) {
                                top.linkTo(locations.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .padding(top = 8.dp, bottom = 8.dp)
                            .navigationBarsPadding(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.CenterVertically
                        )
                    ) {
                        items(uiState.data) { character ->
                            CharacterCard(
                                name = character.name,
                                imageUrl = character.image,
                                onClick = {
                                    navigateToCharacterDetail(character.id)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LocationButton(text: String) {
    Button(onClick = {}) {
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