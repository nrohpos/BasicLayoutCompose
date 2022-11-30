package com.nrohpos.basiclayoutcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nrohpos.basiclayoutcompose.ui.theme.BasicLayoutComposeTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.magnifier
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicLayoutComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

enum class NavType {
    Home, Profile
}


@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var selectType by remember {
        mutableStateOf(NavType.Home)
    }
    Scaffold(bottomBar = { BottomNavigation(
        onClicked = { navType ->
            selectType = navType
        }
    ) }) { padding ->
        when (selectType) {
            NavType.Home -> HomeScreen(Modifier.padding(padding))
            NavType.Profile -> ProfileScreen(Modifier.padding(padding))
        }
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        Text(text = "Profile")
    }
}

@Composable
private fun HomeScreen(modifier: Modifier = Modifier) {
    var searchStr by rememberSaveable { mutableStateOf("") }
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(onValueChange = {
            searchStr = it
        }, valuesStr = searchStr, modifier = Modifier.padding(horizontal = 16.dp))
        HomeSection(title = R.string.firstSection) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            ) {
                items(7) { item ->
                    AlignYourBodyElement()
                }
            }
        }
        HomeSection(title = R.string.secondSection) {
            FavoriteCollectionsGrid()
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun BottomNavigation(
    modifier: Modifier = Modifier,
    navType: NavType = NavType.Home,
    onClicked: (type: NavType) -> Unit = {}
) {
    BottomNavigation(modifier, backgroundColor = MaterialTheme.colors.background) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.home))
            },
            selected = navType == NavType.Home,
            onClick = {
                onClicked(NavType.Home)
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.profile))
            },
            selected = navType == NavType.Profile,
            onClick = {
                onClicked(NavType.Profile)
            }
        )
    }
}

@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            stringResource(title),
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .paddingFromBaseline(top = 16.dp, bottom = 8.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    valuesStr: String = "",
    onValueChange: (String) -> Unit = {}
) {
    TextField(
        value = valuesStr,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
            )
        },
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
        placeholder = {
            Text(text = stringResource(id = R.string.search))
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
    )
}

@Composable
fun AlignYourBodyElement(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(R.string.imgProfile),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.paddingFromBaseline(top = 12.dp, bottom = 6.dp)
        )
    }
}

@Composable
fun FavoriteCollectionsGrid(
    modifier: Modifier = Modifier
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.height(112.dp)
    ) {
        items(7) { item ->
            FavoriteCollectionCard(modifier = Modifier.heightIn(min = 112.dp))
        }
    }
}


@Composable
fun FavoriteCollectionCard(
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .width(192.dp)
                .background(Color.Gray)
                .heightIn(min = 56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                contentScale = ContentScale.Crop, modifier = Modifier.size(26.dp)
            )
            Text(
                text = stringResource(R.string.imgProfile),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicLayoutComposeTheme {
        MyApp()
    }
}