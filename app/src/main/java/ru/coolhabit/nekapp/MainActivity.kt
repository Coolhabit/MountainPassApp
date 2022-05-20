package ru.coolhabit.nekapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.google.gson.Gson
import ru.coolhabit.nekapp.data.User
import ru.coolhabit.nekapp.databinding.ActivityMainBinding
import ru.coolhabit.nekapp.ui.NekListActivity
import ru.coolhabit.nekapp.ui.RegActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            binding.enterBtn.setOnClickListener {
                val shared = this.getSharedPreferences("settings", Context.MODE_PRIVATE)
                val gson = Gson()
                val json = shared.getString("User", "")
                val obj = gson.fromJson(json, User::class.java)
                if (obj == null) {
                    startActivity(Intent(this@MainActivity, RegActivity::class.java))
                } else {
                    startActivity(Intent(this@MainActivity, NekListActivity::class.java))
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }

    }
}