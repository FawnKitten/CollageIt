package com.example.collageit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.collageit.LoginActivity.Companion.TAG
import com.example.collageit.databinding.ActivityLogupBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LogupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogupBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        binding = ActivityLogupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this@LogupActivity, MainActivity::class.java)
            startActivity(intent)
        }
        binding.buttonSignupConfirm.setOnClickListener {
            val email = binding.textEditSignupEmail.text
            val password = binding.textEditSignupPassword.text.toString()
            val confirmpassword = binding.textEditSignupConfirmpassword.text.toString()
            val username = binding.textEditSignupUsername.text

            Log.d("hello", "hello")
            if (password.compareTo(confirmpassword) == 0) {

                auth.createUserWithEmailAndPassword(email.toString(), password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            Toast.makeText(
                                baseContext,
                                "$user",
                                Toast.LENGTH_SHORT,
                            ).show()

                            val db = FirebaseFirestore.getInstance()

                            val doc = UserProfile(

                                "$username",
                                "$email",
                                "",
                                "",

                            )
                            auth.currentUser?.let { it1 ->
                                db.collection("user").document(it1.uid)
                                    .set(doc)
                                    .addOnSuccessListener { Log.d(LoginActivity.TAG, "DocumentSnapshot successfully written!") }
                                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                            }

                            Log.d("hello","done")


                            val intent = Intent(this@LogupActivity, MainActivity::class.java)
                            startActivity(intent)

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }

            } else {
                Toast.makeText(
                    baseContext,
                    "Passwords don't match.",
                    Toast.LENGTH_SHORT,
                ).show()
            }


        }
        binding.root.setOnClickListener {
            startActivity(Intent(this@LogupActivity, LoginActivity::class.java))
        }
    }
}