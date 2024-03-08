package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var et_email : EditText
    lateinit var et_pass : EditText
    lateinit var btn_sign_in : Button
    lateinit var tv_sign_up : TextView
    lateinit var tv_forget_pass : TextView

    lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_email = findViewById(R.id.et_email)
        et_pass = findViewById(R.id.et_pass)
        btn_sign_in = findViewById(R.id.btn_sign_in)
        tv_sign_up = findViewById(R.id.tv_sign_up)
        tv_forget_pass = findViewById(R.id.tv_forget_pass)

        firebaseAuth = FirebaseAuth.getInstance()

        btn_sign_in.setOnClickListener {
            login()
        }

        tv_sign_up.setOnClickListener {
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)

        }

    }
    fun login()
    {
        val email = et_email.text.toString()
        val pass = et_pass.text.toString()
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{
            task->
            if(task.isSuccessful)
            {
                val intent = Intent(this,Dashboard::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this, task.exception?.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }
}