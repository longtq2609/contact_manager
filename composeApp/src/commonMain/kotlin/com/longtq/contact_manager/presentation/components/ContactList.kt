package com.longtq.contact_manager.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longtq.contact_manager.domain.model.Contact
import contact_manager.composeapp.generated.resources.Res
import contact_manager.composeapp.generated.resources.ic_avatar_1
import contact_manager.composeapp.generated.resources.ic_avatar_10
import contact_manager.composeapp.generated.resources.ic_avatar_2
import contact_manager.composeapp.generated.resources.ic_avatar_3
import contact_manager.composeapp.generated.resources.ic_avatar_4
import contact_manager.composeapp.generated.resources.ic_avatar_5
import contact_manager.composeapp.generated.resources.ic_avatar_6
import contact_manager.composeapp.generated.resources.ic_avatar_7
import contact_manager.composeapp.generated.resources.ic_avatar_8
import contact_manager.composeapp.generated.resources.ic_avatar_9
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ContactList(
    listContact: List<Contact>,
    showCheckbox: Boolean,
    onCheckedChange: (Contact) -> Unit
) {
    val groupedContacts = listContact.groupBy { it.name.firstOrNull()?.uppercase() ?: "#" }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding( vertical = 8.dp),
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        groupedContacts.forEach { (initial, contacts) ->
            item {
                Box(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .background(Color.Gray)
                        .fillMaxWidth()
                        .height(50.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = initial,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

            itemsIndexed(contacts) { index, item ->
                ContactItem(
                    contact = item,
                    showCheckbox = showCheckbox,
                    onCheckedChange = onCheckedChange
                )
            }
        }
    }
}

@Composable
fun ContactItem(
    contact: Contact, showCheckbox: Boolean, onCheckedChange: (Contact) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onCheckedChange(contact) }
                )
            }
    ) {


        if (showCheckbox) {
            Box {
                Checkbox(
                    checked = contact.isChecked ?: false,
                    onCheckedChange = { onCheckedChange(contact.copy(isChecked = it)) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Black,
                        uncheckedColor = Color.Black,
                        checkmarkColor = Color.White
                    )

                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
        Image(
            painterResource(getAvatarForType(contact.typeImage.toInt())),
            contentDescription = "User image",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = contact.name)
            Text(text = contact.phone)
        }

    }
}

@Composable
fun getAvatarForType(type: Int): DrawableResource {
    return when (type) {
        1 -> Res.drawable.ic_avatar_1
        2 -> Res.drawable.ic_avatar_2
        3 -> Res.drawable.ic_avatar_3
        4 -> Res.drawable.ic_avatar_4
        5 -> Res.drawable.ic_avatar_5
        6 -> Res.drawable.ic_avatar_6
        7 -> Res.drawable.ic_avatar_7
        8 -> Res.drawable.ic_avatar_8
        9 -> Res.drawable.ic_avatar_9
        10 -> Res.drawable.ic_avatar_10
        else -> Res.drawable.ic_avatar_1
    }
}