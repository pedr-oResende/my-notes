package br.com.mynotes.features.notes.ui.compose.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun DefaultAlertDialog(
    text: String,
    buttonText: String,
    onClick: () -> Unit,
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { setShowDialog(false) },
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = text,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.padding(24.dp))
                    CustomLargeButton(
                        text = buttonText,
                        onClick = {
                            onClick()
                            setShowDialog(false)
                        },
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}