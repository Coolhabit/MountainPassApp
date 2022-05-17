package ru.coolhabit.nekapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.coolhabit.nekapp.databinding.ActivityNekAddBinding

class NekAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNekAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNekAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}