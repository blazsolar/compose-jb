package example.todoapp.lite.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditDialog(
    item: TodoItem,
    onCloseClicked: () -> Unit,
    onTextChanged: (String) -> Unit,
    onDoneChanged: (Boolean) -> Unit,
) {
    Dialog(
        title = "Edit todo",
        onCloseRequest = onCloseClicked,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = item.text,
                modifier = Modifier.weight(1F).fillMaxWidth().sizeIn(minHeight = 192.dp),
                label = { Text("Todo text") },
                onValueChange = onTextChanged,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Text(text = "Completed", Modifier.padding(15.dp))

                Checkbox(
                    checked = item.isDone,
                    onCheckedChange = onDoneChanged,
                )
            }
        }
    }
}
