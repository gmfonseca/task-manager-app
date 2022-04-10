package br.com.gmfonseca.taskmanager.app.ui.components.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = false,
    isRequired: Boolean = false,
) {
    val (isError, setIsError) = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        label = {
            Text(
                text = buildAnnotatedString {
                    append(label)
                    if (isRequired) withStyle(style = SpanStyle(color = Color.Red)) { append("*") }
                },
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        },
        isError = isError,
        onValueChange = { onValueChange(it); if (isRequired) setIsError(it.isBlank()) },
        placeholder = { Text(text = "Type here...") },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Yellow2,
            cursorColor = Color.Yellow2,
            focusedLabelColor = Color.Yellow3,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
    )
}

@Preview(showBackground = true)
@Composable
private fun LabeledTextFieldPreview() {
    LabeledTextField("label", "value", {})
}
