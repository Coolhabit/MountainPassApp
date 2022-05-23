package ru.coolhabit.nekapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.coolhabit.nekapp.data.Nek
import ru.coolhabit.nekapp.databinding.ActivityNekAddBinding
import ru.coolhabit.nekapp.ui.viewmodels.NekAddActivityViewModel
import ru.coolhabit.nekapp.utils.AutoDisposable

class NekAddActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(NekAddActivityViewModel::class.java)
    }

    private lateinit var nek: Nek
    private lateinit var binding: ActivityNekAddBinding
    private val autoDisposable = AutoDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNekAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        autoDisposable.bindTo(lifecycle)

        viewModel.nekData.observe(this) {
            nek = it
        }

        binding.sendBtn.setOnClickListener {
            nek = Nek(
                title = binding.neknameField.text.toString(),
                date = "23.05.2022",
                level = binding.nekDifficulty.checkedRadioButtonText.toString()
            )
            viewModel.sendNek(nek)
        }
    }
}