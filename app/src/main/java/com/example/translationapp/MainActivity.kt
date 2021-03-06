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
import android.widget.ArrayAdapter

import android.widget.Spinner
import android.content.BroadcastReceiver
import android.content.Context
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import org.w3c.dom.Text

//import com.example.translationapp.MainActivity.LanguageDetailsChecker





class MainActivity : AppCompatActivity() {

    companion object
    {
        private val REQUEST_CODE_PERMISSIONS = 100
        private var inputLanguage = "en_US"
        private var outputLanguage = "en_US"
        private var inputText = ""
        private var outputText = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn)
        val outText = findViewById<TextView>(R.id.outText)

        /*
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
                var value = inSpinner.getItemAtPosition(pos)
                if (value == "English") inputLanguage = "en_US"
                if (value == "?????????") inputLanguage = "zh-HK"
                if (value == "?????????") inputLanguage = "zh-Hans"
                if (value == "Korean") inputLanguage = "Kor"
                /*
                when(inSpinner.getItemAtPosition(pos)) {
                    "English" -> inputLanguage = "en_US"
                    "?????????" -> inputLanguage = "zh-HK"
                    "?????????" -> inputLanguage = "zh-Hans"
                    "Korean" -> inputLanguage = "Kor"
                }
                 */

                // print selected language
                Toast.makeText(this@MainActivity,"Input language selected: " + inputLanguages[pos] + "\n which is: " + value, Toast.LENGTH_SHORT).show()
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

        // output spinner selection
        outSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                /*
                when(outSpinner.getItemAtPosition(pos)) {
                    "English" -> outputLanguage = "en_US"
                    "?????????" -> outputLanguage = "zh-HK"
                    "?????????" -> outputLanguage = "zh-Hans"
                    "Korean" -> outputLanguage = "Kor"
                }

                 */
                // print selected language
                //Toast.makeText(Activity(),"Output language selected: " + outputLanguages[pos], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
                Toast.makeText(Activity(),"No output language selected.", Toast.LENGTH_SHORT).show()
            }
        }

         */

        val inputAdapter: ArrayAdapter<CharSequence>
        val outputAdapter: ArrayAdapter<CharSequence>

        val inputLanguages = resources.getStringArray(R.array.languages)
        val outputLanguages = resources.getStringArray(R.array.languages)

        val inSpinner = findViewById<View>(R.id.inputLang) as Spinner
        val outSpinner = findViewById<View>(R.id.outputLang) as Spinner

        inputAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, inputLanguages)
        inputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inSpinner.adapter = inputAdapter

        outputAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, outputLanguages)
        outputAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        outSpinner.adapter = outputAdapter


        // input spinner selection
        inSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                println("HELLO: " + inSpinner.selectedItem.toString())
                when(inSpinner.getItemAtPosition(pos)) {
                    "English" -> inputLanguage = "en_US"
                    "?????????" -> inputLanguage = "zh-HK"
                    "?????????" -> inputLanguage = "zh-Hans"
                    "??????" -> inputLanguage = "Kor"
                }
                println("Input language: " + inputLanguage)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "No language selected", Toast.LENGTH_SHORT).show()
            }
        }

        // output spinner selection
        outSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                println("HELLO: " + outSpinner.selectedItem.toString())
                when(outSpinner.getItemAtPosition(pos)) {
                    "English" -> outputLanguage = "en_US"
                    "?????????" -> outputLanguage = "zh-HK"
                    "?????????" -> outputLanguage = "zh-Hans"
                    "??????" -> outputLanguage = "Kor"
                }
                println("Output language: " + outputLanguage)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
                Toast.makeText(Activity(),"No output language selected.", Toast.LENGTH_SHORT).show()
            }
        }

        btn.setOnClickListener {
            getSpeechInput()
        }

    }

    /*
    class LanguageDetailsChecker : BroadcastReceiver() {
        private var supportedLanguages: List<String>? = null
        override fun onReceive(p0: Context?, p1: Intent?) {
            val results = getResultExtras(true)
            supportedLanguages = results.getStringArrayList(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES)
            if (supportedLanguages == null) {
                println("No voice data found.")
            }
            else{
                for (i in supportedLanguages!!)
                {
                    println(i)
                }
            }
        }
    }
     */

    private fun getSpeechInput()
    {
        /*
        val detailsIntent = Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS)
        val hel = sendOrderedBroadcast(detailsIntent, null, LanguageDetailsChecker(), null, RESULT_OK, null, null)
        */
        try {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, inputLanguage)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi, speak something")
            // if there is no error, show dialog
            startActivityForResult(intent, REQUEST_CODE_PERMISSIONS)
        } catch (e: Exception){
            // if there is any error, get error message and show in Toast
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val inText = findViewById<TextView>(R.id.inText)
        when(requestCode){
            REQUEST_CODE_PERMISSIONS -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // get text from result
                    val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    inputText = text?.get(0).toString()
                    // updating textview
                    inText.text = inputText
                }
            }
        }
    }

    private fun translate(): String {
        // Translate ONLY if necessary aka when languages are different
        // get input text from companion object, inputText
        if (inputLanguage == outputLanguage) {
            return inputText
        }
        else {
            // Create an input-output translator:
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.outputLanguage)
                .build()
            val englishGermanTranslator = Translation.getClient(options)


        }
        return ""
    }

    private fun startOutput()
    {
        // call translate, get string back
        // RETURN BACK IN SPEECH
        translate()
    }
}

