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
                //the function below takes a Kotlin object and return it serialized in a specific format
                call.respond(customerStorage)
            } else {
                call.respondText("No customer found", status = HttpStatusCode.OK)
            }
            //l'objet "call" représente la demande HTTP entrante
            //permet d'accéder aux détails de la demande HTTP (body, paramètres, en-têtes, etc.)
            //fournit les méthodes respondText, respond, respondHtml, etc. pour renvoyer des réponses HTTP à partir de la route.
            //permet de récupérer des informations sur la session en cours, l'utilisateur authentifié, le contexte d'application, etc.
        }
        get("{id?}") {
            //check whether the parameter id exists in the request
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            //return@get spécifie le retour de la fonction de rappel (callback) de type GET
            //ici, ça signifie que le code doit retourner immédiatement la fonction call.respondText() et ne pas continuer à exécuter le reste du code.
            //if the id parameter does not exist in the request, it responds with a 400 Bad Request status code, an error message and is done

            val customer =
                //if it exists, try to find (with a lambda function which iterate through a collection) the corresponding record in customerStorage
                customerStorage.find { it.id == id } ?: return@get call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )
            //if there isn't recording record, return a 404 Not Found and an error message
            //if we find it, we respond with the object
            call.respond(customer)
        }
        //option for a client to POST a JSON representation of a client object
        post {
            val customer = call.receive<Customer>() //automatically deserializes the JSON request body into a Kotlin Customer object
            customerStorage.add(customer) //add the customer to storage and respond with a status code of 201 Created
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)

            //La condition ci-dessous cherche le client dans le stockage customerStorage dont l'id correspond à la variable id passée dans la requête
            //Si un tel client est trouvé et supprimé avec succès, la condition renvoie true, sinon elle renvoie false
            if (customerStorage.removeIf { it.id == id }) {
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}