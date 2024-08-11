package com.orryfrasetyo.suitmediainterntestapp.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.orryfrasetyo.suitmediainterntestapp.R
import com.orryfrasetyo.suitmediainterntestapp.databinding.ActivityFirstScreenBinding

class FirstScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnCheck.setOnClickListener {
            val sentence = binding.etPalindrome.text.toString()

            val isPalindrome = checkPalindrome(sentence)

            val message = if (isPalindrome) {
                "$sentence, isPalindrome"
            } else {
                "$sentence, not palindrome"
            }

            showDialog(message)
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, SecondScreenActivity::class.java)
            intent.putExtra("showName", binding.etName.text.toString())
            startActivity(intent)
        }

    }

    private fun checkPalindrome(sentence: String): Boolean {
        val cleanSentence = sentence.replace("\\s".toRegex(), "")
        val reversedSentence = cleanSentence.reversed()
        return cleanSentence.equals(reversedSentence, ignoreCase = true)
    }

    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }
}








