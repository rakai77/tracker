package com.example.tracker.presentation.main.detail

import android.webkit.WebHistoryItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.tracker.data.database.entity.VehicleEntity
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController
) {
    val viewModel: DetailViewModel = koinViewModel()
    val historyState by viewModel.historyList.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllHistory()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Detail Screen")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    HeaderContent(
                        modifier = Modifier.padding(16.dp),
                        onClick = {}
                    )
                }
                items(historyState) { history ->
                    HistoryItem(
                        modifier = Modifier.padding(8.dp),
                        history = history
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderContent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "List Download",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        )
        IconButton(
            onClick = { onClick.invoke() }
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = "Menu",
                tint = Color.Black
            )
        }
    }

}

@Composable
fun HistoryItem(
    modifier: Modifier = Modifier,
    history: VehicleEntity
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Vehicle Location",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Speed: ${history.speed} km/h",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = "Latitude: ${history.lat}, Longitude: ${history.lng}",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Engine: ${if (history.engineStatus) "On" else "Off"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (history.engineStatus) Color.Green else Color.Red
                )
                Text(
                    text = "Door: ${if (history.doorStatus) "Open" else "Closed"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (history.doorStatus) Color.Yellow else Color.Gray
                )
            }
            Text(
                text = "Timestamp: ${formatTimestamp(history.timestamp)}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

// Format timestamp to human-readable format
fun formatTimestamp(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = Date(timestamp)
    return dateFormat.format(date)
}
