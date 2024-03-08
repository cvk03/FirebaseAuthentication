package com.example.firebaseauthentication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity2 : AppCompatActivity() {

    val auth : FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var et_email : EditText
    lateinit var et_pass : EditText
    lateinit var btn_signUp : Button
    lateinit var pb_Signup : ProgressBar
    lateinit var et_confirmPass : EditText
    var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btn_signUp = findViewById(R.id.btn_signUp)
        et_email = findViewById(R.id.et_emailSignup)
        et_pass = findViewById(R.id.et_passSignup)
        pb_Signup = findViewById(R.id.pb_signup)
        et_confirmPass = findViewById(R.id.et_confirm_pass)

       btn_signUp.setOnClickListener {
            val email = et_email.text.toString()
            val pass = et_pass.text.toString()
           val confirmPass = et_confirmPass.text.toString()

           if(email.isEmpty() || pass.isEmpty()){
               Toast.makeText(this,"Email and Password cant be empty",Toast.LENGTH_LONG).show()
           }
           else if(pass!=confirmPass)
           {
               Toast.makeText(this,"Email and Confirm Password do not match",Toast.LENGTH_LONG).show()
           }
           else{
               signupWithFirebase(email,pass)
           }

        }
    }

    fun signupWithFirebase(email:String, pass:String)
    {
        pb_Signup.visibility = View.VISIBLE
        btn_signUp.isClickable = false

        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener { task->
            if(task.isSuccessful)
            {
                Toast.makeText(applicationContext,"Your account has been created successfully!!!",Toast.LENGTH_LONG).show()
                storeInfo(email,pass)
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
                pb_Signup.visibility = View.INVISIBLE
               btn_signUp.isClickable = true

            }else{

                Toast.makeText(applicationContext, task.exception?.localizedMessage,Toast.LENGTH_SHORT).show()


            }
        }


    }

    fun storeInfo(email: String,pass: String)
    {
        val user = hashMapOf(
            "name" to email,
            "email" to email,
            "password" to pass
        )

       val each_user = firestore.collection("users")
       val curr_user = auth.currentUser.toString()

        each_user.document(curr_user).set(user)
    }
}