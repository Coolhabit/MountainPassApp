package ru.coolhabit.nekapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.coolhabit.nekapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enterBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegActivity::class.java))
        }
    }
}