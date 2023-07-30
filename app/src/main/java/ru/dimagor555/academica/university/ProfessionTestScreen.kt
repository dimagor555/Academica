package ru.dimagor555.academica.university

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dimagor555.academica.navigation.GlobalNavState
import ru.dimagor555.academica.navigation.UniversityOverviewConfig
import ru.dimagor555.academica.ui.theme.AcademicaTheme

@Composable
fun ProfessionTestScreen() {
    val questions = listOf(
        "Охотно знакомишься с новыми людьми?",
        "Хорошо ли ты ориентируешься в пространстве?",
        "Нравится ли тебе конструровать ракеты?",
    )
    val results = listOf(
        "Разработчик",
        "Менеджер",
        "Водитель",
        "Конструктор ракет",
    )
    var questionNumber by remember { mutableStateOf(0) }
    var result by remember { mutableStateOf<String?>(null) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp, top = 128.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val text = result?.let { "Вам подходит профессиия: \n\n$it" } ?: questions[questionNumber]
            Text(
                text = text,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                if (result == null) {
                    Button(
                        modifier = Modifier.padding(vertical = 12.dp),
                        onClick = {
                            if ((questionNumber + 1) in questions.indices) {
                                questionNumber++
                            } else {
                                result = results.random()
                            }
                        },
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                            text = "Да",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                    Button(
                        modifier = Modifier.padding(vertical = 12.dp),
                        onClick = {
                            if ((questionNumber + 1) in questions.indices) {
                                questionNumber++
                            } else {
                                result = results.random()
                            }
                        },
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                            text = "Нет",
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                } else {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        onClick = { GlobalNavState.push(UniversityOverviewConfig) },
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                            text = "Подобрать ВУЗы",
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfessionTestScreenPreview() {
    AcademicaTheme {
        ProfessionTestScreen()
    }
}