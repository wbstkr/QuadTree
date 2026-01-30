package io.github.wbstkr

import processing.core.PApplet

class Main : PApplet() {
    val balls = mutableListOf<Ball>()

    override fun settings() {
        size(600, 600)
    }

    override fun setup() {
        repeat(100) {
            balls.add(Ball(random(width.toFloat()), random(height.toFloat()), 10f))
        }
    }

    override fun draw() {
        background(0)
        balls.forEach {
            it.render(this)
        }
    }
}

fun main(args: Array<String>) {
    PApplet.main(Main::class.java.name)
}