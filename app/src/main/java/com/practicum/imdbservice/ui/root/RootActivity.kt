package com.practicum.imdbservice.ui.root

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.practicum.imdbservice.R
import com.practicum.imdbservice.databinding.ActivityRootBinding
import com.practicum.imdbservice.ui.movies.MoviesFragment

class RootActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRootBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    fun animateBottomNavigationView() {
        //binding.
    }
}