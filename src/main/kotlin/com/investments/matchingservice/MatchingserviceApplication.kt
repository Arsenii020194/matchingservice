package com.investments.matchingservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableKafka
class MatchingserviceApplication

fun main(args: Array<String>) {
	runApplication<MatchingserviceApplication>(*args)
}
