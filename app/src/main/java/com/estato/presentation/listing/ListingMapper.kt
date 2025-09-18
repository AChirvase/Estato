package com.estato.presentation.listing

import com.estato.core.mvi.MviMapper
import com.estato.domain.model.RealEstate
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

class ListingMapper @Inject constructor() : MviMapper<RealEstate, RealEstateUiModel> {

    override fun mapToScreenData(domainData: RealEstate): RealEstateUiModel {
        return RealEstateUiModel(
            id = domainData.id,
            title = domainData.title,
            location = domainData.location,
            price = formatPrice(domainData.price, domainData.currency),
            imageUrl = domainData.imageUrl,
            type = domainData.type.name.lowercase().replaceFirstChar { it.uppercase() },
            bedrooms = domainData.bedrooms,
            bathrooms = domainData.bathrooms,
            area = "${domainData.area.toInt()} m²"
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