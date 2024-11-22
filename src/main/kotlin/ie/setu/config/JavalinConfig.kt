package ie.setu.config

import ie.setu.controllers.UserController
import io.javalin.Javalin
import java.nio.file.Files
import java.nio.file.Paths

class JavalinConfig {

    fun startJavalinService(): Javalin {

        val app = Javalin.create{
                config ->
            // Add static file serving from the "public" directory
            config.staticFiles.add("/static")
        }.apply {
            exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("404 - Not Found") }
        }.start(7000)

        registerRoutes(app)
        return app
    }

    private fun registerRoutes(app: Javalin) {
        val userController = UserController()  // Inject DAO into the controller
        app.get("/api/users") { ctx -> userController.getAllUsers(ctx) }

        //end point to get swagger JSON file
        app.get("/swagger-docs") { ctx ->
            val swaggerPath = Paths.get("src/main/resources/swagger.json")
            val swaggerContent = Files.readString(swaggerPath)
            ctx.contentType("application/json").result(swaggerContent)
        }
    }
}