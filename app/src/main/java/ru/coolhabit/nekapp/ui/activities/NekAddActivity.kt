package ru.coolhabit.nekapp.ui.activities

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import ru.coolhabit.nekapp.data.Nek
import ru.coolhabit.nekapp.data.User
import ru.coolhabit.nekapp.databinding.ActivityNekAddBinding
import ru.coolhabit.nekapp.ui.viewmodels.NekAddActivityViewModel
import ru.coolhabit.nekapp.utils.AutoDisposable
import java.text.SimpleDateFormat
import java.util.*

private const val NOW = "Сегодня: "

class NekAddActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(NekAddActivityViewModel::class.java)
    }

    private lateinit var nek: Nek
    private lateinit var binding: ActivityNekAddBinding
    private val autoDisposable = AutoDisposable()
    var cal = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNekAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        autoDisposable.bindTo(lifecycle)

        viewModel.nekData.observe(this) {
            nek = it
        }

        currentDateTextView()

        binding.sendBtn.setOnClickListener {
            val shared = this.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val gson = Gson()
            val json = shared.getString("User", "")
            val obj = gson.fromJson(json, User::class.java)

            nek = Nek(
                title = binding.neknameField.text.toString(),
                date = binding.datepickerField.text.toString(),
                level = binding.nekDifficulty.checkedRadioButtonText.toString(),
                latitude = binding.latitudeField.text.toString(),
                longtitude = binding.longtitudeField.text.toString(),
                height = binding.nekheightField.text.toString(),
                userId = 1,
                userName = obj.name.toString(),
                userSurname = obj.surname.toString(),
                userEmail = obj.email.toString(),
                userPhone = obj.phone.toString()
            )

            viewModel.sendNek(nek)
        }

        val dataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        binding.datepickerField.setOnClickListener {
            DatePickerDialog(
                this@NekAddActivity,
                dataSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.datepickerField.setText(sdf.format(cal.time))
    }

    private fun currentDateTextView() {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.currentDate.text = NOW + sdf.format(cal.time)
    }
}