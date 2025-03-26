package com.example.tracker.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch


@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val vehicleViewModel: MainViewModel = viewModel()

    val vehicleLocation by vehicleViewModel.vehicleLocation.collectAsStateWithLifecycle()
    val vehicleSpeed by vehicleViewModel.vehicleSpeed.collectAsStateWithLifecycle()
    val engineStatus by vehicleViewModel.engineStatus.collectAsStateWithLifecycle()
    val doorStatus by vehicleViewModel.doorStatus.collectAsStateWithLifecycle()
    val isSimulating by vehicleViewModel.isSimulating.collectAsStateWithLifecycle()

    val defaultLocation = LatLng(-6.200000, 106.816666) // Jakarta
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 14f)
    }

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart),
            verticalArrangement =Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Button (
                onClick = {
                    cameraPositionState.move(CameraUpdateFactory.newLatLng(defaultLocation))
                },
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = "Focus Location")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Location")
            }
            Button(onClick = {
                scope.launch { bottomSheetState.show() }
            }) {
                Icon(Icons.Default.Info, contentDescription = "Show Bottom Sheet")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Info")
            }
        }

        if (bottomSheetState.isVisible) {
            ModalBottomSheet(
                onDismissRequest = { scope.launch { bottomSheetState.hide() } },
                sheetState = bottomSheetState
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Vehicle Status", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.width(8.dp))
                    if (isSimulating) {
                        Text("Lokasi: ${vehicleLocation.latitude}, ${vehicleLocation.longitude}")
                        Text("Kecepatan: $vehicleSpeed km/h")
                        Text("Mesin: ${if (engineStatus) "On" else "Off"}")
                        Text("Pintu: ${if (doorStatus) "Terbuka" else "Tertutup"}")
                        Button(onClick = { vehicleViewModel.toggleSimulation() }) {
                            Text("Stop Simulation")
                        }
                    } else {
                        Text("Simulasi belum dimulai.")
                        Button(onClick = { vehicleViewModel.toggleSimulation() }) {
                            Text("Start Simulation")
                        }
                    }
                }
            }
        }

    }
}