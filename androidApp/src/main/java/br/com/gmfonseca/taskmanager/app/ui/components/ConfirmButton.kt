package br.com.gmfonseca.taskmanager.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.gmfonseca.taskmanager.app.core.design.Color

@Composable
fun ConfirmButton(onClick: () -> Unit, text: String, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Yellow2,
            disabledBackgroundColor = Color.Yellow3,
        ),
        enabled = enabled,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(48.dp),
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
private fun ConfirmButtonPreview() {
    ConfirmButton({}, "Confirm")
}
