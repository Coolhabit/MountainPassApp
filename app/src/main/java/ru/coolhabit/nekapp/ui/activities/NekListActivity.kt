package ru.coolhabit.nekapp.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import ru.coolhabit.nekapp.data.User
import ru.coolhabit.nekapp.databinding.ActivityNekListBinding
import ru.coolhabit.nekapp.ui.MainActivity

class NekListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNekListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNekListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var savedUserName = binding.neklistUsername
        val shared = this.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = shared.getString("User", "")
        var obj = gson.fromJson(json, User::class.java)

        savedUserName.text = obj.surname + " " + obj.name

        binding.exitPic.setOnClickListener {
            val shared = this.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val editor = shared.edit()
            editor.putString("User", null).apply()
            startActivity(Intent(this@NekListActivity, MainActivity::class.java))
        }

        binding.fab.setOnClickListener {
            startActivity(Intent(this@NekListActivity, NekAddActivity::class.java))
        }
    }
}