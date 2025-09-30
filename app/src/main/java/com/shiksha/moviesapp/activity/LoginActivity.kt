package com.shiksha.moviesapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.shiksha.moviesapp.R
import com.shiksha.moviesapp.databinding.ActivityLoginBinding
import com.shiksha.moviesapp.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loginResult.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show()
            }
        })

        binding.button2.setOnClickListener {
            val username = binding.editTextText.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this,  getString(R.string.empty_username_password), Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(username, password)
            }
        }

        binding.textView5.setOnClickListener {
            Toast.makeText(this, getString(R.string.register_clicked), Toast.LENGTH_SHORT).show()
        }

        binding.textView4.setOnClickListener {
            Toast.makeText(this,getString(R.string.forgot_password_clicked), Toast.LENGTH_SHORT).show()
        }
    }
}
