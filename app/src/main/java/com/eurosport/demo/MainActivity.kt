package com.eurosport.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eurosport.demo.di.PresentationFactory
import com.eurosport.demo.feature.home.ArticleListScreen
import com.eurosport.demo.feature.home.HomeContent
import com.eurosport.demo.feature.home.HomeViewModel
import com.eurosport.demo.ui.theme.EurosportTheme

class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels { PresentationFactory() }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EurosportTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(stringResource(R.string.title), color = Color.White)
                            }
                        }, colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary))
                    },
                    content = { innerPadding ->
                        // Apply the padding globally to the whole BottomNavScreensController
                        Box(modifier = Modifier.padding(innerPadding).background(MaterialTheme.colorScheme.secondary)) {
                            HomeContent(viewModel)
                            viewModel.start()
                        }
                    })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EurosportTheme {
        ArticleListScreen(emptyList())
    }
}