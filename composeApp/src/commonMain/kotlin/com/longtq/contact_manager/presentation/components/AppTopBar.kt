package com.longtq.contact_manager.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import contact_manager.composeapp.generated.resources.Res
import contact_manager.composeapp.generated.resources.ic_selected_all
import contact_manager.composeapp.generated.resources.ic_unselected_all
import org.jetbrains.compose.resources.painterResource


@Composable
fun AppTopBar(
    title: String,
    isShowIcon: Boolean = false,
    onIconClick: (() -> Unit)? = null,
    isShowRightIcon: Boolean = false,
    onSelectedSingle: () -> Unit = {},
    isShowIconMore: Boolean = false,
    onDeleteContact: () -> Unit = {},
    onSelectedAll: () -> Unit = {},
    onUnSelectAll: () -> Unit = {},
) {
    val expanded = remember { mutableStateOf(false) }
    val showIconSelectedAll = remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = title) },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        navigationIcon = if (isShowIcon) {
            {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    modifier = Modifier.clickable { onIconClick?.invoke() },
                    contentDescription = null
                )
            }
        } else null,
        actions = {
            Spacer(modifier = Modifier.width(16.dp))
            if (isShowIconMore) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    modifier = Modifier.clickable {
                        onDeleteContact.invoke()
                    },
                    contentDescription = "Delete"
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    painterResource(Res.drawable.ic_unselected_all),
                    modifier = Modifier.clickable {
                        onUnSelectAll.invoke()
                    }.size(22.dp),
                    contentDescription = "UnSelect All"
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            if (showIconSelectedAll.value) {
                Icon(
                    painterResource(Res.drawable.ic_selected_all),
                    modifier = Modifier.clickable {
                        onSelectedAll.invoke()
                    }.size(22.dp),
                    contentDescription = "Select All"
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            if (isShowRightIcon) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    modifier = Modifier.clickable {
                        expanded.value = true
                    },
                    contentDescription = "More options"
                )
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    DropdownMenuItem(onClick = {
                        expanded.value = false
                        showIconSelectedAll.value = !showIconSelectedAll.value
                        onSelectedSingle.invoke()
                    }) {
                        Text("Select")
                    }
                }
            }

        },
        elevation = 0.dp
    )
}
