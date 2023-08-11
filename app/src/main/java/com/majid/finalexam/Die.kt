package com.majid.finalexam

import kotlin.random.Random
enum class Color {
    RED,
    WHITE,
    BLACK,
    Orange
}
// Primary constructor is in the class signature
class Die(private val color: Color, private val numSides: Int) {
    var sideUp: Int = 1
        private set

    // Called each time we initialize a new Die object.
    init {
        sideUp = Random.nextInt(1, numSides + 1)
    }

    // Two Secondary constructors that call the primary constructor
    constructor(numSides: Int) : this(Color.WHITE, numSides)

    // Roll function which returns a random value
    fun roll(): Int{
        sideUp = Random.nextInt(1, numSides + 1)
        return sideUp
    }
    // Getter for highest side


    // Setter method to set sideUp
    fun setSide(num: Int){
        if(num < numSides){
            sideUp = num
        }
        else{
            println("The Side is out of range")
        }
    }
}