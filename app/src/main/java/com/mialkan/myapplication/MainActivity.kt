package com.mialkan.myapplication

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mialkan.myapplication.ui.theme.ComposeTextFieldDatePickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTextFieldDatePickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TextFieldDatePickerList()
                }
            }
        }
    }
}

@Composable
fun TextFieldDatePickerList() {

    var selectedDate by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            selectedDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, 2022, 11, 17
    )

    val onSelectDate = {
        datePickerDialog.show()
    }
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
    ) {
        Text("TextFieldDatePickerWithClickable")
        TextFieldDatePickerWithClickable(selectedDate = selectedDate, onSelectDate = onSelectDate)

        Spacer(modifier = Modifier.height(20.dp))

        Text("TextFieldDatePickerWithInteractionSource")
        TextFieldDatePickerWithInteractionSource(
            selectedDate = selectedDate,
            onSelectDate = onSelectDate
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("TextFieldDatePickerWithBoxOverLay")
        TextFieldDatePickerWithBoxOverLay(selectedDate = selectedDate, onSelectDate = onSelectDate)
    }
}

// in layout system we could select date with setOnClickListener
@Composable
fun TextFieldDatePickerWithClickable(selectedDate: String?, onSelectDate: () -> Unit) {
    AppDateTextField(
        value = selectedDate.orEmpty(),
        modifier = Modifier.fillMaxWidth().clickable {
            onSelectDate.invoke()
        }
    )
}

@Composable
fun TextFieldDatePickerWithInteractionSource(selectedDate: String?, onSelectDate: () -> Unit) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    // another way to collected press
    /*LaunchedEffect(key1 = interactionSource) {
        interactionSource.interactions
            .filterIsInstance<PressInteraction>()
            .distinctUntilChanged()
            .collect {
                onSelectDate.invoke()
            }
    }*/
    // consider using collectIsFocusedAsState for ADA compatibility.
    if (interactionSource.collectIsPressedAsState().value) {
        onSelectDate.invoke()
    }

    AppDateTextField(
        value = selectedDate.orEmpty(),
        modifier = Modifier.fillMaxWidth(),
        interactionSource = interactionSource
    )
}

@Composable
fun TextFieldDatePickerWithBoxOverLay(selectedDate: String?, onSelectDate: () -> Unit) {

    Box(modifier = Modifier.fillMaxWidth()) {
        AppDateTextField(
            value = selectedDate.orEmpty(),
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier.matchParentSize().clickable {
                onSelectDate.invoke()
            }
        )
    }
}

@Composable
fun AppDateTextField(
    modifier: Modifier,
    value: String?,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    OutlinedTextField(
        modifier = modifier,
        value = value.orEmpty(),
        onValueChange = {},
        readOnly = true,
        singleLine = true,
        label = {
            Text("Date")
        },
        interactionSource = interactionSource
    )
}
