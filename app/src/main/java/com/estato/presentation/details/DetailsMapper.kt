package com.estato.presentation.details

import com.estato.core.mvi.MviMapper
import com.estato.domain.model.RealEstate
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

class DetailsMapper @Inject constructor() : MviMapper<RealEstate, RealEstateDetailsUiModel> {

    override fun mapToScreenData(domainData: RealEstate): RealEstateDetailsUiModel {
        return RealEstateDetailsUiModel(
            id = domainData.id,
            title = domainData.title,
            description = domainData.description,
            price = formatPrice(domainData.price, domainData.currency),
            location = domainData.location,
            imageUrl = domainData.imageUrl,
            type = domainData.type.name.lowercase().replaceFirstChar { it.uppercase() },
            bedrooms = domainData.bedrooms,
            bathrooms = domainData.bathrooms,
            area = "${domainData.area.toInt()} m²",
            isForSale = domainData.isForSale,
            agentName = domainData.contactInfo.agentName,
            phoneNumber = domainData.contactInfo.phoneNumber,
            email = domainData.contactInfo.email
        )
    }

    private fun formatPrice(price: Double, currency: String): String {
        return when (currency.uppercase()) {
            "USD" -> {
                val formatter = NumberFormat.getCurrencyInstance(Locale.US)
                formatter.format(price)
            }
            "EUR" -> {
                val formatter = NumberFormat.getCurrencyInstance(Locale.FRANCE)
                formatter.format(price)
            }
            else -> "$currency ${NumberFormat.getInstance().format(price)}"
        }
    }
}
