package com.example.collageit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.collageit.LoginActivity.Companion.TAG
import com.example.collageit.databinding.ActivityLogupBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class LogupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        binding = ActivityLogupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignupConfirm.setOnClickListener {
            val email = binding.textEditSignupEmail.text
            val password = binding.textEditSignupPassword.text
            val username = binding.textEditSignupUsername.text
            val confirmpassword = binding.textEditSignupConfirmpassword.text
            Log.d("hello","hello")

            val db = FirebaseFirestore.getInstance()

          val user = User(
                  "$email",
                  "$password",

                  "$username",
              )
          db.collection("user").document("VKrmjUYGndEzfiu41JMN")
              .set(user)
              .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
              .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

              Log.d("hello","done")

        }
        binding.root.setOnClickListener {
            startActivity(Intent(this@LogupActivity, LoginActivity::class.java))
        }
    }
}