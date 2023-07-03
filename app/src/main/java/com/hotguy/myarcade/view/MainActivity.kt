package com.hotguy.myarcade.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hotguy.myarcade.R
import com.hotguy.myarcade.controller.MainController

class MainActivity : AppCompatActivity() {

    companion object {
        const val intentTag = "game"
    }

    private val GAME_REQUEST = 1000

    private val mainPackage = "com.hotguy.myarcade.games"

    private val snake = "snake." + MainController().getSnake()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, Launcher()::class.java)
        intent.putExtra(intentTag, "$mainPackage.$snake")
        startActivityForResult(intent, GAME_REQUEST)
    }
}