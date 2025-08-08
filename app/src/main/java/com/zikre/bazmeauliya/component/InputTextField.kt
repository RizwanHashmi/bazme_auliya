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
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun InputTextField(
//    focusRequester: FocusRequester = FocusRequester.Cancel,
    textValue: MutableState<String>,
    errorField: MutableState<Boolean>,
    label: String,
    leadingIcon: Int,
    textField: String = TEXT,
//    isSingleLine: Boolean = false,
    textColor: Color = Color(0xFF969696),
    borderColor: Color = Color(0xFFF36773),
    isEnable : Boolean =  true,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val pattern = remember { Regex("[a-zA-z\\s]*") }
    val numberPattern = remember { Regex("^\\d*\\.?\\d*\$") }  // Pattern to allow digits and an optional decimal point

    val focusRequester = remember { FocusRequester() }

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    val tfBorderColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = borderColor,
        unfocusedBorderColor = borderColor,
        focusedLabelColor = Color(0xFF969696),
        cursorColor = Color(0xFF969696),
        unfocusedTextColor = if (isSystemInDarkTheme())Color(0xFF969696)else Color(0xFF333333),
        disabledBorderColor = borderColor,
        disabledTextColor = if (isSystemInDarkTheme())Color(0xFF969696)else Color(0xFF333333)
    )

    var isFocus by remember { mutableStateOf(false) }

    val leadingIconView = @Composable {
        Image(painter = painterResource(id = leadingIcon), contentDescription = "")
    }
    val trailingIconView = @Composable {
        Image(painter = painterResource(id = leadingIcon), contentDescription = "")
    }

    OutlinedTextField(
        value = textValue.value.uppercase(),
        onValueChange = {
            when (textField) {
                TEXT -> {
                    if (it.matches(pattern)) {
                        textValue.value = it
                        errorField.value = false
                    }
                }
                NUMBER -> {
                    if (it.matches(numberPattern)) {  // Validate against the number pattern
                        textValue.value = it
                        errorField.value = false
                    }
                }
                else -> {
                    textValue.value = it
                    errorField.value = false
                }
            }

        },
        shape = RoundedCornerShape(10.dp),
        colors = tfBorderColors,
        modifier = Modifier
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewRequester)
            .focusRequester(focusRequester)
            .padding(
                top = 20.dp,
                start = 20.dp,
                end = 20.dp
            )
            .onFocusChanged {
                isFocus = it.isFocused
            },
        keyboardOptions = KeyboardOptions(
            keyboardType = when (textField) {
                NUMBER -> KeyboardType.Number
                EMAIL -> KeyboardType.Email
                else -> {
                    KeyboardType.Text
                }
            },
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
        label = {
            Text(
                text = label,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(
                            resId = R.font.poppins_semibold
                        )
                    ),
                    fontSize = if (textValue.value.isNotBlank() || isFocus) 10.sp else 14.sp,
                    color = if (isSystemInDarkTheme()) Color(0xFFFFFFFF) else textColor,
                ),
            )
        },
        maxLines = 2,
        leadingIcon = if (leadingIcon != 0) leadingIconView else null,
        trailingIcon = {
            if (isEnable){
                TrailingButton(
                    fieldName = textValue,
                    fieldError = errorField
                )
            }else{
                null
            }
        },
        isError = errorField.value,
        enabled = isEnable,
    )

    if (errorField.value) {
        coroutineScope.launch {
            bringIntoViewRequester.bringIntoView()
        }
        ShowErrorView("Please Enter $label")
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
            Image(
                painter = painterResource(id = R.drawable.ic_trailling),
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