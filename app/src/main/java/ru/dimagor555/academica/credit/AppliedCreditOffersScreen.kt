package ru.dimagor555.academica.credit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.ktor.client.call.*
import io.ktor.client.request.*
import ru.dimagor555.academica.ui.theme.AcademicaTheme
import ru.dimagor555.academica.university.mapToThousandsRubles

@Composable
fun AppliedCreditOffersScreen(requiredAmountInRubles: Int) {
    val bankOffers = remember { mutableStateListOf<BankOffer>() }
    LaunchedEffect(Unit) {
        bankOffers += loadBankOffers()
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
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Одобренные кредиты",
                style = MaterialTheme.typography.headlineMedium,
            )
            LazyColumn(
                contentPadding = PaddingValues(bottom = 32.dp),
            ) {
                items(
                    items = bankOffers,
                    key = { it.bankName },
                ) { bank ->
                    BankOfferCard(bank = bank.copy(maxAmountInRubles = requiredAmountInRubles))
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun BankOfferCard(bank: BankOffer) {
    Card(
        modifier = Modifier.padding(vertical = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            GlideImage(
                modifier = Modifier.requiredSize(32.dp),
                model = bank.iconUrl,
                contentDescription = null,
            ) {
                it.transform(RoundedCorners(25))
            }
            Text(
                text = bank.bankName,
                style = MaterialTheme.typography.headlineMedium,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = "${bank.interest}%",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "годовых",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Text(
                text = bank.creditName,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = bank.maxAmountInRubles.mapToThousandsRubles(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "${bank.maxTernInMonths} месяцев",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        Button(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {/*TODO*/ },
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                text = "Оформить",
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview
@Composable
private fun BankOfferCardPreview() {
    AcademicaTheme {
        BankOfferCard(
            bank = BankOffer(
                bankName = "Тинькофф",
                creditName = "На учёбу",
                interest = 13.6f,
                iconUrl = "",
                maxAmountInRubles = 300000,
                maxTernInMonths = 60,
            )
        )
    }
}