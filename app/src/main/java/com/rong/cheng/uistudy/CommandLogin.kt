package com.rong.cheng.uistudy

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.auto.service.AutoService
import com.google.gson.Gson
import com.rong.cheng.base.BaseApplication
import com.rong.cheng.webview.command.Command

/**
 * @author: frc
 * @description:
 * @date:  2021/6/18 6:30 下午
 *
 */
@AutoService(Command::class)
class CommandLogin : Command {
    override fun name(): String {
        return "login"
    }

    override fun execute(parameters: Map<String, String>) {
       Log.e("login", Gson().toJson(parameters))
    }
}