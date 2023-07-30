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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import ru.dimagor555.academica.net.client
import ru.dimagor555.academica.ui.theme.AcademicaTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CreditOfferOverviewScreen() {
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
                text = "Предложения банков",
                style = MaterialTheme.typography.headlineMedium,
            )
            LazyColumn {
                items(
                    items = bankOffers,
                    key = { it.bankName },
                ) { bank ->
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
                                )
                                Text(
                                    text = "годовых",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Text(
                                    text = "до ${bank.maxAmountInRubles} рублей",
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(
                                    text = "на срок до ${bank.maxTernInMonths} месяцев",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

data class BankOffer(
    val bankName: String,
    val creditName: String,
    val interest: Float,
    val iconUrl: String,
    val maxAmountInRubles: Int,
    val maxTernInMonths: Int,
)

suspend fun loadBankOffers(): List<BankOffer> {
    val dtos = runCatching { client.get("bank/credits").body<List<BankOfferDto>>() }.getOrElse { emptyList() }
    return dtos.map { it.toBankOffer() }
}

@Serializable
data class BankOfferDto(
    val name: String,
    val bank: String,
    val logo: String,
    val maxAmount: Int,
    val maxTern: Int,
    val interestRate: Float,
)

fun BankOfferDto.toBankOffer() = BankOffer(
    bankName = bank,
    creditName = name,
    interest = interestRate,
    iconUrl = logo,
    maxAmountInRubles = maxAmount,
    maxTernInMonths = maxTern,
)

@Preview
@Composable
private fun CreditOfferOverviewScreenPreview() {
    AcademicaTheme {
        CreditOfferOverviewScreen()
    }
}