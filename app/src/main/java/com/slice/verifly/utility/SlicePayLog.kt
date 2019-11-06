package com.slice.verifly.utility

import com.slicepay.logger.log.SlicePayLogger

object SlicePayLog {

    fun debug(tag: String, msg: String?) = SlicePayLogger.debug(tag, msg)

    fun error(tag: String, msg: String?) = SlicePayLogger.error(tag, msg)

    fun info(tag: String, msg: String?) = SlicePayLogger.info(tag, msg)

    fun warn(tag: String, msg: String?) = SlicePayLogger.warn(tag, msg)

    fun verbose(tag: String, msg: String?) = SlicePayLogger.verbose(tag, msg)
}