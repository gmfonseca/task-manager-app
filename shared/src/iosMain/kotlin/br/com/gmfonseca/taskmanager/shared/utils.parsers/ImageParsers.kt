package br.com.gmfonseca.taskmanager.shared.utils.parsers

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toCValues
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.UIKit.UIImage
import platform.posix.memcpy

fun ByteArray.toNative(): UIImage? = memScoped {
    toCValues().ptr
        .let { NSData.dataWithBytes(it, size.toULong()) }
        .let { UIImage.imageWithData(it) }
}

fun NSData.toByteArray(): ByteArray = ByteArray(length.toInt()).apply {
    usePinned { memcpy(it.addressOf(0), bytes, length) }
}
