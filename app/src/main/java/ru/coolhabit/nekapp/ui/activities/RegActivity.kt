package ru.coolhabit.nekapp.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Patterns
import com.google.android.material.snackbar.Snackbar
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
            if (checkAllFields()) {
                registerCheck()
            } else {
                Snackbar.make(binding.root, "Не удалось войти", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        binding.phoneField.addTextChangedListener(PhoneNumberFormattingTextWatcher())
    }

    private fun registerCheck() {
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

    private fun checkAllFields(): Boolean {
        if (binding.surnameField.length() == 0) {
            binding.surnameField.error = SURNAME_ERROR
            return false
        }
        if (binding.nameField.length() == 0) {
            binding.nameField.error = NAME_ERROR
            return false
        }
        if (binding.emailField.length() == 0) {
            binding.emailField.error = EMAIL_NULL
            return false
        }
        if (!binding.emailField.text.toString().matches(EMAIL_PATTERN.toRegex())) {
            binding.emailField.error = EMAIL_ERROR
            return false
        }

        return true
    }

    companion object {
        private const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        private const val SURNAME_ERROR = "Введите фамилию!"
        private const val NAME_ERROR = "Введите имя!"
        private const val EMAIL_NULL = "Введите электронную почту!"
        private const val EMAIL_ERROR = "Неверный формат электронной почты!"
    }
}