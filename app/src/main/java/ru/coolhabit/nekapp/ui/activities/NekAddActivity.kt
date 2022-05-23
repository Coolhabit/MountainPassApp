package ru.coolhabit.nekapp.ui.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import ru.coolhabit.nekapp.data.Nek
import ru.coolhabit.nekapp.data.User
import ru.coolhabit.nekapp.databinding.ActivityNekAddBinding
import ru.coolhabit.nekapp.ui.viewmodels.NekAddActivityViewModel
import ru.coolhabit.nekapp.utils.AutoDisposable
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val NOW = "Сегодня: "
private const val PICTURE_FROM_CAMERA: Int = 1
private const val AUTHORITY = "ru.coolhabit.nekapp.fileprovider"

class NekAddActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(NekAddActivityViewModel::class.java)
    }

    private lateinit var nek: Nek
    private lateinit var binding: ActivityNekAddBinding
    private val autoDisposable = AutoDisposable()
    var cal = Calendar.getInstance()
    private lateinit var photoFile: File
    lateinit var currentPhotoPath: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNekAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        autoDisposable.bindTo(lifecycle)

        viewModel.nekData.observe(this) {
            nek = it
        }

        currentDateTextView()

        binding.photoBtnCamera.setOnClickListener {
            takePicture()
        }

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

    private fun takePicture(){
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = createImageFile()
        val uri = FileProvider.getUriForFile(this, AUTHORITY, photoFile)
        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(pictureIntent, PICTURE_FROM_CAMERA)
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == PICTURE_FROM_CAMERA) {
                val uri = FileProvider.getUriForFile(this,AUTHORITY, photoFile)
                binding.nekPhoto.visibility = View.VISIBLE
                binding.photoDescriptionHeader.visibility = View.VISIBLE
                binding.photoDescriptionLayout.visibility = View.VISIBLE
                binding.sendBtn.visibility = View.VISIBLE
                binding.nekPhoto.setImageURI(uri)
                binding.photoBtnCamera.visibility = View.GONE
                binding.photoBtnMemory.visibility = View.GONE
                binding.fromCamera.visibility = View.GONE
                binding.fromGallery.visibility = View.GONE
                binding.photoHint.visibility = View.GONE
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}