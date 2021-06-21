package com.rong.cheng.webview.webviewprocess

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.rong.cheng.base.BaseApplication
import com.rong.cheng.webview.IWebViewProcessToMainProcessInterface
import com.rong.cheng.webview.mainprocess.MainProcessCommandService
import com.rong.cheng.webview.mainprocess.MainProcessCommandsManager

/**
 * @author: frc
 * @description:
 * @date:  2021/6/18 5:36 下午
 *
 */
class WebViewProcessCommandDispatcher private constructor() : ServiceConnection {
    private var mBinder: IWebViewProcessToMainProcessInterface? = null

    companion object {
        private var sInstance: WebViewProcessCommandDispatcher? = null
            get() {
                if (field == null) {
                    field = WebViewProcessCommandDispatcher()
                }
                return field
            }


        fun get(): WebViewProcessCommandDispatcher {
            return sInstance!!
        }
    }

    fun initAidlConnection() {
        val intent = Intent(BaseApplication.sApplication, MainProcessCommandService::class.java)
        BaseApplication.sApplication.bindService(intent, this, Context.BIND_AUTO_CREATE)


    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        mBinder = IWebViewProcessToMainProcessInterface.Stub.asInterface(service)

    }

    override fun onServiceDisconnected(name: ComponentName?) {
        mBinder = null
        initAidlConnection()
    }

    override fun onBindingDied(name: ComponentName?) {
        super.onBindingDied(name)
        mBinder = null
        initAidlConnection()
    }

    fun executeCommand(name: String, params: String){
        mBinder?.apply {
            this.handleWebCommand(name,params)
        }
    }

}