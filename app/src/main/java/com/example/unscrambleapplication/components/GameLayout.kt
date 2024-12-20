package com.example.unscrambleapplication.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unscrambleapplication.R
import com.example.unscrambleapplication.data.maxWordCount

@Composable
fun GameLayout(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    currentScrambleWord: String,
    keyboardOptions: KeyboardOptions,
    currentWordCount: Int,
    isError: Boolean
) {
    Card(
        modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(300.dp) // ayarlanacak
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.word_count, currentWordCount, maxWordCount),
                modifier = modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF2196F3))
                    .align(Alignment.End)
                    .padding(top = 6.dp)
                    .width(64.dp)
                    .height(32.dp),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentScrambleWord,
                fontSize = 36.sp,
            )
            Text(
                text = stringResource(R.string.description),
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.padding(top = 16.dp, bottom = 20.dp)
            )
            OutlinedTextField(
                value = value,
                onValueChange = onValueChanged,
                label = {
                    Text(
                        text = if (isError) stringResource(R.string.guess_wrong)
                        else stringResource(R.string.label)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = keyboardOptions,
                isError = isError
            )
        }
    }
}