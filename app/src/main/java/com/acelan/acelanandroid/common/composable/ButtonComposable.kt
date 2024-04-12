package com.acelan.acelanandroid.common.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.acelan.acelanandroid.R

@Composable
fun BasicButton(text: String, modifier: Modifier, action: () -> Unit) {
  Button(
    onClick = action,
    modifier = modifier,
    colors =
      ButtonDefaults.buttonColors(
        contentColor = MaterialTheme.colorScheme.onPrimary
      )
  ) {
    Text(text = text, fontSize = 16.sp)
  }
}

@Composable
fun InterfaceButton(
  mainButtonOn: Boolean,
  onCancelMain: () -> Unit,
){
  IconButton(onClick = { onCancelMain() }) {
    Icon(
      painter = painterResource(if (mainButtonOn) R.drawable.outline_label_off_24 else R.drawable.outline_label_24),
      contentDescription = null,
      modifier=Modifier.fillMaxSize().padding(5.dp)
    )
  }
}
@Composable
fun SettingButton(
  onCancelMain: () -> Unit
){
  IconButton(onClick = { onCancelMain() }) {
    Icon(
      painter = painterResource( R.drawable.baseline_settings_24 ),
      contentDescription = null,
      modifier=Modifier.fillMaxSize().padding(5.dp)
    )
  }
}
@Composable
fun SaveButton(
  onCancelMain: () -> Unit
){
  IconButton(onClick = { onCancelMain() }) {
    Icon(
      painter = painterResource( R.drawable.baseline_save_24 ),
      contentDescription = null,
      modifier=Modifier.fillMaxSize().padding(5.dp)
    )
  }
}