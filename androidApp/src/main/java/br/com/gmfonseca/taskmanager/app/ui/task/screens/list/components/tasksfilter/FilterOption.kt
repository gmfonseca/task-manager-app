package br.com.gmfonseca.taskmanager.app.ui.task.screens.list.components.tasksfilter

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gmfonseca.taskmanager.app.core.design.Color
import br.com.gmfonseca.taskmanager.app.ui.task.screens.list.model.FilterOption
import androidx.compose.ui.graphics.Color as ComposeColor

@Composable
fun FilterOption(
    option: FilterOption,
    onClick: (FilterOption) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    shape: Shape = RectangleShape,
) {
    val (
        backgroundColor,
        foregroundColor,
        defaultElevation,
        buttonModifier
    ) = style(isSelected, shape)
    val backgroundColorAnimated by animateColorAsState(targetValue = backgroundColor)

    Button(
        onClick = { if (!isSelected) onClick(option) },
        modifier = buttonModifier.then(modifier),
        elevation = ButtonDefaults.elevation(defaultElevation, defaultElevation + 8.dp),
        shape = shape,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColorAnimated)
    ) {
        Text(
            text = stringResource(id = option.textRes).uppercase(),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold.takeIf { isSelected },
            color = foregroundColor
        )
    }
}

@Preview(name = "Not Selected")
@Composable
private fun FilterOptionPreview() {
    FilterOption(
        FilterOption.PENDING,
        onClick = {},
        shape = RoundedCornerShape(8.dp)
    )
}

@Preview(name = "Selected")
@Composable
private fun FilterOptionSelectedPreview() {
    FilterOption(
        FilterOption.PENDING,
        onClick = {},
        shape = RoundedCornerShape(8.dp),
        isSelected = true,
    )
}

private fun style(isSelected: Boolean, shape: Shape) = if (isSelected) {
    val backgroundColor = Color.Yellow2

    FilterOptionStyle(
        backgroundColor = backgroundColor,
        foregroundColor = Color.TextGray2,
        defaultElevation = 8.dp,
        buttonModifier = Modifier
            .background(backgroundColor, shape)
            .border(BorderStroke(2.dp, Color.Gray3), shape)
            .padding(2.dp),
    )
} else {
    val backgroundColor = Color.Gray3

    FilterOptionStyle(
        backgroundColor = backgroundColor,
        foregroundColor = Color.TextGray1,
        defaultElevation = 0.dp,
        buttonModifier = Modifier.background(backgroundColor, shape),
    )
}

private data class FilterOptionStyle(
    val backgroundColor: ComposeColor,
    val foregroundColor: ComposeColor,
    val defaultElevation: Dp,
    val buttonModifier: Modifier
)
