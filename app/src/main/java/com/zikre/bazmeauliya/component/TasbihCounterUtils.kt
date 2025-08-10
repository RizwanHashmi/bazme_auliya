package com.zikre.bazmeauliya.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zikre.bazmeauliya.R
import com.zikre.bazmeauliya.features.zikr.TasbihCounterUI

@SuppressLint("RememberReturnType")
@Composable
fun TasbihCounterUtils(
    count: Int,
    onTasbihClick: () -> Unit,
    onResetClick: () -> Unit
) {
    // Your counter state
    var counter by remember { mutableStateOf(0) }

    // Will hold measured rendered image size (in px)
    var imageSizePx by remember { mutableStateOf(IntSize(0, 0)) }
    val density = LocalDensity.current

    // SVG viewport from your XML
    val viewportWidth = 370f
    val viewportHeight = 452f

    // Replace with your imported vector resource
    val vector = ImageVector.vectorResource(id = R.drawable.ic_counter)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // 1) The device image (measured)
        androidx.compose.foundation.Image(
            imageVector = vector,
            contentDescription = "device",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth(0.8f)                     // scale the image on screen
                .aspectRatio(viewportWidth / viewportHeight)
                .onGloballyPositioned { coords ->
                    imageSizePx = coords.size
                }
        )

        // 2) Once we know rendered size, add an overlay Box exactly that size and stack overlays there.
        if (imageSizePx.width > 0 && imageSizePx.height > 0) {
            // scales from SVG units -> rendered px
            val scaleX = imageSizePx.width.toFloat() / viewportWidth
            val scaleY = imageSizePx.height.toFloat() / viewportHeight

            // convert px -> dp for Compose modifiers
            val overlayWidthDp = with(density) { imageSizePx.width.toDp() }
            val overlayHeightDp = with(density) { imageSizePx.height.toDp() }

            Box(
                modifier = Modifier
                    .size(overlayWidthDp, overlayHeightDp), // exactly overlay image
                contentAlignment = Alignment.TopStart
            ) {
                // --- SCREEN AREA (from SVG path) ---
                // In your SVG:
                // "M106,79 L283,79 ... L106,161 ... L96,89 ..." -> screen bounding box approx: left=96..106, top=79, right=293, bottom=161
                // We use the rectangle corners used in the path: left=96, top=79, right=293, bottom=161.
                val screenLeftPx = 96f
                val screenTopPx = 79f
                val screenWidthPx = 293f - 96f  // 197
                val screenHeightPx = 161f - 79f // 82

                val screenLeftDp = with(density) { (screenLeftPx * scaleX).toDp() }
                val screenTopDp = with(density) { (screenTopPx * scaleY).toDp() }
                val screenWidthDp = with(density) { (screenWidthPx * scaleX).toDp() }
                val screenHeightDp = with(density) { (screenHeightPx * scaleY).toDp() }

                Box(
                    modifier = Modifier
                        .offset(x = screenLeftDp, y = screenTopDp)
                        .size(screenWidthDp, screenHeightDp)
                        .background(Color(0xFFFFFFAA), shape = RoundedCornerShape(10.dp)), // LCD background
                    contentAlignment = Alignment.Center
                ) {
                    // Ghost background
                    Text(
                        text = "00000".toString().padStart(5, '0'),
                        fontFamily = FontFamily(Font(R.font.digital_seven_mono)),
                        fontSize = 72.sp,
                        color = Color.Black.copy(alpha = 0.15f), // faint background
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Foreground counter
                    Text(
                        text = counter.toString(), // Always 5 digits
                        fontFamily = FontFamily(Font(R.font.digital_seven_mono)),
                        fontSize = 72.sp,
                        color = Color.Black, // strong active digits
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // --- LEFT SMALL BUTTON (from your SVG path) ---
                // path: "M96.5,225.5 m-27.5,0 ..." => center (96.5, 225.5), radius 27.5
                val leftCenterX = 96.5f
                val leftCenterY = 225.5f
                val leftRadius = 27.5f

                val leftTopPxX = (leftCenterX - leftRadius) * scaleX
                val leftTopPxY = (leftCenterY - leftRadius) * scaleY
                val leftSizePx = (leftRadius * 2f) * ((scaleX + scaleY) / 2f) // average scale for a circle

                val leftTopDpX = with(density) { leftTopPxX.toDp() }
                val leftTopDpY = with(density) { leftTopPxY.toDp() }
                val leftSizeDp = with(density) { leftSizePx.toDp() }

                /*Minus Button*/
                Box(
                    modifier = Modifier
                        .offset(x = leftTopDpX, y = leftTopDpY)
                        .size(leftSizeDp, leftSizeDp)
                        // .background(Color.Red.copy(alpha = 0.15f)) // <- enable for debug visual
                        .clickable {
                            // decrement (clamp to 0)
                            counter = (counter - 1).coerceAtLeast(0)
                        }
                )

                // --- RIGHT SMALL BUTTON (from your SVG path) ---
                // center (271.5,225.5), radius 27.5
                val rightCenterX = 271.5f
                val rightCenterY = 225.5f
                val rightRadius = 27.5f

                val rightTopPxX = (rightCenterX - rightRadius) * scaleX
                val rightTopPxY = (rightCenterY - rightRadius) * scaleY
                val rightSizePx = (rightRadius * 2f) * ((scaleX + scaleY) / 2f)

                val rightTopDpX = with(density) { rightTopPxX.toDp() }
                val rightTopDpY = with(density) { rightTopPxY.toDp() }
                val rightSizeDp = with(density) { rightSizePx.toDp() }

                /*Clear Button*/
                Box(
                    modifier = Modifier
                        .offset(x = rightTopDpX, y = rightTopDpY)
                        .size(rightSizeDp, rightSizeDp)
                        // .background(Color.Green.copy(alpha = 0.12f)) // <- enable to debug
                        .clip(CircleShape) // Makes ripple and click area circular
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = true,
                                radius = rightTopDpX / 2, // Covers whole circle
                                color = Color.White      // Optional: set ripple color
                            )
                        ) {
                            counter = 0
                        }
                )

                // --- LARGE BOTTOM BUTTON (big brown circle) ---
                // path: "M183,312 m-69,0 ..." => center (183,312), radius 69
                val bigCenterX = 183f
                val bigCenterY = 312f
                val bigRadius = 69f

                val bigTopPxX = (bigCenterX - bigRadius) * scaleX
                val bigTopPxY = (bigCenterY - bigRadius) * scaleY
                val bigSizePx = (bigRadius * 2f) * ((scaleX + scaleY) / 2f)

                val bigTopDpX = with(density) { bigTopPxX.toDp() }
                val bigTopDpY = with(density) { bigTopPxY.toDp() }
                val bigSizeDp = with(density) { bigSizePx.toDp() }

                /*Tab Button*/
                Box(
                    modifier = Modifier
                        .offset(x = bigTopDpX, y = bigTopDpY)
                        .size(bigSizeDp, bigSizeDp)
                        .clip(CircleShape) // Makes ripple and click area circular
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = true,
                                radius = bigSizeDp / 2, // Covers whole circle
                                color = Color.White      // Optional: set ripple color
                            )
                        ) {
                            counter += 1
                        }
                )

            } // end overlay Box
        } // end if size > 0
    } // end outer Box


}
@Preview(showBackground = true)
/*@Preview(name = "Phone - Pixel 4", device = Devices.PIXEL_4)
@Preview(name = "Tablet - Nexus 7", device = Devices.NEXUS_7)
@Preview(name = "Foldable", device = Devices.FOLDABLE)*/
@Composable
fun TasbihCounterPreview() {
    var count by remember { mutableStateOf(50) }

    TasbihCounterUtils(
        count = count,
        onTasbihClick = {
            if (count < 100) count++
        },
        onResetClick = { count = 0 }
    )
}