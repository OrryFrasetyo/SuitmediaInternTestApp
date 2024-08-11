package com.orryfrasetyo.suitmediainterntestapp.screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.orryfrasetyo.suitmediainterntestapp.R
import com.orryfrasetyo.suitmediainterntestapp.databinding.ActivitySecondScreenBinding

class SecondScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreenBinding

    private val chooseUserLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedUserName = result.data?.getStringExtra("selectedUserName")
            binding.tvSelectedUser.text = selectedUserName
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val showName = intent.getStringExtra("showName")
        binding.tvShowName.text = showName

        val selectedUserName = intent.getStringExtra("selectedUserName")
        if (selectedUserName != null) {
            binding.tvSelectedUser.text = getString(R.string.select_user_name, selectedUserName)
        }

        binding.btnChooseUser.setOnClickListener {
            val intent = Intent(this, ThirdScreenActivity::class.java)
            chooseUserLauncher.launch(intent)
        }

        binding.ibBack.setOnClickListener {
            val intent = Intent(this, FirstScreenActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

}









