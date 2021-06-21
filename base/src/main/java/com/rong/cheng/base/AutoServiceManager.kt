package com.rong.cheng.base

import java.util.*

/**
 * @author: frc
 * @description:
 * @date:  2021/6/17 8:03 下午
 *
 */
object AutoServiceManager {
   fun <T> load(clazz: Class<T>):T{
       return ServiceLoader.load(clazz).iterator().next()
   }
}