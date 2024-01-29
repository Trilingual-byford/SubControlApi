package com.project.sub.control.utils

import java.math.BigInteger
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

fun String.encodeToShortUrl(truncateLength: Int = 6): String {
    // hash String with MD5
    val hashBytes = MessageDigest.getInstance("MD5").digest(System.nanoTime().toString().toByteArray())
    // transform to human readable MD5 String
    val hashString = String.format("%032x", BigInteger(1, hashBytes))
    // truncate MD5 String
    val truncatedHashString = hashString.take(truncateLength)
    // return id
    return truncatedHashString
}

fun String.encodeUrl(): String {
    return URLEncoder.encode("$this", StandardCharsets.UTF_8.toString());
}

