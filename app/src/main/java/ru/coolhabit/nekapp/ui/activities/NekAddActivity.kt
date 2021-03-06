package ru.coolhabit.nekapp.ui.activities

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.app.DatePickerDialog
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import ru.coolhabit.nekapp.data.Nek
import ru.coolhabit.nekapp.data.User
import ru.coolhabit.nekapp.databinding.ActivityNekAddBinding
import ru.coolhabit.nekapp.ui.viewmodels.NekAddActivityViewModel
import ru.coolhabit.nekapp.utils.AutoDisposable
import ru.coolhabit.remote_module.entity.ImageRequest
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*


private const val NOW = "Сегодня: "
private const val PICTURE_FROM_CAMERA = 1
private const val PICTURE_FROM_GALLERY = 2
private const val AUTHORITY = "ru.coolhabit.nekapp.fileprovider"
private const val MOCK_IMAGE = "не смог добыть сам файл при загрузке из галереи"
private const val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1001

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
    private var imageUri: Uri? = null
    val photoList = mutableListOf<ImageRequest>()

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
                userPhone = obj.phone.toString(),
                imageList = photoList
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

        binding.photoBtnMemory.setOnClickListener {
//            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            startActivityForResult(gallery, PICTURE_FROM_GALLERY)
            pickImage()
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

    private fun takePicture() {
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encodeFileToBase64 (file: File): String {
        val filePath = file.path
        val encoded = Files.readAllBytes(Paths.get(filePath))
        return Base64.getEncoder().encodeToString(encoded)
    }

    private fun uriToImageFile(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val filePath = cursor.getString(columnIndex)
                cursor.close()
                return File(filePath)
            }
            cursor.close()
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == PICTURE_FROM_CAMERA) {
                val uri = FileProvider.getUriForFile(this, AUTHORITY, photoFile)

                val nekConvertedImage = ImageRequest(encodeFileToBase64(photoFile), binding.photoDescriptionField.text.toString())
                photoList.add(nekConvertedImage)

                binding.nekPhoto.visibility = View.VISIBLE
                binding.photoDescriptionHeader.visibility = View.VISIBLE
                binding.photoDescriptionLayout.visibility = View.VISIBLE
                binding.sendBtn.visibility = View.VISIBLE

                binding.photoBtnCamera.visibility = View.GONE
                binding.photoBtnMemory.visibility = View.GONE
                binding.fromCamera.visibility = View.GONE
                binding.fromGallery.visibility = View.GONE
                binding.photoHint.visibility = View.GONE

                Glide.with(this)
                    .load(uri)
                    .centerCrop()
                    .into(binding.nekPhoto)

            } else if (requestCode == PICTURE_FROM_GALLERY) {
                binding.nekPhoto.visibility = View.VISIBLE
                binding.photoDescriptionHeader.visibility = View.VISIBLE
                binding.photoDescriptionLayout.visibility = View.VISIBLE
                binding.sendBtn.visibility = View.VISIBLE

                binding.photoBtnCamera.visibility = View.GONE
                binding.photoBtnMemory.visibility = View.GONE
                binding.fromCamera.visibility = View.GONE
                binding.fromGallery.visibility = View.GONE
                binding.photoHint.visibility = View.GONE


                imageUri = data?.data
                if (imageUri != null) {
                    val imageFile = uriToImageFile(imageUri!!)
                    val nekConvertedImage = ImageRequest(encodeFileToBase64(imageFile!!), binding.photoDescriptionField.text.toString())
                    photoList.add(nekConvertedImage)
                }

                Glide.with(this)
                    .load(imageUri)
                    .centerCrop()
                    .into(binding.nekPhoto)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun pickImage() {
        if (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            intent.putExtra("crop", "true")
            intent.putExtra("scale", true)
            intent.putExtra("aspectX", 16)
            intent.putExtra("aspectY", 9)
            startActivityForResult(intent, PICTURE_FROM_GALLERY)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // pick image after request permission success
                    pickImage()
                }
            }
        }
    }
}