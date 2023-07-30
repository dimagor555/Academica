package ru.dimagor555.academica.university

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.dimagor555.academica.ui.theme.AcademicaTheme

@Composable
fun UniversityDetailsScreen(university: University) {
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Факультеты в ${university.name}",
                style = MaterialTheme.typography.headlineMedium,
            )
            LazyColumn {
                items(
                    items = university.faculties,
                ) { faculty ->
                    Card(
                        modifier = Modifier.padding(vertical = 8.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = faculty.name,
                                    fontWeight = FontWeight.Bold,
                                )
                                val cost = faculty.cost.toInt()
                                val costThousands = cost / 1000
                                Text(
                                    text = "₽$costThousands тыс.",
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                            Text(
                                text = faculty.programs,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun UniversityDetailsScreenPreview() {
    AcademicaTheme {
        UniversityDetailsScreen(
            university = University(
                name = "КУБГАУ",
                photoUrl = "https://uspehbogatstvo.ru/wp-content/uploads/2020/08/kubgau-kubanskiy-gosudarstvennyy-agrarnyy-universitet-2020.jpg",
                description = "Кубанский государственный аграрный университет имени И.Т. Трубилина. Один из крупнейших аграрных университетов России",
                faculties = listOf(
                    University.Faculty(
                        name = "Бухгалтерия",
                        cost = "300 000",
                    ),
                    University.Faculty(
                        name = "Агрономия",
                        cost = "100 000",
                    ),
                )
            )
        )
    }
}