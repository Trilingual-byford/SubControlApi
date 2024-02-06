package com.project.sub.control.utils

import com.project.sub.control.entity.ServerNode
import org.springframework.web.util.UriUtils
import java.math.BigInteger
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*

data class NodeDto(val url: String, val comment: String)

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
    return URLEncoder.encode(this, StandardCharsets.UTF_8.toString());
}

fun String.decodeBase64(): String {
    return String(Base64.getMimeDecoder().decode(this))
}

fun String.encodeBase64(): String {
    return Base64.getMimeEncoder().encodeToString(this.toByteArray())
}

fun String.extractServerCommentList(): String {
    return Base64.getMimeEncoder().encodeToString(this.toByteArray())
}

fun urlDecode(url: String) = UriUtils.decode(url, "UTF-8")
fun urlEncode(url: String) = UriUtils.encode(url, "UTF-8")

fun String.isContainSensitiveWord(): Boolean {
    val sensitiveWordsList = listOf("流量", "剩余", "重置", "到期")
    sensitiveWordsList.forEach {
        if (this.contains(it)) return true
    }
    return false
}

fun String.base64ToServerNodeDto(): List<NodeDto> {
    val decodeBase64 = this.decodeBase64()
    val nodeDtoLst = mutableListOf<NodeDto>()
    decodeBase64.split("\n").forEach {
        val split = it.split("#")
        if (split.size < 2) return@forEach
        val url = split.get(0)
        val comment = urlDecode(split.get(1)).removeEmptyLine()
        if (comment.isContainSensitiveWord()) return@forEach
        nodeDtoLst.add(NodeDto(url, comment))
    }
    return nodeDtoLst
}

fun List<NodeDto>.toBase64(): String {
    val sb = StringBuilder()
    this.forEach {
        if (it.comment.isContainSensitiveWord()) return@forEach
        val encodedComment = (it.comment.encodeUrl())
        sb.append("$it.url#$encodedComment")
        sb.append("#")
        sb.append("\n")
    }
    return sb.toString()
}

fun List<NodeDto>.toServerNodeList(subUrl: String?): List<ServerNode> {
    val serverNodeList = mutableListOf<ServerNode>()
    this.forEach {
        serverNodeList.add(ServerNode(0, it.url, it.comment, null, subUrl, null))
    }
    return serverNodeList
}

fun String.removeEmptyLine(): String {
    return this.replace(Regex("(\\n|\\r|\\n\\r)"), "|")
}

fun List<NodeDto>.toSubUrl(baseUrl: String): String {
    if (this.isEmpty()) return ""
    val sb = StringBuilder()
    sb.append(baseUrl)
    this.forEachIndexed { index, nodeDto ->
        if (index == 0) {
            val connectorNode =
                "ss://Y2hhY2hhMjAtaWV0Zi1wb2x5MTMwNTo4MWRhZTA1Mi03Y2YzLTQ4NWItOWE5Yy1jMDE3ZDA0MWFhYjY@ydyh.nanbeicloud.top:26901" + "#" + "防失联加微:proxy101kefu|"
            val urlEncodedForConnector = connectorNode.encodeUrl()
            sb.append(urlEncodedForConnector)
        }
        val subUrlServerNode =
            nodeDto.url + "#" + nodeDto.comment // url#comment
        val urlEncodedForSub = subUrlServerNode.encodeUrl() // url#comment
        sb.append(urlEncodedForSub)
    }
    sb.append("&insert=false&config=https%3A%2F%2Fcdn.jsdelivr.net%2Fgh%2FSleepyHeeead%2Fsubconverter-config%40master%2Fremote-config%2Fspecial%2Fbasic.ini&emoji=true&list=false&tfo=false&scv=true&fdn=false&sort=false&new_name=true")
    return sb.toString()
}

