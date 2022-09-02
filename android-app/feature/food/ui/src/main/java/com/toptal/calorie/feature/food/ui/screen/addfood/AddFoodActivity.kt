package com.toptal.calorie.feature.food.ui.screen.addfood

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.toptal.calorie.core.android.theme.CalorieAppTheme
import com.toptal.calorie.core.utils.ResultState
import com.toptal.calorie.feature.food.ui.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFoodActivity : ComponentActivity() {

    private val viewModel: AddFoodViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalorieAppTheme {
                Scaffold(topBar = { TopAppBar(title = { Text(text = stringResource(R.string.add_food_title)) }) },
                    content = {
                        Surface(modifier = Modifier.fillMaxSize()) {
                            val result: ResultState<Unit>? by viewModel.addFood.observeAsState(null)
                            result?.let {
                                when (it) {
                                    is ResultState.Success -> onBackPressed()
                                    else -> AddFoodScreen()
                                }
                                if (it is ResultState.Error) Toast.makeText(this@AddFoodActivity, it.message, Toast.LENGTH_SHORT).show()
                            } ?: AddFoodScreen()
                        }
                    })
            }
        }
    }

    @Composable
    private fun AddFoodScreen() {
        var foodName by remember { mutableStateOf(viewModel.foodModel.value?.name ?: "") }
        var calorie by remember { mutableStateOf(viewModel.foodModel.value?.calorie ?: "") }

        Column(Modifier.padding(20.dp)) {
            TextField(
                value = foodName,
                onValueChange = {
                    viewModel.isEnable = it.isNotBlank()
                    foodName = it
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text(stringResource(R.string.name_text)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(5.dp))
            TextField(
                value = calorie,
                onValueChange = {
                    viewModel.isEnable = it.isNotBlank()
                    calorie = it
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(stringResource(R.string.calorie_text)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(5.dp))
            Button(onClick = { viewModel.saveFood(foodName, calorie) }, enabled = !viewModel.isLoading && viewModel.isEnable) {
                Text(
                    text = stringResource(viewModel.foodButtonText),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (viewModel.isDeleteButtonVisible) {
                Spacer(Modifier.height(5.dp))
                Button(
                    onClick = { viewModel.deleteFood() },
                    enabled = !viewModel.isLoading,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text(
                        text = stringResource(R.string.delete_food_text),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White
                    )
                }
            }
        }
    }
}