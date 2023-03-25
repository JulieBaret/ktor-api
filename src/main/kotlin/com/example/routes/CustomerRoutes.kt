package com.example.routes

import com.example.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("/customer") {
        get {
            if (customerStorage.isNotEmpty()) {
                //this function below can take a Kotlin object and return it serialized in a specific format
                call.respond(customerStorage)
            } else {
                call.respondText("No customer found", status = HttpStatusCode.OK)
            }
        }
        get("{id?}") {
            //check whether the parameter id exists in the request
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            ) // if the id parameter does not exist in the request, respond with a 400 Bad Request status code, an error message and are done
            // if the id parameter exist in the request, try to find the corresponding record in customerStorage
            val customer =
                customerStorage.find { it.id == id } ?: return@get call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                ) // if there isn't recording record, return a 404 Not Found and an error message
            call.respond(customer) // if we find it, we respond with the object
        }
        post {
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {

        }
    }
}