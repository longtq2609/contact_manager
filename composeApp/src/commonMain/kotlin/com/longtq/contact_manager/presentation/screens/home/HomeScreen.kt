package com.longtq.contact_manager.presentation.screens.home

import AddContactScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.longtq.contact_manager.presentation.components.AppLoading
import com.longtq.contact_manager.presentation.components.AppTopBar
import com.longtq.contact_manager.presentation.components.ContactList

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<HomeViewModel>()
        MainContent(viewModel)
    }
}

@Composable
fun MainContent(viewModel: HomeViewModel) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    var showCheckbox by remember { mutableStateOf(false) }
    val fabIcon = if (isMenuExpanded) Icons.Default.Close else Icons.Default.Add
    val backgroundColor = if (isMenuExpanded) Color.White else Color.Black
    val colorTint = if (isMenuExpanded) Color.Black else Color.White
    val openAlertDialog = remember { mutableStateOf(false) }
    val state by viewModel.viewState.collectAsState()
    val navigator = LocalNavigator.currentOrThrow
    var search by remember { mutableStateOf("") }
    val isShowIconMore = viewModel.listIdChecked.value.isEmpty()
    Box(modifier = Modifier.fillMaxSize().background(Color.White).clickable {
        isMenuExpanded = false
    }) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTopBar(
                title = "Contacts",
                isShowIcon = false,
                isShowRightIcon = true,
                onSelectedSingle = {
                    showCheckbox = !showCheckbox
                    viewModel.selectAllContacts(false)
                },
                isShowIconMore = !isShowIconMore,
                onDeleteContact = {
                    viewModel.deleteSelectedContacts()
                },
                onSelectedAll = {
                    viewModel.selectAllContacts(true)
                },
                onUnSelectAll = {
                    viewModel.selectAllContacts(false)
                }
            )
            SearchBar(search = search, onValueChange = {
                search = it
                viewModel.searchContact(it)
            })
            when (state) {
                is HomeViewState.Loading -> AppLoading()
                is HomeViewState.Success -> {
                    ContactList(
                        (state as HomeViewState.Success).contact,
                        showCheckbox = showCheckbox,
                        onCheckedChange = { updatedContact ->
                            viewModel.updateContactCheckStatus(updatedContact)

                        }

                    )
                }

                is HomeViewState.Error -> {
                    Text(text = (state as HomeViewState.Error).error)
                }
            }

        }
        Box(
            modifier = Modifier.padding(16.dp).fillMaxSize(), contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = { isMenuExpanded = !isMenuExpanded },
                modifier = Modifier.padding(16.dp).border(
                    if (isMenuExpanded) {
                        2.dp
                    } else {
                        0.dp
                    }, Color.Black, CircleShape
                ),
                backgroundColor = backgroundColor,
            ) {
                Icon(fabIcon, contentDescription = "Toggle Menu", tint = colorTint)
            }

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(end = 16.dp, bottom = 72.dp)
            ) {
                AnimatedVisibility(
                    visible = isMenuExpanded,
                    enter = scaleIn(animationSpec = tween(durationMillis = 500)),
                    exit = scaleOut(animationSpec = tween(durationMillis = 500))
                ) {
                    FloatingActionButton(
                        onClick = {
                            openAlertDialog.value = true
                        }, backgroundColor = Color.Black, modifier = Modifier.padding(bottom = 8.dp)

                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Load Contact",
                            tint = Color.White
                        )
                    }
                }
                AnimatedVisibility(
                    visible = isMenuExpanded,
                    enter = scaleIn(animationSpec = tween(durationMillis = 700)),
                    exit = scaleOut(animationSpec = tween(durationMillis = 700))
                ) {
                    FloatingActionButton(
                        onClick = {
                            navigator.push(AddContactScreen(onContactAdded = {
                                viewModel.getAllContacts()
                            }))
                        }, backgroundColor = Color.Black, modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Contact",
                            tint = Color.White
                        )
                    }
                }
            }
        }
        if (openAlertDialog.value) {
            FileSelectionDialog(onDismissRequest = {
                openAlertDialog.value = false
            }, listFile = viewModel.file.value, onFileSelected = {
                viewModel.readJsonFromFile(it)
            })
        }
    }

}

@Composable
fun SearchBar(
    search: String, onValueChange: (String) -> Unit
) {

    Box(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
    ) {
        TextField(
            value = search,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search...") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = Color.Black,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            },

            )
    }
}

@Composable
fun FileSelectionDialog(
    onDismissRequest: () -> Unit, listFile: List<String>, onFileSelected: (String) -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Selected Json File",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                listFile.forEach { file ->
                    Button(
                        onClick = {
                            onDismissRequest()
                            onFileSelected.invoke(file)
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                        ),
                        elevation = null,
                        modifier = Modifier.fillMaxWidth()
                            .border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                    ) {
                        Text(text = file)
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }

        }
    }
}



