package com.longtq.contact_manager.data.mapper

import com.longtq.contact_manager.data.database.model.ContactDatabase
import com.longtq.contact_manager.data.local.model.ContactLocal
import com.longtq.contact_manager.domain.model.Contact

fun ContactDatabase.toDomain(): Contact {
    return Contact(
        id = id,
        name = name,
        phone = phone,
        email = email,
        typeImage = typeImage,
        isChecked = false
    )
}

fun Contact.toDatabase(): ContactDatabase {
    return ContactDatabase(
        id = id,
        name = name,
        phone = phone,
        email = email,
        typeImage = typeImage
    )
}

fun ContactLocal.toDomain(): Contact {
    return Contact(
        id = -1,
        name = name,
        phone = phone,
        email = email,
        typeImage = 0,
        isChecked = false
    )
}
