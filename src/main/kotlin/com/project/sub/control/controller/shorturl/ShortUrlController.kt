package com.project.sub.control.controller.shorturl

import com.project.sub.control.entity.ShortUrlMap
import com.project.sub.control.repository.OriginalSubUrlRepository
import com.project.sub.control.repository.ShortUrlMapRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.net.URLDecoder
import java.net.URLEncoder


@RestController
@RequestMapping("/api/short_url")
@Validated
class ShortUrlController {

    @Autowired
    lateinit var shortUrlMapRepository: ShortUrlMapRepository

    @Autowired
    lateinit var originalSubUrlRepository: OriginalSubUrlRepository

    private val restTemplate = RestTemplate()


    val shortUrlDomain = "http://localhost:8080/"

    @PatchMapping("/{short_url_id}")
    fun boundSubUrlByUrlId(
        @PathVariable short_url_id: String,
        @RequestBody @Valid request: UpdateShortUrlRequest
    ): ResponseEntity<String> {
        val originalSubUrlOptional = originalSubUrlRepository.findOriginalSubUrlByUrlId(request.subUrlId)
        val shortUrlMapObj = shortUrlMapRepository.findShortUrlMapByShortUrl(short_url_id)
        if (originalSubUrlOptional.isEmpty || shortUrlMapObj.isEmpty) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SubUrl were not found")
        } else {
            val shortUrlMap = shortUrlMapObj.get()
            shortUrlMap.subscriptionUrl = originalSubUrlOptional.get().subscriptionUrl
            shortUrlMapRepository.save(shortUrlMap)
            return ResponseEntity.status(HttpStatus.OK).body("subscription url updated for ${short_url_id}")
        }
    }

    @GetMapping()
    fun findAllSubUrl(): ResponseEntity<List<ShortUrlMap>> {
        val shortUrlMaps = shortUrlMapRepository.findAll()
        return ResponseEntity.status(HttpStatus.OK).body(shortUrlMaps)
    }

    fun decode(url: String) = URLDecoder.decode(url, "UTF-8")
    fun encode(url: String) = URLEncoder.encode(url, "UTF-8")

    @GetMapping("{short_url}")
    fun directShortUrlToSubUrl(@PathVariable short_url: String): ResponseEntity<String> {
//        val shortUrlMaps = shortUrlMapRepository.findShortUrlMapByShortUrl(short_url)
//        if (shortUrlMaps.isEmpty || shortUrlMaps.get().shortUrl.isEmpty()) {
//            throw Exception("${short_url} is not found")
//        }
//        val subscriptionUrl = shortUrlMaps.get().subscriptionUrl
//        if (subscriptionUrl == null) {
//            throw Exception("Subscription url for ${short_url} is not found")
//        }
//        log.info("redirect to $subscriptionUrl")
        if (short_url.equals("6579f4c1")) {
            return ResponseEntity.status(HttpStatus.OK).body(
                "port: 7890\n" +
                        "socks-port: 7891\n" +
                        "allow-lan: true\n" +
                        "mode: Rule\n" +
                        "log-level: info\n" +
                        "external-controller: :9090\n" +
                        "proxies:\n" +
                        "  - {name: 防失联加微:proxy101kefu, server: ydyh.nanbeicloud.top, port: 26901, type: ss, cipher: chacha20-ietf-poly1305, password: 81dae052-7cf3-485b-9a9c-c017d041aab6, udp: true}\n" +
                        "  - {name: \uD83C\uDDED\uD83C\uDDF0 香港 1-01, server: line2.doves.top, port: 32544, type: ss, cipher: aes-128-gcm, password: 01285e63-fdd0-4f41-91b3-eab582e3c0be, udp: true}\n" +
                        "  - {name: \uD83C\uDDED\uD83C\uDDF0 香港 1-02, server: line1.doves.top, port: 32544, type: ss, cipher: aes-128-gcm, password: 01285e63-fdd0-4f41-91b3-eab582e3c0be, udp: true}\n" +
                        "  - {name: \uD83C\uDDED\uD83C\uDDF0 香港 2-01, server: line2.doves.top, port: 43715, type: ss, cipher: aes-128-gcm, password: 01285e63-fdd0-4f41-91b3-eab582e3c0be, udp: true}\n" +
                        "  - {name: \uD83C\uDDED\uD83C\uDDF0 香港 2-02, server: line1.doves.top, port: 43715, type: ss, cipher: aes-128-gcm, password: 01285e63-fdd0-4f41-91b3-eab582e3c0be, udp: true}\n" +
                        "  - {name: \uD83C\uDDE8\uD83C\uDDF3 台湾 1-01, server: line2.doves.top, port: 36861, type: ss, cipher: aes-128-gcm, password: 01285e63-fdd0-4f41-91b3-eab582e3c0be, udp: true}\n" +
                        "  - {name: \uD83C\uDDE8\uD83C\uDDF3 台湾 1-02, server: line1.doves.top, port: 36861, type: ss, cipher: aes-128-gcm, password: 01285e63-fdd0-4f41-91b3-eab582e3c0be, udp: true}\n" +
                        "  - {name: \uD83C\uDDEF\uD83C\uDDF5 日本 1-01, server: line2.doves.top, port: 46507, type: ss, cipher: aes-128-gcm, password: 01285e63-fdd0-4f41-91b3-eab582e3c0be, udp: true}\n" +
                        "  - {name: \uD83C\uDDEF\uD83C\uDDF5 日本 1-02, server: 546893ed91ee556f.cdn.jiashule.com, port: 28277, type: ss, cipher: aes-128-gcm, password: 01285e63-fdd0-4f41-91b3-eab582e3c0be, udp: true}\n" +
                        "  - {name: \uD83C\uDDEF\uD83C\uDDF5 日本 2-01, server: line2.doves.top, port: 12848, type: ss, cipher: aes-128-gcm, password: 01285e63-fdd0-4f41-91b3-eab582e3c0be, udp: true}\n" +
                        "  - {name: \uD83C\uDDEF\uD83C\uDDF5 日本 2-02, server: 546893ed91ee556f.cdn.jiashule.com, port: 30625, type: ss, cipher: aes-128-gcm, password: 01285e63-fdd0-4f41-91b3-eab582e3c0be, udp: true}\n" +
                        "  - {name: \uD83C\uDDF8\uD83C\uDDEC 新加坡 1-01, server: line2.doves.top, port: 28751, type: ss, cipher: aes-128-gcm, password: 01285e63-fdd0-4f41-91b3-eab582e3c0be, udp: true}\n" +
                        "  - {name: \uD83C\uDDF8\uD83C\uDDEC 新加坡 1-02, server: 5f87e0aaad10229d.cdn.jiashule.com, port: 57539, type: ss, cipher: aes-128-gcm, password: 01285e63-fdd0-4f41-91b3-eab582e3c0be, udp: true}\n" +
                        "  - {name: \uD83C\uDDE6\uD83C\uDDEA 迪拜 1-01, server: ceshi.leiyingchuanmei.com, port: 51233, type: ss, cipher: aes-128-gcm, password: 01285e63-fdd0-4f41-91b3-eab582e3c0be, udp: true}\n" +
                        "proxy-groups:\n" +
                        "  - name: Proxies\n" +
                        "    type: select\n" +
                        "    proxies:\n" +
                        "      - 防失联加微:proxy101kefu\n" +
                        "      - \uD83C\uDDED\uD83C\uDDF0 香港 1-01\n" +
                        "      - \uD83C\uDDED\uD83C\uDDF0 香港 1-02\n" +
                        "      - \uD83C\uDDED\uD83C\uDDF0 香港 2-01\n" +
                        "      - \uD83C\uDDED\uD83C\uDDF0 香港 2-02\n" +
                        "      - \uD83C\uDDE8\uD83C\uDDF3 台湾 1-01\n" +
                        "      - \uD83C\uDDE8\uD83C\uDDF3 台湾 1-02\n" +
                        "      - \uD83C\uDDEF\uD83C\uDDF5 日本 1-01\n" +
                        "      - \uD83C\uDDEF\uD83C\uDDF5 日本 1-02\n" +
                        "      - \uD83C\uDDEF\uD83C\uDDF5 日本 2-01\n" +
                        "      - \uD83C\uDDEF\uD83C\uDDF5 日本 2-02\n" +
                        "      - \uD83C\uDDF8\uD83C\uDDEC 新加坡 1-01\n" +
                        "      - \uD83C\uDDF8\uD83C\uDDEC 新加坡 1-02\n" +
                        "      - \uD83C\uDDE6\uD83C\uDDEA 迪拜 1-01\n" +
                        "rules:\n" +
                        "  - DOMAIN-SUFFIX,1password.com,DIRECT\n" +
                        "  - DOMAIN-SUFFIX,vultr.com,DIRECT\n" +
                        "  - DOMAIN-SUFFIX,mb3admin.com,DIRECT\n" +
                        "  - DOMAIN-SUFFIX,rixcloud.io,DIRECT\n" +
                        "  - DOMAIN-SUFFIX,tempestapp.io,DIRECT\n" +
                        "  - DOMAIN-SUFFIX,baidu.com,DIRECT\n" +
                        "  - DOMAIN-SUFFIX,baidu-int.com,DIRECT\n" +
                        "  - DOMAIN-SUFFIX,erebor.douban.com,DIRECT\n" +
                        "  - DOMAIN,mtalk.google.com,DIRECT\n" +
                        "  - DOMAIN,alt1-mtalk.google.com,DIRECT\n" +
                        "  - DOMAIN,alt2-mtalk.google.com,DIRECT\n" +
                        "  - DOMAIN,alt3-mtalk.google.com,DIRECT\n" +
                        "  - DOMAIN,alt4-mtalk.google.com,DIRECT\n" +
                        "  - DOMAIN,alt5-mtalk.google.com,DIRECT\n" +
                        "  - DOMAIN,alt6-mtalk.google.com,DIRECT\n" +
                        "  - DOMAIN,alt7-mtalk.google.com,DIRECT\n" +
                        "  - DOMAIN,alt8-mtalk.google.com,DIRECT\n" +
                        "  - DOMAIN,alt9-mtalk.google.com,DIRECT\n" +
                        "  - DOMAIN,captive.apple.com,DIRECT\n" +
                        "  - DOMAIN,time-ios.apple.com,DIRECT\n" +
                        "  - DOMAIN-SUFFIX,gateway.push-apple.com.akadns.net,DIRECT\n" +
                        "  - DOMAIN-SUFFIX,push.apple.com,DIRECT\n" +
                        "  - DOMAIN-KEYWORD,github,Proxies\n" +
                        "  - DOMAIN-SUFFIX,github.com,Proxies\n" +
                        "  - DOMAIN-SUFFIX,github.io,Proxies\n" +
                        "  - DOMAIN-SUFFIX,githubapp.com,Proxies\n" +
                        "  - DOMAIN-SUFFIX,githubassets.com,Proxies\n" +
                        "  - DOMAIN-SUFFIX,githubusercontent.com,Proxies\n" +
                        "  - DOMAIN-SUFFIX,home-intl.console.aliyun.com,Proxies\n" +
                        "  - DOMAIN,ip.skk.moe,Proxies\n" +
                        "  - DOMAIN,ip.sb,Proxies\n" +
                        "  - DOMAIN-SUFFIX,googleapis.cn,Proxies\n" +
                        "  - DOMAIN-SUFFIX,maying.co,Proxies\n" +
                        "  - DOMAIN-SUFFIX,flowercloud.net,Proxies\n" +
                        "  - DOMAIN-SUFFIX,socloud.me,Proxies\n" +
                        "  - DOMAIN-SUFFIX,ytoo.asia,Proxies\n" +
                        "  - DOMAIN-SUFFIX,ytoo.co.uk,Proxies\n" +
                        "  - GEOIP,CN,DIRECT\n" +
                        "  - MATCH,Proxies"
            )
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found")
        }

    }
}