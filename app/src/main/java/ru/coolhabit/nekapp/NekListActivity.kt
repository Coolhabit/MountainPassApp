package ru.coolhabit.nekapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.coolhabit.nekapp.databinding.ActivityNekListBinding

class NekListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNekListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNekListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}