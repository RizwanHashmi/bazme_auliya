package com.zikre.bazmeauliya.component

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.zikre.bazmeauliya.R
import com.zikre.bazmeauliya.utils.Constants.EMAIL
import com.zikre.bazmeauliya.utils.Constants.NUMBER
import com.zikre.bazmeauliya.utils.Constants.TEXT
import kotlinx.coroutines.launch
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun InputTextField(
    textValue: MutableState<String>,
    errorField: MutableState<Boolean>,
    label: String,
    leadingIcon: Int,
    textField: String = TEXT,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    isEnable: Boolean = true,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val pattern = remember { Regex("[a-zA-Z\\s]*") }
    val numberPattern = remember { Regex("^\\d*\\.?\\d*\$") }

    val focusRequester = remember { FocusRequester() }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    var isFocus by remember { mutableStateOf(false) }

    val tfColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = borderColor,
        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        errorBorderColor = MaterialTheme.colorScheme.error,
        focusedLabelColor = borderColor,
        cursorColor = borderColor,
        focusedTextColor = textColor,
        disabledTextColor = MaterialTheme.colorScheme.secondary,
        disabledBorderColor = MaterialTheme.colorScheme.secondary
    )

    OutlinedTextField(
        value = textValue.value,
        onValueChange = {
            val isValid = when (textField) {
                TEXT -> it.matches(pattern)
                NUMBER -> it.matches(numberPattern)
                else -> true
            }

            if (isValid) {
                textValue.value = it
                errorField.value = false
            } else {
                errorField.value = true
            }
        },
        shape = RoundedCornerShape(16.dp),
        colors = tfColors,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .focusRequester(focusRequester)
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusChanged { isFocus = it.isFocused },
        label = {
            Text(
                text = label,
                fontSize = if (isFocus || textValue.value.isNotBlank()) 12.sp else 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (errorField.value) MaterialTheme.colorScheme.error else textColor
            )
        },
        leadingIcon = if (leadingIcon != 0) {
            {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = null,
                    tint = if (errorField.value) MaterialTheme.colorScheme.error else borderColor
                )
            }
        } else null,
        trailingIcon = {
            if (isEnable) {
                TrailingButton(fieldName = textValue, fieldError = errorField)
            }
        },
        isError = errorField.value,
        enabled = isEnable,
        keyboardOptions = KeyboardOptions(
            keyboardType = when (textField) {
                NUMBER -> KeyboardType.Number
                EMAIL -> KeyboardType.Email
                else -> KeyboardType.Text
            },
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
        maxLines = 1,
        singleLine = true
    )

    if (errorField.value) {
        coroutineScope.launch {
            bringIntoViewRequester.bringIntoView()
        }
        ShowErrorView("Please enter a valid $label")
    }
}


@Composable
fun TrailingButton(fieldName: MutableState<String>, fieldError: MutableState<Boolean>) {
    if (fieldError.value) {
        Icon(Icons.Filled.Warning, "error", tint = MaterialTheme.colorScheme.error)
    } else if (fieldName.value.isNotBlank()) {
        IconButton(
            onClick = { fieldName.value = "" }
        ) {
            Icon(
                imageVector = Icons.Default.Close, // or Icons.Default.Clear
                contentDescription = "Clear"
            )
        }
    }
}

@Composable
fun ShowErrorView(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 20.dp)
    )
}
