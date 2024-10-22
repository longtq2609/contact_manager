package com.longtq.contact_manager.data.database.model

data class ContactDatabase(
    val id: Long,
    val name: String,
    val phone: String,
    val email: String,
    val isChecked: Boolean = false,
    val typeImage: Long
)