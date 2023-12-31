import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import kotlin.math.roundToInt

@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Composable
fun App() {
    DraggableText("Kotlin Multi Mobile")
    var buttonOffsetX by remember { mutableStateOf(0f) }
    var buttonOffsetY by remember { mutableStateOf(0f) }
    var imageOffsetX by remember { mutableStateOf(0f) }
    var imageOffsetY by remember { mutableStateOf(0f) }
    var greetingText by remember { mutableStateOf("Hello, World!") }
    var showImage by remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                greetingText = "Hello, ${getPlatformName()}"
                showImage = !showImage
            },
            modifier = Modifier
                .offset { IntOffset(buttonOffsetX.roundToInt(), buttonOffsetY.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumePositionChange()
                        buttonOffsetX += dragAmount.x
                        buttonOffsetY += dragAmount.y
                    }
                }
        ) {
            Text(greetingText)
        }

        if (showImage) {
            Box(
                Modifier
                    .offset { IntOffset(imageOffsetX.roundToInt(), imageOffsetY.roundToInt()) }
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consumePositionChange()
                            imageOffsetX += dragAmount.x
                            imageOffsetY += dragAmount.y
                        }
                    }
            ) {
                Image(
                    painterResource("compose-multiplatform.xml"),
                    null
                )
            }
        }
    }
}@Composable
private fun DraggableText(text: String) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Text(
        modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .wrapContentHeight()
            .wrapContentWidth()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        text = text,
        color = Color.Black
    )
}


expect fun getPlatformName(): String