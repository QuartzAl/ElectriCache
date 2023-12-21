package com.example.electricache.model

enum class PartType {
    None,
    Resistor,
    Capacitor,
    Inductor,
    Diode,
    Transistor,
    IC,
    Connector,
    Switch,
    Fuse,
    Battery,
    LED,
    Crystal,
    Oscillator,
    Potentiometer,
    Relay,
    Sensor,
    Transformer,
    Wire,
    Cable,
    Other;

    companion object {
        fun getByName(name: String?): PartType {
            values().forEach { partType -> if (name == partType.name) return partType }

            return None
        }

        fun getOptions(): List<String> {
            val options = mutableListOf<String>()
            values().forEach { partType -> options.add(partType.name) }
            return options
        }
    }
}