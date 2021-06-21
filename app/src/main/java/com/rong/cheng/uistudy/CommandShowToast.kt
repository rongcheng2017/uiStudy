package com.rong.cheng.uistudy

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.auto.service.AutoService
import com.rong.cheng.base.BaseApplication
import com.rong.cheng.webview.command.Command

/**
 * @author: frc
 * @description:
 * @date:  2021/6/18 6:30 下午
 *
 */
@AutoService(Command::class)
class CommandShowToast : Command {
    override fun name(): String {
        return "showToast"
    }

    override fun execute(parameters: Map<String, String>) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(Runnable {
            Toast.makeText(BaseApplication.sApplication,
                parameters["message"],
                Toast.LENGTH_LONG).show()
        })


    }
}