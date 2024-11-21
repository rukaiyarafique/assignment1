package com.example.demo

import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import io.javalin.Javalin
import org.junit.jupiter.api.Assertions.*
import kong.unirest.Unirest

import com.example.demo.DemoApplication
import org.springframework.boot.runApplication


class DemoApplicationTest {

    companion object {
//        lateinit var app: Javalin

        @BeforeAll
        @JvmStatic
        fun setUp() {
            var application = runApplication<DemoApplication>()
//            app = Javalin.create().start(7000)
//            app.get("/") { ctx -> ctx.result("Hello, World!") }
//            app.get("/greet/{name}") { ctx -> ctx.result("Hello, ${ctx.pathParam("name")}!") }
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
//            app.stop()
        }
    }

//    @Test
//    fun testHelloWorld() {
//        val response: HttpResponse<String> = Unirest.get("http://localhost:7000/")
//            .asString()
//
//        assertEquals(200, response.status)
//        assertEquals("Hello, World!", response.body)
//    }
//
//    @Test
//    fun testGreeting() {
//        val response: HttpResponse<String> = Unirest.get("http://localhost:7000/greet/Rukaiya")
//            .asString()
//
//        assertEquals(200, response.status)
//        assertEquals("Hello, Rukaiya!", response.body)
//    }
    @Test
    fun testDemo(){
        assertEquals(1,1)
    }

    @Test
    fun testJavalinRoute() {
        val response: HttpResponse<String> = Unirest.get("http://localhost:7000/javalin-route")
            .asString()
        assertEquals(200, response.status)
        assertEquals("Hello from Javalin!", response.body)
    }

    @Test
    fun testGetAllUser() {
        val response: HttpResponse<String> = Unirest.get("http://localhost:7000/api/users")
            .asString()
        assertEquals(200, response.status)
        println("============"+response.body)
    }

    @Test
    fun testGetTrackerById() {
        val response: HttpResponse<String> = Unirest.get("http://localhost:7000/api/trackers/2")
            .asString()
        assertEquals(200, response.status)
        println("============"+response.body)
    }

}
