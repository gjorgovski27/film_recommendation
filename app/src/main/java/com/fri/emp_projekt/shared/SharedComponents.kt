package com.fri.emp_projekt.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fri.emp_projekt.R

@Composable
fun RatingStars(rating: Float) {
    Row(
        modifier = Modifier.padding(top = 4.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        for (i in 1..5) {
            val icon = if (i <= rating) R.drawable.ic_star_filled else R.drawable.ic_star_outline
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Star Rating",
                modifier = Modifier.size(16.dp),
                tint = Color.Yellow
            )
        }
    }
}
