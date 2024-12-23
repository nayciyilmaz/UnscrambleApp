package com.example.unscrambleapplication.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unscrambleapplication.R

@Composable
fun GameStatus(
    modifier: Modifier = Modifier,
    totalScore: Int
) {
    Card(
        modifier.padding(16.dp),
        shape = RectangleShape
    ) {
        Text(
            text = stringResource(R.string.score, totalScore),
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier.padding(8.dp)
        )
    }
}