package com.majid.finalexam

import kotlin.random.Random
enum class Color {
    RED,
    WHITE,
    BLACK,
    Orange
}

enum class Sides(val value: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    ELEVEN(11),
    TWELVE(12),
    THIRTEEN(13),
    FOURTEEN(14),
    FIFTEEN(15),
    SIXTEEN(16),
    SEVENTEEN(17),
    EIGHTEEN(18),
    NINETEEN(19),
    TWENTY(20)
}
// Primary constructor is in the class signature
class Die(private val color: Color, private val numSides: Sides) {
    var sideUp: Int = 1
        private set

    // Called each time we initialize a new Die object.
    init {
        sideUp = Random.nextInt(1, numSides.value + 1)
        println("Creating a ${color.name.lowercase()} d${numSides.value}")
    }

    // Two Secondary constructors that call the primary constructor
    constructor() : this(Color.WHITE, Sides.SIX)
    constructor(numSides: Sides) : this(Color.WHITE, numSides)

    // Roll function which returns a random value
    fun roll(): String{
        sideUp = Random.nextInt(1, numSides.value + 1)
        return "Rolling the ${color.name.lowercase()} d${numSides.value}..."
    }
    // Getter for highest side
    fun getHighestSide(): Int{
        return numSides.value
    }

    // Setter method to set sideUp
    fun setSide(num: Int){
        if(num < numSides.value){
            println("Setting the d${numSides.value} to ${num}")
            sideUp = num
        }
        else{
            println("The Side is out of range")
        }
    }

    // Prints die color and highest side with its current sideUp
    override fun toString(): String {
        return "A ${color.name.lowercase()} die with ${numSides.value} faces and currently showing a $sideUp."
    }
}