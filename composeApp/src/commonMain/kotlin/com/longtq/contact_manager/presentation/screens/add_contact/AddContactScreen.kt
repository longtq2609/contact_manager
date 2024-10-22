import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.longtq.contact_manager.presentation.components.AppLoading
import com.longtq.contact_manager.presentation.components.AppTextField
import com.longtq.contact_manager.presentation.components.AppTopBar
import com.longtq.contact_manager.presentation.screens.add_contact.AddContactViewModel
import com.longtq.contact_manager.presentation.screens.add_contact.AddContactViewState

class AddContactScreen(val onContactAdded: () -> Unit) : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<AddContactViewModel>()
        MainContent(viewModel)
    }

    @Composable
    fun MainContent(addContactViewModel: AddContactViewModel) {
        val name = remember { mutableStateOf(TextFieldValue("")) }
        val phone = remember { mutableStateOf(TextFieldValue("")) }
        val email = remember { mutableStateOf(TextFieldValue("")) }
        val isShowError = remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow
        val state by addContactViewModel.viewState.collectAsState()
        val error = remember { mutableStateOf("") }
        when (state) {
            is AddContactViewState.Loading -> AppLoading()
            is AddContactViewState.Success -> {
                onContactAdded.invoke()
                navigator.pop()
            }

            is AddContactViewState.Error -> {
                isShowError.value = true
                error.value = (state as AddContactViewState.Error).error
            }

        }
        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppTopBar(title = "Add Contacts", isShowIcon = true)
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Name",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(
                    value = name.value.text,
                    onValueChange = { name.value = TextFieldValue(it) },
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Phone Number",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(
                    value = phone.value.text,
                    onValueChange = { phone.value = TextFieldValue(it) },
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Email",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppTextField(
                    value = email.value.text,
                    onValueChange = { email.value = TextFieldValue(it) },
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (isShowError.value) {
                    Text(text = error.value, color = Color.Red)
                }
            }

            Button(
                onClick = {
                    addContactViewModel.addContact(
                        name.value.text,
                        phone.value.text,
                        email.value.text
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                ),
                elevation = null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp).border(2.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            ) {
                Text("Add Contact")
            }
        }
    }
}


