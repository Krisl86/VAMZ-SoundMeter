package com.example.soundmeter.enums

enum class NoiseReference(val description: String, val minValue: Double, val maxValue: Double) {
    WHISPER("Whisper, Leaves rustling", 0.0, 29.0),
    HOME("Average home noise", 30.0, 49.0),
    CONVERSATION("Conversation, Office noise", 50.0, 69.0),
    VACUUM("Vacuum cleaner, Heavy traffic", 70.0, 89.0),
    MOTORCYCLE("Motorcycle, Boom box", 90.0, 109.0),
    CHAINSAW("Chainsaw, Leaf blower", 110.0, 119.0),
    CONCERT("Concert, Racing car", 120.0, 129.0),
    GUN("Gun shot, Jet engine", 130.0, 140.0);

    companion object {
        fun stringByValue(value: Double): String {
            return if (value <= 0)
                ""
            else
                NoiseReference.values()
                    .filter { it.minValue <= value && value <= it.maxValue }
                    .map { it.description }
                    .elementAt(0)
        }
    }
}