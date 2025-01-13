package com.dhother.bg_remover

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.huawei.hms.mlsdk.MLAnalyzerFactory
import com.huawei.hmf.tasks.Task
import com.huawei.hms.mlsdk.common.MLFrame
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentation
import com.huawei.hms.mlsdk.imgseg.MLImageSegmentationAnalyzer
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var segmentationAnalyzer: MLImageSegmentationAnalyzer
    private var photoUri: Uri? = null
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)

        segmentationAnalyzer = MLAnalyzerFactory.getInstance()
            .imageSegmentationAnalyzer


        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 101)
        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        val photoFile = createImageFile()
        photoUri = photoFile?.let {
            FileProvider.getUriForFile(this, "${packageName}.fileprovider", it)
        }

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        }

        cameraLauncher.launch(takePictureIntent)
    }

    private fun createImageFile(): File? {
        return try {
            val storageDir = externalCacheDir
            File.createTempFile("IMG_", ".jpg", storageDir)
        } catch (e: IOException) {
            Toast.makeText(this, "Error creating file: ${e.message}", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                photoUri?.let { uri ->
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    imageView.setImageBitmap(bitmap)
                    removeBackground()
                }
            } else {
                Toast.makeText(this, "Picture not taken", Toast.LENGTH_SHORT).show()
            }
        }

    private fun removeBackground() {
        bitmap?.let { ggg ->
            val frame = MLFrame.fromBitmap(ggg)
            val task: Task<MLImageSegmentation> = segmentationAnalyzer.asyncAnalyseFrame(frame)

            task.addOnSuccessListener { segmentationResult ->
                val foreground = segmentationResult.foreground
                if (foreground != null) {
                    imageView.setImageBitmap(foreground)
                    Toast.makeText(this, "Background removed", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to get foreground image", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Failed to remove background: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        segmentationAnalyzer.stop()
    }
}
