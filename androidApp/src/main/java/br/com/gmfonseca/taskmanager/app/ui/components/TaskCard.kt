package br.com.gmfonseca.taskmanager.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import br.com.gmfonseca.taskmanager.shared.domain.entities.Task

@Composable
fun TaskCard(task: Task, onClick: (Task) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentHeight()
            .fillMaxWidth()
            .fillMaxHeight(),
        backgroundColor = Color(0xFFBABEBF),
        elevation = 4.dp
    ) {
        ConstraintLayout {
            val (container, button) = createRefs()

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.constrainAs(container) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(button.start)
                    width = Dimension.fillToConstraints
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = task.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color(0xFF232323),
                    )
                    Text(
                        text = task.description,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color(0xFF232323)
                    )
                }
            }

            Button(
                onClick = { onClick(task) },
                modifier = Modifier
                    .padding(end = 16.dp)
                    .wrapContentWidth()
                    .constrainAs(button) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0x00ECB600)
                ),
                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
            ) {
                Text(text = "COMPLETE", color = Color(0xFF232323))
            }
        }
    }
}

@Preview
@Composable
fun TaskCardPreview() {
    TaskCard(
        task = Task(
            id = "1",
            title = "This is a cool task title",
            description = "This is a cool task description",
        ),
        onClick = {}
    )
}