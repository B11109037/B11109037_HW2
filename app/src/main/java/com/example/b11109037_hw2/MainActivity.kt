package com.example.b11109037_hw2


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.b11109037_hw2.ui.theme.B11109037_HW2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            B11109037_HW2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AffirmationApp()
                }
            }
        }
    }
}
class Datasource() {
    fun loadScenerys(): List<Scenery> {
        return listOf(
            Scenery(1, R.string.scenery1, R.drawable.image1,25.183600990336842, 121.41140941104533,"淡水漁人碼頭"),
            Scenery(2, R.string.scenery2, R.drawable.image2,25.103422000924915, 121.54855105672759,"中正紀念堂"),
            Scenery(3, R.string.scenery3, R.drawable.image3,24.312801211172964, 120.54734450954133,"高美濕地"),
            Scenery(4, R.string.scenery4, R.drawable.image4,24.2721515072593, 120.74017202335352,"后豐鐵馬道"),
            Scenery(5, R.string.scenery5, R.drawable.image5,22.981028846805383, 120.1558031348824,"漁光島"),
            Scenery(6, R.string.scenery6, R.drawable.image6,24.144745559370534, 120.66261049100542,"審計新村"),
            Scenery(7, R.string.scenery7, R.drawable.image7,25.16602231010251, 121.57527088487198,"擎天崗" ),
            Scenery(8, R.string.scenery8, R.drawable.image8,24.68094941671015, 121.73980523339893,"星夢森林劇場"),
            Scenery(9, R.string.scenery9, R.drawable.image9,24.675597589968863, 121.76988326844537,"羅東夜市"),
            Scenery(10, R.string.scenery10, R.drawable.image10,25.05573669368193, 121.5101226157845,"霞海城隍廟"),
            // 其他风景照片
        )
    }
    fun getSceneryDescription(sceneryId: Int): Int {
        // 根据不同的id返回不同的介绍文字资源
        return when (sceneryId) {
            1 -> R.string.scenery1_description
            2 -> R.string.scenery2_description
            3 -> R.string.scenery3_description
            4 -> R.string.scenery4_description
            5 -> R.string.scenery5_description
            6 -> R.string.scenery6_description
            7 -> R.string.scenery7_description
            8 -> R.string.scenery8_description
            9 -> R.string.scenery9_description
            else -> R.string.scenery10_description
        }
    }
}
data class Scenery(
    val id: Int,
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int,
    val latitude: Double,
    val longitude: Double,
    val label: String
)

@Composable
fun SceneryCard(scenery: Scenery, modifier: Modifier = Modifier,navController: NavController) {
    Card(modifier = modifier.clickable { navController.navigate("detail/${scenery.id}") }) {
        Column {
            Image(
                painter = painterResource(scenery.imageResourceId),
                contentDescription = stringResource(scenery.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = LocalContext.current.getString(scenery.stringResourceId),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
@Composable
fun SceneryList(
    sceneryList: List<Scenery>,
    modifier: Modifier = Modifier,
   navController: NavController
) {
    LazyColumn(modifier = modifier) {
        items(sceneryList) { scenery ->
            SceneryCard(
                scenery = scenery,
                modifier = Modifier.padding(8.dp),
                navController =navController,
            )
        }
    }
}



@Composable
fun  DetailScreen(scenery: Scenery,navController: NavController) {
    val context = LocalContext.current
    val description = Datasource().getSceneryDescription(scenery.id)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = { navController.popBackStack() },
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow), // 使用自定义箭头图片
                contentDescription = "hhh",
                modifier = Modifier.size(50.dp)
            )

        }
        Spacer(modifier = Modifier.height(50.dp))
        Image(
            painter = painterResource(scenery.imageResourceId),
            contentDescription = stringResource(scenery.stringResourceId),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(description),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = { navigateToGoogleMap(context,scenery.latitude, scenery.longitude, scenery.label) },
            modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally),
        ) {
            Text("在 Google 地图中查看")
        }
    }
}
fun navigateToGoogleMap( context:Context,latitude: Double, longitude: Double, label: String) {
    val uri = "geo:$latitude,$longitude?q=$latitude,$longitude($label)"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri)).apply {
        setPackage("com.google.android.apps.maps")
    }
    context.startActivity(intent)
}

@Composable
fun AffirmationApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            SceneryList(
                sceneryList = Datasource().loadScenerys(),
                navController = navController
            )
        }
        composable(
            route = "detail/{affirmationId}",
            arguments = listOf(navArgument("affirmationId") { type = NavType.IntType })
        ) { backStackEntry ->
            val affirmationId = backStackEntry.arguments?.getInt("affirmationId")
            val affirmation = Datasource().loadScenerys().find { it.id == affirmationId }
            affirmation?.let { DetailScreen(scenery = it,
                navController = navController) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    B11109037_HW2Theme {
        AffirmationApp()
    }
}