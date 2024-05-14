package se.ju.dimp2022.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import se.ju.dimp2022.notes.ui.theme.NotesTheme

import java.util.*

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    var isChecked: MutableState<Boolean> = mutableStateOf(false),
    val text: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val list = remember {
                mutableStateListOf(Note(title = "Example", text = "This is an example note!"))
            }

            val navController = rememberNavController()

            NotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "main_screen") {
                        composable("main_screen") {
                            MainScreen(navController = navController, list = list)
                        }
                        composable("new_note_screen") {
                            NewNoteView(navController = navController, list = list)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController, list: MutableList<Note>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        NewNoteButton(navController = navController)
        ListView(list = list)
    }
}

@Composable
fun NewNoteButton(navController: NavController, modifier: Modifier = Modifier) {
    Button(
        onClick = { navController.navigate("new_note_screen") },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(text = "NEW NOTE")
    }
}

@Composable
fun ListView(list: List<Note>) {
    LazyColumn {
        items(list) { task ->
            RowView(task)
        }
    }
}

@Composable
fun RowView(note: Note) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = note.isChecked.value,
            onCheckedChange = {
                note.isChecked.value = !note.isChecked.value
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = note.title)
            if (expanded) {
                Text(text = note.text)
            }
        }
        IconButton(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .size(48.dp)
                .padding(12.dp),
            content = {
                if (expanded) {
                    Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = null)
                } else {
                    Icon(Icons.Outlined.KeyboardArrowRight, contentDescription = null)
                }
            }
        )
    }
}


