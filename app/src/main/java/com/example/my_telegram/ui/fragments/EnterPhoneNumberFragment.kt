package com.example.my_telegram.ui.fragments

import androidx.fragment.app.Fragment
import com.example.my_telegram.MainActivity
import com.example.my_telegram.R
import com.example.my_telegram.activites.RegisterActivity
import com.example.my_telegram.utils.AUTH
import com.example.my_telegram.utils.replaceActivity
import com.example.my_telegram.utils.replaceFragment
import com.example.my_telegram.utils.showToast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_phone_number.*
import java.util.concurrent.TimeUnit

class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {

    private lateinit var mPhoneNumber:String
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onStart() {
        super.onStart()
        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener{task ->
                    if (task.isSuccessful){
                        showToast("Добро пожаловать")
                        (activity as RegisterActivity).replaceActivity(MainActivity())
                    }else{
                        showToast(task.exception?.message.toString())
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterCodeFragment(mPhoneNumber, id))
            }
        }
        register_btn_next.setOnClickListener {
            sendCode()
        }
    }

    private fun sendCode() {
        if (register_input_phone_number.text.toString().isEmpty()){
            showToast(getString(R.string.register_toast_enter_phone))
        }else{
           // replaceFragment(EnterCodeFragment())
            authUser()
        }
    }

    private fun authUser() {
        mPhoneNumber = register_input_phone_number.text.toString()

        // данный код взят из справочника по firebase (так же пришлось включить API в GOOGLE)
        PhoneAuthProvider.verifyPhoneNumber(PhoneAuthOptions.newBuilder(AUTH)
            .setActivity(activity as RegisterActivity)
            .setPhoneNumber(mPhoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(mCallback)
            .build()
        )

        /*PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mPhoneNumber,
            60,
            TimeUnit.SECONDS,
            activity as RegisterActivity,
            mCallback
        )*/
    }
}