package com.korea200.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.korea200.R
import com.korea200.ui.theme.Typography

@Composable
fun KwordCard(
    kword: String,
    audioLink: String?,
    meaningList: List<String>?,
    egList: List<String>?,
    onAdd: () -> Unit
) {
    OutlinedCard(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(0.7f)
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(15.dp, 0.dp, 0.dp, 15.dp)) {
            /* Header */
            CardHeader(kword = kword, audioLink = audioLink, onAdd = onAdd)
            Spacer(modifier = Modifier.height(12.dp))
            /* Body */
            UnorderdList(title = "Meaning", content = meaningList, modifier = Modifier.padding(4.dp, 4.dp))
            Spacer(modifier = Modifier.height(10.dp))
            UnorderdList(title = "Examples", content = egList, modifier = Modifier.padding(4.dp, 0.dp))
            /* Footer */
            CardFooter(kword = kword)
        }
    }
}

@Composable
fun SimplifiedCard(
    kword: String,
    translateWord: (String) -> Unit,
    meaningList: List<String>?,
    modifier: Modifier = Modifier
) {
    var isClicked by remember { mutableStateOf<Boolean>(false) }

    OutlinedCard(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isClicked) Color.White else Color.White.copy(0.7f)
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier,
        onClick = {
            translateWord(kword)
            isClicked = true
        }
    ) {
        Column(modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 5.dp)) {
            Text(text = kword, style = Typography.headlineLarge, modifier = Modifier.fillMaxWidth().padding(0.dp))
            UnorderdList(
                title = "Meaning",
                content = meaningList,
                modifier = Modifier.padding(4.dp, 4.dp).fillMaxWidth()
            )
        }
    }
}

@Composable
private fun CardHeader(
    modifier: Modifier = Modifier,
    kword: String,
    audioLink: String?,
    onAdd: () -> Unit
) {
   Row (verticalAlignment = Alignment.CenterVertically){
       Text(text = kword, style = Typography.headlineLarge,)
       CardSubHeader(audioLink = audioLink)
       Spacer(Modifier.weight(1f))
       IconButton(
           onClick =  onAdd ,
           modifier = Modifier.align(Alignment.Top),
           content ={ Icon(imageVector = Icons.Outlined.Add, contentDescription = "addIcon") }
       )

   }
}

@Composable
private fun CardSubHeader(
    audioLink: String?
) {
    val uriHandler = LocalUriHandler.current

    Column (verticalArrangement = Arrangement.SpaceBetween){
        if (audioLink != null) {
            IconButton(
                onClick = { uriHandler.openUri(audioLink) },
                modifier = Modifier.offset(-10.dp, 0.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.volume_up),
                    contentDescription = "volumeUpIcon"
                )
            }
        }
        Text(text = "Source: 국립국어원", style = Typography.labelLarge)
    }
}

@Composable
private fun CardFooter(kword: String) {
    Column {
        HorizontalDivider(modifier = Modifier.padding(0.dp, 5.dp, 15.dp, 5.dp))
        Hyperlink(
            url = "https://papago.naver.com/?sk=ko&tk=en&st=$kword",
            text = "Check Papago",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 2.dp)
        )
        Hyperlink(
            url = "https://translate.google.com/?sl=ko&tl=en&text=$kword",
            text = "Check Google Translate",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCrads() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        KwordCard(
            kword = "성",
            audioLink = "",
            meaningList = listOf("Castle", "Star"),
            egList = listOf("해성", "모래성"),
            onAdd = {}
        )
        //SimplifiedCard(kword = "인도내시아", searchWord = {})
    }
}