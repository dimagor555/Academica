package ru.dimagor555.academica.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.academica.navigation.CreditOfferOverviewConfig
import ru.dimagor555.academica.navigation.GlobalNavState
import ru.dimagor555.academica.navigation.UniversityOverviewConfig
import ru.dimagor555.academica.ui.theme.AcademicaTheme

@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Вы можете",
                style = MaterialTheme.typography.headlineMedium,
            )
            Column(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 32.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                ServiceCard(
                    serviceName = "Посмотреть ВУЗы",
                    onClick = { GlobalNavState.push(UniversityOverviewConfig) },
                )
                ServiceCard(
                    serviceName = "Подобрать профессию",
                    onClick = {},
                )
                ServiceCard(
                    serviceName = "Подобрать кредит",
                    onClick = { GlobalNavState.push(CreditOfferOverviewConfig) },
                )
                ServiceCard(
                    serviceName = "Посмотреть стажировки",
                    onClick = {},
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ServiceCard(
    serviceName: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.padding(vertical = 8.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = serviceName,
                style = MaterialTheme.typography.headlineSmall,
            )
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    AcademicaTheme {
        MainScreen()
    }
}