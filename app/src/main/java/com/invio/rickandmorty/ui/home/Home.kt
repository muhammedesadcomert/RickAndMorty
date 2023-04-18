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
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.invio.rickandmorty.R
import com.invio.rickandmorty.domain.model.Character
import com.invio.rickandmorty.domain.model.Location
import com.invio.rickandmorty.ui.theme.Yellow
import com.invio.rickandmorty.util.CharacterGender
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
                } else if (uiState.errorMessage.isNotEmpty()) {
                    snackBarHostState.ShowSnackBar(errorMessage = uiState.errorMessage)
                } else if (uiState.data.isNotEmpty()) {
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
    var selectedPosition by remember { mutableStateOf(-1) }

    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        itemsIndexed(
            locationLazyPagingItems,
            key = { _, location -> location.id }
        ) { index, location ->
            location?.let {
                LocationButton(text = it.name, isSelected = selectedPosition == index) {
                    selectedPosition = index
                    onButtonClick(it.residents)
                }
            }
        }

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