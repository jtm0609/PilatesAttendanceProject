package com.example.cmong_pilates_attendance_project.view.admin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cmong_pilates_attendance_project.R
import com.example.cmong_pilates_attendance_project.base.BaseFragment
import com.example.cmong_pilates_attendance_project.utils.LogUtil
import com.example.cmong_pilates_attendance_project.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InputPhoneNumberFragment : BaseFragment() {
    private val userViewModel : UserViewModel by activityViewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(mContext).apply {
            setContent { mainView() }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataObserver()
    }

    private fun setDataObserver(){
        userViewModel.searchedUser.observe(viewLifecycleOwner){
            if(it==null) return@observe
            if(userViewModel.isExistUser.value==true){
                findNavController().navigate(R.id.action_inputPhoneNumberFragment_to_manageUserFragment)
            }
        }

        userViewModel.isExistUser.observe(viewLifecycleOwner){
            if(it==null) return@observe
            if(it==false){
                if(userViewModel.userPhoneNumber.isBlank()){
                    showToast(getString(R.string.text_noti_input_phone_number))
                }else {
                    showToast(getString(R.string.text_not_exist_user))
                }
            }
        }
    }

    @Composable
    fun textView(
        text: String,
        color: Color,
        fontSize: TextUnit,
        textAlign: TextAlign,
        modifier: Modifier = Modifier
    ) {
        Text(
            text = text,
            color = color,
            modifier = modifier,
            textAlign = textAlign,
            fontSize = fontSize,

            )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun toolbar() {
        TopAppBar(
            title = {
                textView(
                    text = "",
                    Color.White,
                    30.sp,
                    TextAlign.Center
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF2b2b2b)),
            navigationIcon = {
                IconButton(onClick = { findNavController().popBackStack() }) {
                    Icon(
                        Icons.Filled.ArrowBack, "backIcon", tint = Color.White,
                        modifier = Modifier.size(50.dp).padding(end = 10.dp)
                    )
                }
            },
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun editText(hint: String, modifier: Modifier = Modifier) {
        TextField(
            value = userViewModel.userPhoneNumber,
            onValueChange = { textValue ->
                if (textValue.length <= 11) {
                    userViewModel.setUserPhoneNumber(textValue)
                }
            },
            placeholder = {
                Text(
                    hint,
                    color = Color.Gray
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ), modifier = modifier
        )
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun mainView() {
        Scaffold(
            containerColor = Color(0xFF2b2b2b),
            topBar = { toolbar() },
            content = {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize().clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) { hideKeyboard()}
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.TopCenter)
                    ) {
                        Divider(
                            color = Color(0xFF333333),
                            thickness = 1.dp,
                            modifier = Modifier.padding(bottom = 15.dp)
                        )

                        textView(
                            text = stringResource(
                                id = R.string.text_title_input_phone_number
                            ),
                            color = Color.White, fontSize = 40.sp, textAlign = TextAlign.Start,
                            modifier = Modifier.padding(top = 100.dp, start = 70.dp, bottom = 20.dp)
                        )
                        textView(
                            text = stringResource(
                                id = R.string.text_guide_input_phone_number
                            ),
                            color = Color.Gray, fontSize = 25.sp, textAlign = TextAlign.Start,
                            modifier = Modifier.padding(start = 70.dp)
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        textView(
                            text = "대한민국(Repulbic of Korea)",
                            color = Color.White,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(start = 70.dp, top = 10.dp, bottom = 20.dp)
                        )

                        editText(
                            hint = stringResource(id = R.string.text_title_input_phone_number),
                            modifier = Modifier
                                .padding(start = 70.dp, bottom = 20.dp, end=70.dp)
                                .fillMaxWidth()
                                .height(60.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 30.dp, start = 60.dp, end = 60.dp,
                                top=30.dp)
                            .clickable {
                                clickNextButton()
                            },
                    ) {
                        textView(
                            text = stringResource(R.string.text_next_button),
                            color = Color.White,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .background(
                                    Color(0xFF333333),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .wrapContentSize(align = Alignment.Center)

                        )
                    }
                }
            }
        )
    }

    private fun clickNextButton() {
        LogUtil.d("userPhone: ${userViewModel.userPhoneNumber}")
        userViewModel.getUser(userViewModel.userPhoneNumber)
    }

    private fun hideKeyboard() {
        val inputManager = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            activity?.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    override fun onStop() {
        super.onStop()
        userViewModel.clearData()
    }

}