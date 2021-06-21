package com.rong.cheng.webview.mainprocess

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * @author: frc
 * @description:
 * @date:  2021/6/18 5:09 下午
 *
 */
class MainProcessCommandService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return MainProcessCommandsManager.get()
    }
}