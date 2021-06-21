package com.rong.cheng.webview.mainprocess

import com.google.gson.Gson
import com.rong.cheng.webview.IWebViewProcessToMainProcessInterface
import com.rong.cheng.webview.command.Command
import java.util.*
import kotlin.collections.HashMap

/**
 * @author: frc
 * @description:
 * @date:  2021/6/18 5:14 下午
 *
 */
class MainProcessCommandsManager private constructor() :
    IWebViewProcessToMainProcessInterface.Stub() {

    companion object {

        private var sInstance: MainProcessCommandsManager? = null
            get() {
                if (field == null) {
                    field = MainProcessCommandsManager()
                }
                return field
            }


        fun get(): MainProcessCommandsManager {
            return sInstance!!
        }
    }
    private val mCommands: HashMap<String, Command> = HashMap()

    init {
        val load = ServiceLoader.load(Command::class.java)
        for (command in load) {
            if (!mCommands.containsKey(command.name())) {
                mCommands[command.name()] = command
            }
        }
    }



    override fun handleWebCommand(commandName: String?, jsonParams: String?) {
        if (commandName.isNullOrEmpty()) return
        jsonParams?.apply {
            executeCommand(commandName,
                Gson().fromJson(jsonParams, Map::class.java) as Map<String, String>)
        }.also {
            executeCommand(commandName)
        }
    }

    private fun executeCommand(commandName: String, jsonParams: Map<String, String>) {
        mCommands[commandName]?.execute(jsonParams)
    }

    private fun executeCommand(commandName: String) {

    }
}