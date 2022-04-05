package br.com.gmfonseca.taskmanager.app.ui.components.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.app.core.design.Color

@Composable
fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false
) {
    val (isError, setIsError) = remember { mutableStateOf(false) }

    Column(modifier) {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 12.sp)

        OutlinedTextField(
            value = value,
            isError = isError,
            onValueChange = { onValueChange(it); if (isRequired) setIsError(it.isBlank()) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Yellow2,
                cursorColor = Color.Yellow2,
            ),

            )
    }
}

@Preview(showBackground = true)
@Composable
private fun LabeledTextFieldPreview() {
    LabeledTextField("label", "value", {})
}
