package com.example.translationapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import java.util.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    companion object
    {
        //private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.INTERNET)
        private val REQUEST_CODE_PERMISSIONS = 1
        private var inputLanguage = "en_US"
        private var outputLanguage = "en_US"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //access to items of the list
        //val input_languages = resources.getStringArray(R.array.Input_Language)
        //val output_languages = resources.getStringArray(R.array.Output_Language)

        val input_Adapter = ArrayAdapter.createFromResource(this, R.array.Input_Language, android.R.layout.simple_spinner_item)
        val output_Adapter = ArrayAdapter.createFromResource(this, R.array.Output_Language, android.R.layout.simple_spinner_item)

        input_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        output_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val inSpinner = findViewById<Spinner>(R.id.inputLang)
        val outSpinner = findViewById<Spinner>(R.id.outputLang)

        inSpinner.adapter = input_Adapter
        outSpinner.adapter = output_Adapter

/*        if (allPermissionsGranted())
        {
            startInput()
        }*/

        //input spinner selection
        inSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using parent.getItemAtPosition(pos)
                when(parent.getItemAtPosition(pos)) {
                    "English" -> inputLanguage = "en_US"
                    "廣東話" -> inputLanguage = "zh-HK"
                    "普通话" -> inputLanguage = "zh-Hans"
                    "Korean" -> inputLanguage = "Kor"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
                Toast.makeText(Activity(),"No input language selected.", Toast.LENGTH_SHORT).show()
            }
        }

        //output spinner selection
        outSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                when(parent.getItemAtPosition(pos)) {
                    "English" -> outputLanguage = "en_US"
                    "廣東話" -> outputLanguage = "zh-HK"
                    "普通话" -> outputLanguage = "zh-Hans"
                    "Korean" -> outputLanguage = "Kor"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
                Toast.makeText(Activity(),"No output language selected.", Toast.LENGTH_SHORT).show()
            }
        }

    }
    //In onItemSelected method add this code sp.setSelection(0); <-- this will make default options for spinners

    private fun startInput()
    {
        //will listen to user and go from speech to text
        if (!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this, "Speech Recognition is not available", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH)
        }

    }

    private fun startOutput()
    {
        //cal translate, get string back and return back speech

    }

    private fun translate()
    {
        //Translate ONLY if necessary aka when languages are different

    }


/*
    //PERMISSIONS - BEGIN
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startInput()
            } else {
                Toast.makeText(this, "Permission for microphone was denied." +
                        "Please change in the settings.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
    //PERMISSIONS - END
    */

}