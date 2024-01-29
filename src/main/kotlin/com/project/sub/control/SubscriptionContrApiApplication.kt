package com.project.sub.control

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//@SpringBootApplication(exclude = arrayOf(SecurityAutoConfiguration::class, ManagementWebSecurityAutoConfiguration::class))
@SpringBootApplication
class SubscriptionContrApiApplication

fun main(args: Array<String>) {
    runApplication<SubscriptionContrApiApplication>(*args)
    run {
//        val env = System.getenv("SubscriptionApplicationURL")
//        assertNotNull(env)
//
//        println(env)
    }
}
