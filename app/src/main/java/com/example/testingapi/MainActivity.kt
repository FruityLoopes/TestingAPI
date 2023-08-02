package com.example.testingapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import java.net.URL
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {


    lateinit var txtvQuestion:TextView
    lateinit var txtvCategory:TextView
   // lateinit var txtvDifficulty:TextView
    lateinit var txtAnswer:EditText
    lateinit var btnSubmitAnswer:Button
    lateinit var txtvAnswer:TextView
    lateinit var btnNextQuestion:Button
    lateinit var spinner1:Spinner




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        InfoGetter()
        var diff =  ArrayList<String>(10)
        //Setting values in array list(GeeksforGeeks, 2022)
        diff.add("Easy")
        diff.add("Medium")
        diff.add("Hard")

        txtvQuestion = findViewById(R.id.txtvQuestion)
        txtvCategory = findViewById(R.id.txtvCategory)
        txtAnswer = findViewById(R.id.txtAnswer)
        btnSubmitAnswer = findViewById(R.id.btnSubmitAnswer)
        txtvAnswer = findViewById(R.id.txtvAnswer)
        btnNextQuestion = findViewById(R.id.btnNextQuestion)
        spinner1 = findViewById(R.id.spinner1)
        val adapter1 = ArrayAdapter(spinner1.context,android.R.layout.simple_spinner_item,diff)
        spinner1.adapter=adapter1


        val btnGenerate:Button = findViewById(R.id.btnGenerate)
        btnGenerate.setOnClickListener {
            InfoGetter()
//            }
//            if ( variablesList[0].value<= 300) {
//                Toast.makeText(this, "You have selected Easy", Toast.LENGTH_SHORT)
//                    .show()
//
//            }
//            else if( variablesList[0].value>=301 && variablesList[0].value<=700) {
//                Toast.makeText(this, "You have selected Medium", Toast.LENGTH_SHORT)
//                    .show()
//            }
//            else {
//                android.widget.Toast.makeText(this, "You have selected Hard", android.widget.Toast.LENGTH_SHORT)
//                    .show()
        }

        btnSubmitAnswer.setOnClickListener(){
            if(txtAnswer.text.toString().toLowerCase() == variablesList.get(0).answer.toString().toLowerCase()){
                Toast.makeText(this, "Your answer was correct", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrect answer, you suck!!", Toast.LENGTH_SHORT).show()
                txtvAnswer.text = variablesList.get(0).answer.toString()
            }
        }
        btnNextQuestion.setOnClickListener(){
            InfoGetter()
            txtAnswer.setText("")
            txtvAnswer.setText("")
        }

    }
        lateinit var answer:String
        lateinit var variablesList:List<ApiVariables>
         private fun InfoGetter() {
        val executor =  Executors.newSingleThreadExecutor()
        executor.execute {
            val url = URL("https://jservice.io/api/random")
            val json = url.readText()
            variablesList = Gson().fromJson(json, Array<ApiVariables>::class.java).toList()

            Handler(Looper.getMainLooper()).post {
                Log.d("PullingInfo", "Plain Json Vars :" + json.toString())
                Log.d("ConvertingInfo", "Converted Json :" + variablesList.toString())

                txtvQuestion.text = variablesList[0].question
                txtvCategory.text = variablesList[0].category.title
               // txtvDifficulty.text = variablesList[0].value.toString()
                answer=variablesList[0].answer
                Log.d("Display", "question: ${variablesList[0].question}, \nCategory: ${variablesList[0].category.title},\nDifficulty: ${variablesList[0].value.toString()}")
            }
        }
    }
}