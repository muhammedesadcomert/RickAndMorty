package com.muhammedesadcomert.rickandmorty.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.muhammedesadcomert.rickandmorty.R
import com.muhammedesadcomert.rickandmorty.domain.model.Character
import com.muhammedesadcomert.rickandmorty.domain.model.Location
import com.muhammedesadcomert.rickandmorty.ui.theme.Yellow
import com.muhammedesadcomert.rickandmorty.util.CharacterGender

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navigateToCharacterDetail: (String) -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            val characterUiState by viewModel.characters.collectAsStateWithLifecycle()
            val locationLazyPagingItems = viewModel.locations.collectAsLazyPagingItems()

            LocationRow(
                modifier = Modifier,
                locationLazyPagingItems = locationLazyPagingItems,
                onButtonClick = {
                    viewModel.getMultipleCharacters(it)
                }
            )

            characterUiState.let { uiState ->
                if (uiState.isLoading) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                } else if (uiState.errorMessage != null) {
                    snackBarHostState.ShowSnackBar(errorMessage = uiState.errorMessage)
                } else if (uiState.data != null) {
                    CharacterColumn(
                        Modifier,
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
    var selectedPosition by rememberSaveable { mutableIntStateOf(-1) }

    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        itemsIndexed(
            items = locationLazyPagingItems.itemSnapshotList.items,
            key = { _: Int, location: Location -> location.id },
            contentType = { _: Int, location: Location -> location },
            itemContent = { index: Int, location: Location ->
                LocationButton(text = location.name, isSelected = selectedPosition == index) {
                    selectedPosition = index
                    onButtonClick(location.residents)
                }
            }
        )

        if (locationLazyPagingItems.loadState.append is LoadState.Loading) {
            item {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun LocationButton(text: String, isSelected: Boolean, onButtonClick: () -> Unit) {
    val (containerColor, contentColor) = if (isSelected) {
        Pair(Yellow, Color.Black)
    } else {
        Pair(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onPrimary)
    }

    Button(
        modifier = Modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        onClick = onButtonClick
    ) {
        Text(text = text)
    }
}

@Composable
fun CharacterColumn(
    modifier: Modifier,
    characters: List<Character>,
    onCardClick: (String) -> Unit
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
            if (character.name.isNotEmpty() || character.image.isNotEmpty()) {
                CharacterCard(
                    name = character.name,
                    imageUrl = character.image,
                    gender = character.gender.uppercase(),
                    onClick = {
                        onCardClick(character.id)
                    },
                )
            }
        }
    }
}

@Composable
fun CharacterCard(name: String, imageUrl: String, gender: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable {
                onClick()
            }
    ) {
        val backgroundColor: Color = when (gender) {
            CharacterGender.MALE.name -> Color.Cyan
            CharacterGender.FEMALE.name -> Color(0xFFFF7AA7)
            CharacterGender.GENDERLESS.name -> Color.Yellow
            CharacterGender.UNKNOWN.name -> Color.LightGray
            else -> Color.LightGray
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                modifier = Modifier.size(100.dp),
                model = imageUrl,
                contentDescription = stringResource(R.string.character_image),
                contentScale = ContentScale.Crop
            )
            Text(text = name, color = Color.Black, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun SnackbarHostState.ShowSnackBar(errorMessage: String) {
    LaunchedEffect(key1 = errorMessage) {
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