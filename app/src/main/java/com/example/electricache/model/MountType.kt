package com.example.electricache.model

enum class MountType {
    None,
    THT,
    SMD;

    companion object {
        fun getByName(name: String?): MountType {
            values().forEach { mountType -> if (name == mountType.name) return mountType }

            return None
        }

        fun getOptions(): List<String> {
            val options = mutableListOf<String>()
            values().forEach { mountType -> options.add(mountType.name) }
            return options
        }
    }
}