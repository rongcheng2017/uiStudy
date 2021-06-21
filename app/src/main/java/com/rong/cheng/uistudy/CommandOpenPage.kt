package com.rong.cheng.uistudy

import android.content.ComponentName
import android.content.Intent
import com.google.auto.service.AutoService
import com.rong.cheng.base.BaseApplication
import com.rong.cheng.webview.command.Command

/**
 * @author: frc
 * @description:
 * @date:  2021/6/18 6:12 下午
 *
 */
@AutoService(Command::class)
class CommandOpenPage :Command {
    override fun name(): String {
        return "openPage"
    }

    override fun execute(parameters: Map<String, String>) {
        val target: String? = parameters["target_class"]
        target?.apply {
            val intent = Intent()
            intent.component = ComponentName(BaseApplication.sApplication, target)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            BaseApplication.sApplication.startActivity(intent)
        }
    }
}