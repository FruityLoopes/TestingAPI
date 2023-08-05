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
import java.util.Random
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {


    lateinit var txtvQuestion:TextView
    lateinit var txtvCategory:TextView
    lateinit var txtAnswer:EditText
    lateinit var btnSubmitAnswer:Button
    lateinit var txtvAnswer:TextView
    lateinit var btnNextQuestion:Button
    lateinit var spinner1:Spinner

    lateinit var variablesList:List<ApiVariables>
    var random:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        InfoGetter()
        var diff =  ArrayList<String>(10)
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
            Random()
            }


        btnSubmitAnswer.setOnClickListener(){
            if(txtAnswer.text.toString().toLowerCase() == variablesList.get(random).answer.toString().toLowerCase()){
                Toast.makeText(this, "Your answer was correct", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrect answer, you suck!!", Toast.LENGTH_SHORT).show()
                txtvAnswer.text = variablesList.get(random).answer.toString()
            }
        }

        btnNextQuestion.setOnClickListener(){
           Random()
            txtvAnswer.text=""
            txtvAnswer.text = variablesList.get(random).answer.toString()
        }

    }


    private fun InfoGetter() {
        val executor =  Executors.newSingleThreadExecutor()

        executor.execute {
            val url = URL("https://jservice.io/api/random?count=100")
            val json = url.readText()
            variablesList = Gson().fromJson(json, Array<ApiVariables>::class.java).toList()
            Handler(Looper.getMainLooper()).post {
               Log.d("Display", "Count: ${variablesList.count()}")

                for (i in 1..variablesList.count() - 1 )
                Log.d("Display", "question: ${variablesList[i].question}, \nCategory: ${variablesList[i].category.title},\nDifficulty: ${variablesList[i].value.toString()}, \n" +
                        "Answer: ${variablesList[i].answer.toString()}")
            }
        }
    }

    private fun Random(){
        val rand = 0..99

        var ran = 0

        var check = false
        val diffi = spinner1.selectedItem
        while (check == false){
            ran = rand.random()
            random = ran
            txtvAnswer.text = variablesList[ran].answer
            if(diffi == "Easy"){

                if (variablesList[ran].value <= 300){
                    txtvQuestion.text = variablesList[ran].question
                    txtvCategory.text = variablesList[ran].category.title

                    check = true
                }
            } else if (diffi == "Medium"){

                if (variablesList[ran].value >= 301 && variablesList[ran].value <= 700){
                    txtvQuestion.text = variablesList[ran].question
                    txtvCategory.text = variablesList[ran].category.title

                    check = true
                }

            } else {

                if (variablesList[ran].value > 700){
                    txtvQuestion.text = variablesList[ran].question
                    txtvCategory.text = variablesList[ran].category.title

                    check = true
                }

            }
        }

    }
}