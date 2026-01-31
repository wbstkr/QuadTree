package io.github.wbstkr

import processing.core.PApplet

class Main : PApplet() {
    val balls = mutableListOf<Ball>()

    override fun settings() {
        size(600, 600)
    }

    override fun setup() {
        repeat(100) {
            val randomX = random(width.toFloat())
            val randomY = random(height.toFloat())
            val randomR = random(5f, 10f)
            balls.add(Ball(randomX, randomY, randomR))
        }
    }

    override fun draw() {
        background(0)
        balls.forEach { it.render(this) }
    }
}

fun main() {
    PApplet.main(Main::class.java.name)
}