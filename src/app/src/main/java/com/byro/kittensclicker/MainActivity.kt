package com.byro.kittensclicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.byro.kittensclicker.ui.theme.KittensClickerTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KittensClickerTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                ) { innerPadding ->
                    Root(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Root(modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    var clicksCount by remember { mutableIntStateOf(0) }
    var autoClicks by remember { mutableIntStateOf(0) }
    var autoClickCost by remember { mutableIntStateOf(10) }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(2.seconds)
            for (i in 0 until autoClicks) {
                clicksCount++
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Score: $clicksCount", fontSize = 62.sp)
        Image(
            painter = painterResource(id = R.drawable.default_cat),
            contentDescription = stringResource(id = R.string.default_cat_description),
            modifier = Modifier
                .clickable(
                    onClick = {
                        clicksCount++
                        expanded = !expanded
                    },
                    interactionSource = interactionSource,
                    indication = null
                )
                .animateContentSize()
                .width(if (expanded) 500.dp else 400.dp)
        )
        Button(
            enabled = clicksCount >= autoClickCost,
            onClick = {
                if (clicksCount >= autoClickCost) {
                    clicksCount -= autoClickCost;
                    autoClickCost *= 10;
                    autoClicks++;
                }
            }
        ) {
            Text(text = "Buy auto-click ₡ ${autoClickCost}", fontSize = 22.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RootPreview() {
    KittensClickerTheme {
        Root()
    }
}