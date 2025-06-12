package com.korea200.ui.components

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.korea200.ui.theme.Indigo
import com.korea200.ui.theme.Kblue
import com.korea200.ui.theme.Kred
import com.korea200.ui.theme.Typography
import com.korea200.ui.theme.linkSpanStyle
import kotlin.text.Typography.bullet


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    viewResult: (String) -> Unit
) {
    var kword by rememberSaveable { mutableStateOf("") }
    var focus by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = kword,
        onValueChange = { kword = it },
        placeholder = {Text("Enter a Korean Word")},
        singleLine = true,
        shape = RoundedCornerShape(25.dp),
        trailingIcon = {
            IconButton(
                onClick = {
                    viewResult(kword)
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "SearchIcon")
            }
        },
        modifier = modifier
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    colors = if (focus) listOf(Kred, Kblue) else listOf(
                        Color.Transparent,
                        Color.Transparent
                    )
                ),
                shape = RoundedCornerShape(25.dp)
            )
            .onFocusChanged { focusState -> focus = focusState.isFocused },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search, keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions(onSearch = {
            viewResult(kword)
            keyboardController?.hide()
            focusManager.clearFocus()
        })
    )
    //To lose the focus on the textbox when back button is hit
    BackHandler(enabled = focus) {
        keyboardController?.hide()
        focusManager.clearFocus()
    }
}

@Composable
fun LargeTitle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        /*Title*/
        Text(text = "Korea200", style = Typography.displayLarge)
        /*Outline*/
        Text(
            text = "Korea200",
            style = Typography.displayLarge.copy(
                shadow = null,
                color = Color.Black.copy(alpha = 0.5f),
                drawStyle = Stroke(width =  4f)
            )
        )
    }
}

@Composable
fun LargeText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = Typography.headlineLarge.copy(
                textAlign = TextAlign.Center,
                lineHeight = 50.sp,
                fontSize = fontSize ?: Typography.headlineLarge.fontSize
            )
        )
    }
}

@Composable
fun Hyperlink(
    url: String,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier
) {
    Text(
        buildAnnotatedString {
            withLink(
                LinkAnnotation.Url(
                    url = url,
                    styles = TextLinkStyles(
                        style = linkSpanStyle,
                        pressedStyle = linkSpanStyle.copy(color = Indigo)
                    )
                )
            ) { append(text) }
        },
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun UnorderdList(
    modifier: Modifier = Modifier,
    title: String,
    content: List<String>?
) {
    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(firstLine = 10.sp))
    Column (modifier = modifier){
        Text(text = title, style = Typography.titleLarge)
        Text(
            style = Typography.bodyLarge,
            text = buildAnnotatedString {
                content?.forEach {
                    withStyle(style = paragraphStyle) {
                        append(bullet)
                        append("\t$it")
                    }
                }
            }
        )
    }
}

@Composable
fun UserInputDialog(
    title: String,
    placeholderText: String,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card (
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ){
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(17.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ){
                /* Header */
                Text(text = title, style = Typography.headlineLarge)
                Spacer(modifier = Modifier.height(20.dp))
                /* Body */
                OutlinedTextField(
                    value = name,
                    placeholder = {Text(placeholderText)},
                    onValueChange = {name = it},
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go, keyboardType = KeyboardType.Text),
                    keyboardActions = KeyboardActions(onGo = {
                        Log.e("widgetDialog", "after")
                        onSubmit(name)
                        Log.e("widgetDialog", "before")
                    })
                )
                /* Footer */
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    TextButton(onClick = { Log.e("widgetDialog", "after")
                        onSubmit(name)
                        Log.e("widgetDialog", "before") }) { Text(text = "Submit", style = Typography.bodyLarge) }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewWidgets() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        UserInputDialog(title = "What is your name?", placeholderText = "your name?", onSubmit = {}, onDismiss = {})
    }
}