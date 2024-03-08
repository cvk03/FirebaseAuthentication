package com.example.firebaseauthentication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.StructuredName
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Dashboard : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var tv_details : TextView
    var firestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        tv_details = findViewById(R.id.tv_detail)

        firebaseAuth = FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser==null)
        {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{

            var curr_user = firestore.collection("users").document(firebaseAuth.currentUser.toString())

            tv_details.text = "HI " + firebaseAuth.currentUser

        }

    }
}