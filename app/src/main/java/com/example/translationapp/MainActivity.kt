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
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    companion object
    {
        private val REQUEST_CODE_PERMISSIONS = 100
        private var inputLanguage = "en_US"
        private var outputLanguage = "en_US"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn)

        // INPUT SPINNER SET UP
        val inSpinner = findViewById<Spinner>(R.id.inputLang)
        // access to items of the list (in this case, languages)
        val inputLanguages = resources.getStringArray(R.array.languages)
        val inputAdapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item)
        // set layout for choices to appear
        inputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // setSelection(0); <-- this will make default options for spinners
        inSpinner.setSelection(0)
        // set adapter to inputSpinner
        inSpinner.adapter = inputAdapter

        // input spinner selection
        inSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using parent.getItemAtPosition(pos)

                when(inSpinner.getItemAtPosition(pos)) {
                    "English" -> inputLanguage = "en_US"
                    "廣東話" -> inputLanguage = "zh-HK"
                    "普通话" -> inputLanguage = "zh-Hans"
                    "Korean" -> inputLanguage = "Kor"
                }

                // print selected language
                Toast.makeText(this@MainActivity,"Input language selected: " + inputLanguages[pos], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
                Toast.makeText(Activity(),"No input language selected.", Toast.LENGTH_SHORT).show()
            }
        }

        // OUTPUT SPINNER SET UP
        val outSpinner = findViewById<Spinner>(R.id.outputLang)
        val outputLanguages = resources.getStringArray(R.array.languages)
        val outputAdapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item)
        outputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        outSpinner.setSelection(0)
        outSpinner.adapter = outputAdapter

        /*
        // output spinner selection
        outSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {

                when(outSpinner.getItemAtPosition(pos)) {
                    "English" -> outputLanguage = "en_US"
                    "廣東話" -> outputLanguage = "zh-HK"
                    "普通话" -> outputLanguage = "zh-Hans"
                    "Korean" -> outputLanguage = "Kor"
                }
                // print selected language
                Toast.makeText(Activity(),"Output language selected: " + outputLanguages[pos], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
                Toast.makeText(Activity(),"No output language selected.", Toast.LENGTH_SHORT).show()
            }
        }

         */

        btn.setOnClickListener {
            startInput()
        }

    }


    private fun startInput()
    {
        val int = Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS)
        print(int)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, inputLanguage)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi, speak something")
        try {
            // if there is no error, show dialog
            startActivityForResult(intent, REQUEST_CODE_PERMISSIONS)
        } catch (e: Exception){
            // if there is any error, get error message and show in Toast
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE_PERMISSIONS -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // get text from result
                    val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                    // set the text to textView
                    print(text)

                }
            }
        }
    }

    private fun startOutput()
    {
        // call translate, get string back and return back speech

    }

    private fun translate()
    {
        // Translate ONLY if necessary aka when languages are different

    }

}