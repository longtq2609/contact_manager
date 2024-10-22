package com.longtq.contact_manager.data.local.model

import kotlinx.serialization.Serializable

@Serializable
data class ContactLocal(
    val name: String,
    val phone: String,
    val email: String,
)