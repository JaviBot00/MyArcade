package com.hotguy.myarcade.controller

import android.app.Activity
import android.widget.Toast
import com.hotguy.myarcade.games.snake.GdxSnake

class MainController {

    fun getSnake(): String {
        return GdxSnake::class.java.simpleName
    }

    fun showToast(fromActivity: Activity ,text: Int) {
        Toast.makeText(fromActivity, fromActivity.resources.getString(text), Toast.LENGTH_SHORT)
            .show()
    }
}