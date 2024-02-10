package com.example.weather2
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.buttonDialogBox)
        button.setOnClickListener{
            val dialog = DialogFragment()
            dialog.show(supportFragmentManager, "Выбор")
        }
    }
}