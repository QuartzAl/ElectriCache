package com.example.electricache.screens.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electricache.model.InventoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class InventoryViewModel: ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _itemList = MutableStateFlow(inventoryItems)

    val itemListFiltered = searchQuery.combine(_itemList) { query, items ->
        if (query.isBlank()) {
            items
        } else {
            items.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.partType.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = _itemList.value
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}


// make list of 5 dummy items
private val inventoryItems = listOf (
    InventoryItem(
        name = "Arduino Uno",
        description = "The Arduino Uno is a microcontroller board based on the ATmega328P. " +
                "It has 14 digital input/output pins (of which 6 can be used as PWM outputs)",
        lowStock = 1,
        quantity = 5,
        image = "https://www.arduino.cc/en/uploads/Products/Uno-TH.jpg",
        partType = "Microcontroller",
        id = "1"
    ),
    InventoryItem(
        name = "Arduino Mega",
        description = "The Arduino Mega 2560 is a microcontroller board based on the ATmega2560. " +
                "It has 54 digital input/output pins (of which 15 can be used as PWM outputs), ",
        lowStock = 1,
        quantity = 5,
        image = "https://www.arduino.cc/en/uploads/Main/ArduinoMega2560_R3_Front_450px.jpg",
        partType = "Microcontroller",
        id = "2"
    ),
    InventoryItem(
        name = "Arduino Nano",
        description = "The Arduino Nano is a small, complete, and breadboard-friendly board " +
                "based on the ATmega328 (Arduino Nano 3.x). It has more or less the same " +
                "functionality of the Arduino Duemilanove, but in a different package. ",
        lowStock = 2,
        quantity = 5,
        image = "https://www.arduino.cc/en/uploads/Main/ArduinoNanoFront_3_sm.jpg",
        partType = "Microcontroller",
        id = "3"
    ),
    InventoryItem(
        name = "Arduino Nano 33 BLE",
        description = "The Arduino Nano 33 BLE is a miniature sized module containing a ublox " +
                "NINA B306 module, based on Nordic nRF52480 and containing a powerful Cortex M4F. " +
                "The Nano 33 BLE (without headers) is Arduino’s 3.3V compatible board in the " +
                "smallest available form factor: 45x18mm!",
        lowStock = 2,
        quantity = 5,
        image = "https://www.arduino.cc/en/uploads/Main/Nano33BLE_WithoutHeaders.jpg",
        partType = "Microcontroller",
        id = "4"
    ),
    InventoryItem(
        name = "Arduino Nano 33 IoT",
        description = "The Arduino Nano 33 IoT is the easiest and cheapest point of entry to enhance existing devices (and creating new ones) to be part of the IoT and designing pico-network applications. Whether you are looking at building a sensor network connected to your office or home router, or if you want to create a BLE device sending data to a cellphone, the Arduino Nano 33 IoT is your one-stop-solution for many of the basic IoT application scenarios.",
        lowStock = 3,
        quantity = 5,
        image = "https://www.arduino.cc/en/uploads/Main/Nano33IoT_WithoutHeaders.jpg",
        partType = "Microcontroller",
        id = "5"
    ),
)