package com.invio.rickandmorty.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.invio.rickandmorty.R
import com.invio.rickandmorty.ui.home.ShowSnackBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetail(
    viewModel: CharacterDetailViewModel = hiltViewModel(),
    characterId: String,
    navigateToBack: () -> Unit
) {
    if (characterId.isNotEmpty()) {
        viewModel.getCharacter(characterId)
    }
    val character by viewModel.character.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    character.data?.let {
                        Text(text = it.name)
                    }
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .clickable { navigateToBack() },
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = stringResource(R.string.return_to_previous_screen)
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            character.let { uiState ->
                if (uiState.isLoading) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                } else if (uiState.errorMessage != null) {
                    snackBarHostState.ShowSnackBar(errorMessage = uiState.errorMessage)
                } else if (uiState.data != null) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(vertical = 20.dp, horizontal = 50.dp)
                            .size(275.dp),
                        model = uiState.data.image,
                        contentDescription = stringResource(id = R.string.character_image),
                        contentScale = ContentScale.Crop
                    )
                    AttributeRow(title = "Status: ", text = uiState.data.status)
                    AttributeRow(title = "Species: ", text = uiState.data.species)
                    AttributeRow(title = "Gender: ", text = uiState.data.gender)
                    AttributeRow(title = "Origin: ", text = uiState.data.origin)
                    AttributeRow(title = "Location: ", text = uiState.data.location)
                    AttributeRow(title = "Episodes: ", text = uiState.data.episodes)
                    AttributeRow(title = "Created at: ", text = uiState.data.created)
                }
            }
        }
    }
}

@Composable
fun AttributeRow(title: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp)
    ) {
        TitleText(text = title)
        RegularText(text = text)
    }
}

@Composable
fun TitleText(text: String) {
    Text(text = text, fontSize = 22.sp, fontWeight = FontWeight.Bold)
}

@Composable
fun RegularText(text: String) {
    Text(text = text.replaceFirstChar { it.uppercase() }, fontSize = 22.sp)
}