package ru.coolhabit.nekapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.coolhabit.nekapp.databinding.ActivityMainBinding
import ru.coolhabit.nekapp.databinding.ActivityRegBinding

class RegActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.enterBtn.setOnClickListener {
            startActivity(Intent(this@RegActivity, NekListActivity::class.java))
        }
    }
}