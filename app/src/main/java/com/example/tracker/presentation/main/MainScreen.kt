package com.example.tracker.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MainScreen() {

    val defaultLocation = LatLng(-6.200000, 106.816666) // Jakarta
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 14f)
    }

    val scope = rememberCoroutineScope()


    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars),
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

            }) {
                Icon(Icons.Default.Info, contentDescription = "Show Bottom Sheet")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Info")
            }
        }

    }
}