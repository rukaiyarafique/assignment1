package com.example.demo

import com.example.demo.healthInfo.Tracker
import com.example.demo.healthInfo.TrackerContoller
import com.example.demo.users.UserController
import io.javalin.Javalin
import io.javalin.http.pathParamAsClass
import org.eclipse.jetty.server.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

import com.example.demo.users.User
import io.javalin.http.bodyAsClass

import java.nio.file.Files
import java.nio.file.Paths


@SpringBootApplication
class DemoApplication {
    @Bean
    fun javalin(userController: UserController, trackerController: TrackerContoller): Javalin {
        val app = Javalin.create {
            it.jetty.server { Server() }
            it.staticFiles.add("/static")

        }.start(7000)

        app.get("/javalin-route") { ctx -> ctx.result("Hello from Javalin!") }

        app.get("/api/users"){
                ctx -> ctx.json(userController.getAllUsers())
        }

        app.get("api/users/{user-id}") { ctx ->
            val userId = ctx.pathParamAsClass<Int>("user-id").get()
            ctx.json(userController.getUserById(userId))
        }

        app.post("api/users") { ctx ->
            val user = ctx.bodyAsClass<User>()
            ctx.json(userController.createUser(user))
        }

        app.delete("api/users/{user-id}") { ctx ->
            val userId = ctx.pathParamAsClass<Int>("user-id").get()
            val trackers = trackerController.getAllTrackers()
            val trackersToDelete = trackers.filter { it.userId == userId }
            if(trackerController.deletedTrackerByUserId(trackersToDelete))
                ctx.json(userController.deletedUSerById(userId))
            else
                ctx.status(404)
        }

        // paths for trackers
        app.get("/api/trackers"){
                ctx -> ctx.json(trackerController.getAllTrackers())
        }

        app.get("api/trackers/{user-id}") { ctx ->
            val userId = ctx.pathParamAsClass<Int>("user-id").get()
            val trackers = trackerController.getAllTrackers()
            val trackersToView = trackers.filter { it.userId == userId }
            ctx.json(trackersToView)
        }


        app.post("api/trackers") { ctx ->
            val tracker = ctx.bodyAsClass<Tracker>()
            ctx.json(trackerController.createTracker(tracker))
        }

        app.delete("api/trackers/{tracker-id}") { ctx ->
            val trackerId = ctx.pathParamAsClass<Int>("tracker-id").get()
            ctx.json(trackerController.deletedTrackerById(trackerId))
        }

        // get Swagger JSON
        app.get("/swagger-docs") { ctx ->
            val swaggerPath = Paths.get("src/main/resources/swagger.json")
            val swaggerContent = Files.readString(swaggerPath)
            ctx.contentType("application/json").result(swaggerContent)
        }

        return app
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
