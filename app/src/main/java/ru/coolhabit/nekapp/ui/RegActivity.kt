package ru.coolhabit.nekapp.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.google.gson.Gson
import ru.coolhabit.nekapp.data.User
import ru.coolhabit.nekapp.databinding.ActivityRegBinding

class RegActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enterBtn.setOnClickListener {
            val shared = this.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val gson = Gson()
            val json = shared.getString("User", "")
            val obj = gson.fromJson(json, User::class.java)
            if (obj == null) {
                saveUser()
                startActivity(Intent(this@RegActivity, NekListActivity::class.java))
            } else {
                startActivity(Intent(this@RegActivity, NekListActivity::class.java))
            }
        }
    }

    private fun saveUser() {
        val inputSurname = binding.surnameField.text.toString()
        val inputName = binding.nameField.text.toString()
        val inputPatron = binding.patronField.text.toString()
        val inputEmail = binding.emailField.text.toString()
        val inputPhone = binding.phoneField.text.toString()
        val inputSocial = binding.socialField.text.toString()

        val obj = User()
        obj.surname = inputSurname
        obj.name = inputName
        obj.patron = inputPatron
        obj.email = inputEmail
        obj.phone = inputPhone
        obj.socialLink = inputSocial


        val shared = this.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val editor = shared.edit()
        val gson = Gson()
        val json = gson.toJson(obj)
        editor.putString("User", json).apply()

    }
}