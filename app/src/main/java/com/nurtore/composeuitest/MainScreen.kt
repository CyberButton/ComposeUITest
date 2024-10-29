package com.nurtore.composeuitest

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenContentsSample() {
    Scaffold(
        bottomBar = {

        },
        content = { paddingValues ->
            UserAvatar()
        }
    )
}

@Composable
private fun UserAvatar(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        imageVector = Icons.Default.Face,
        contentDescription = null
    )
}

@Composable
private fun AppointmentCard(modifier: Modifier = Modifier, docsName: String, docsPosition: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color(0xFF4894FE)
        ),
        content = {
            Column(
                modifier = Modifier.padding(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DocsCard(
                    modifier = Modifier.fillMaxWidth(),
                    docsName = docsName,
                    docsPosition = docsPosition,
                    arrowColor = Color(0xFF333344)
                )
                HorizontalDivider()
                AppointmentTime(
                    modifier = Modifier.fillMaxWidth(),
                    appointmentDate = "Sunday, 12 June",
                    appointmentTime = "11:00 - 12:00 AM"
                )
            }
        }
    )
}

@Composable
private fun AppointmentTime(
    modifier: Modifier = Modifier,
    appointmentDate: String,
    appointmentTime: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            CalendarIcon(modifier = Modifier.size(16.dp))
            AppointmentDateText(appointmentDate = appointmentDate)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ClockIcon(modifier = Modifier.size(16.dp))
            AppointmentTimeText(appointmentTime = appointmentTime)
        }
    }
}

@Composable
private fun CalendarIcon(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier,
        imageVector = Icons.Default.DateRange,
        contentDescription = "Calendar icon"
    )
}

@Composable
private fun AppointmentDateText(modifier: Modifier = Modifier, appointmentDate: String) {
    Text(
        text = appointmentDate,
        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onPrimary)
    )
}

@Composable
private fun ClockIcon(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier,
        imageVector = Icons.Outlined.Info,
        contentDescription = "Clock icon"
    )
}

@Composable
private fun AppointmentTimeText(modifier: Modifier = Modifier, appointmentTime: String) {
    Text(
        text = appointmentTime,
        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onPrimary)
    )
}

@Composable
private fun DocsCard(
    modifier: Modifier = Modifier,
    docsName: String,
    docsPosition: String,
    arrowColor: Color
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            DocsName(docsName = docsName)
            DocsPosition(positionName = docsPosition)
        }
        Arrow(modifier = Modifier.background(color = arrowColor))
    }
}

@Composable
private fun DocsName(modifier: Modifier = Modifier, docsName: String) {
    Text(
        text = docsName,
        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
    )
}

@Composable
private fun DocsPosition(modifier: Modifier = Modifier, positionName: String) {
    Text(
        text = positionName,
        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimary)
    )
}

@Composable
private fun Arrow(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Default.KeyboardArrowRight,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onPrimary
    )
}

@Preview
@PreviewLightDark
@Composable
private fun DocsCardPreview() {
    AppointmentCard(
        docsName = "Dr. Imran Syahir",
        docsPosition = "General Doctor"
    )
}