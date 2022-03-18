package com.example.coursesproject

import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.coursesproject.databinding.ActivityMainBinding
import com.example.coursesproject.ml.Model
import org.json.JSONObject
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding
    private lateinit var tflite : Interpreter
    private lateinit var tflitemodel : ByteBuffer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.button.setOnClickListener(){
            val context = bindingClass.inputtxt.text
            bindingClass.sadImageView.visibility = View.GONE
            bindingClass.smileImageView.visibility = View.VISIBLE

            fun loadModelFile(assetManager: AssetManager, modelPath: String): ByteBuffer {
                val fileDescriptor = assetManager.openFd(modelPath)
                val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
                val fileChannel = inputStream.channel
                val startOffset = fileDescriptor.startOffset
                val declaredLength = fileDescriptor.declaredLength
                return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
            }

            tflitemodel = loadModelFile(this.assets, "model.tflite")
            tflite = Interpreter(tflitemodel)

            var myList = floatArrayOf(1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 3.0f, 3.0f, 3.0f, 4.0f, 4.0f, 4.0f, 5.0f, 5.0f, 5.0f)

            val inputVal = myList
            val byteBuffer = ByteBuffer.allocateDirect(4 * 15)
            byteBuffer.order(ByteOrder.nativeOrder())


        }


    }

}
