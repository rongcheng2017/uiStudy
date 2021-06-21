package com.rong.cheng.webview.command

/**
 * @author: frc
 * @description:
 * @date:  2021/6/18 5:50 下午
 *
 */
interface Command {
    fun name():String

    fun execute(parameters:Map<String,String>)
}