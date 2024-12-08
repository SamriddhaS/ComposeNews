package com.example.composenews.ui.interestsScreen

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.composenews.R


@Composable
fun InterestRoute(
    viewModel: InterestViewModel,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier: Modifier = Modifier) {

    InterestScreen(
        snackbarHostState = snackbarHostState,
        openDrawer = openDrawer
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterestScreen(
    snackbarHostState: SnackbarHostState,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier
)
{
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Interests",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                navigationIcon = {
                    IconButton(openDrawer) {
                        Icon(
                            painter = painterResource(R.drawable.ic_jetnews_logo),
                            contentDescription = "App Logo Compose News",
                        )
                    }
                },
                actions = {
                    IconButton(
                        {
                            Toast.makeText(context,"Feature not implemented yet",Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Feature"
                        )
                    }
                }
            )
        },
        snackbarHost = {
             SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)

    }
}