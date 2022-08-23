package br.com.mynotes.features.notes.presentation.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomLargeButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    color: Color,
    buttonHeight: Dp = 48.dp,
    borderStroke: Dp = 2.dp,
    shape: Shape = MaterialTheme.shapes.large,
    outlined: Boolean = false,
    enabled: Boolean = true,
    style: TextStyle = MaterialTheme.typography.button
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(buttonHeight),
        onClick = onClick,
        colors = if (outlined)
            ButtonDefaults.outlinedButtonColors(contentColor = color, disabledContentColor = color)
        else
            ButtonDefaults.buttonColors(backgroundColor = color, disabledContentColor = color),
        border = if (outlined)
            BorderStroke(
                width = borderStroke,
                color = color
            )
        else
            null,
        shape = shape,
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
        enabled = enabled
    ) {
        Text(text = text, style = style)
    }
}