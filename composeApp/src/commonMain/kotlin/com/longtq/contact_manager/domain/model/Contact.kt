package com.longtq.contact_manager.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    val id: Long = 0,
    val name: String,
    val phone: String,
    val email: String,
    val isChecked: Boolean? = false,
    val typeImage: Long = 0
)