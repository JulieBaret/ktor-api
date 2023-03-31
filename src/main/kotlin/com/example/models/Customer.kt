//defines the data that's associated with a customer

package com.example.models

import kotlinx.serialization.Serializable

//class
@Serializable
data class Customer(val id: String, val firstName: String, val lastName: String, val email: String)

//storage
val customerStorage = mutableListOf<Customer>()