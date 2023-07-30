package ru.dimagor555.academica.university

import android.os.Parcelable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import ru.dimagor555.academica.navigation.GlobalNavState
import ru.dimagor555.academica.navigation.UniversityDetailsConfig
import ru.dimagor555.academica.net.client
import ru.dimagor555.academica.ui.theme.AcademicaTheme

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UniversityOverviewScreen() {
    var universities by rememberSaveable { mutableStateOf(Universities()) }
    LaunchedEffect(Unit) {
        universities = Universities(loadUniversities())
    }
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
                text = "Университеты",
                style = MaterialTheme.typography.headlineMedium,
            )
            LazyColumn(
                contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp)
            ) {
                items(
                    items = universities.universities,
                    key = { it.name },
                ) { university ->
                    Card(
                        modifier = Modifier.padding(vertical = 8.dp),
                        onClick = { GlobalNavState.push(UniversityDetailsConfig(university)) },
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            model = university.photoUrl,
                            contentDescription = null,
                        ) {
                            it.centerCrop()
                        }
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(top = 8.dp, bottom = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = university.name,
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Center,
                            )
                            Text(text = university.description)
                        }
                    }
                }
            }
        }
    }
}

@Immutable
@Parcelize
data class Universities(
    val universities: List<University> = emptyList(),
) : Parcelable

@Parcelize
data class University(
    val name: String,
    val photoUrl: String,
    val description: String,
    val faculties: List<Faculty>,
) : Parcelable {

    @Parcelize
    data class Faculty(
        val name: String,
        val programs: String = "специалитет, бакалавриат",
        val cost: String,
    ) : Parcelable
}

private suspend fun loadUniversities(): List<University> {
    val dtos = runCatching { client.get("university").body<List<UniversityDto>>() }.getOrElse { emptyList() }
    return dtos.map { it.toUniversity() }
}

@Serializable
private data class UniversityDto(
    val name: String,
    val description: String,
    val logo: String,
    val faculties: List<FacultyDto>,
) {

    @Serializable
    data class FacultyDto(
        val name: String,
        val programs: String,
        val tuitionFees: String,
    )
}

private fun UniversityDto.toUniversity() = University(
    name = name,
    description = description,
    photoUrl = logo,
    faculties = faculties.map { it.toFaculty() },
)

private fun UniversityDto.FacultyDto.toFaculty() = University.Faculty(
    name = name,
    programs = programs,
    cost = tuitionFees,
)

@Preview
@Composable
private fun UniversityOverviewScreenPreview() {
    AcademicaTheme {
        UniversityOverviewScreen()
    }
}