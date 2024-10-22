package com.longtq.contact_manager.data.database.model

data class Error(
    val error: DatabaseError,
    val t: Throwable? = null
)

enum class DatabaseError(val message: String) {
    NetworkError("Network error"), UnknownResponse("Unknown response"), UnknownError("Unknown Error")
}
