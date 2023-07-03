package com.hotguy.myarcade.view

import android.os.Bundle
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.hotguy.myarcade.R
import com.hotguy.myarcade.controller.MainController
import java.lang.Exception

class Launcher : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val config = AndroidApplicationConfiguration()
            val game =
                Class.forName(intent.getStringExtra(MainActivity.intentTag)).constructors[0].newInstance() as ApplicationListener
            initialize(game, config)
        } catch (e: Exception) {
            MainController().showToast(this@Launcher, R.string.error_game)
        }
    }
}