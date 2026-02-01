package io.github.wbstkr

import processing.core.PApplet

private const val NUM_OF_BALLS = 5000
private const val MOUSE_BOUNDARY_RADIUS = 50f

class Main : PApplet() {
    val balls = mutableListOf<Ball>()
    lateinit var quadTree: QuadTree

    override fun settings() {
        size(600, 600)
    }

    override fun setup() {
        quadTree = QuadTree(0f, 0f, width.toFloat(), height.toFloat())
        repeat(NUM_OF_BALLS) {
            val randomX = random(width.toFloat())
            val randomY = random(height.toFloat())
            val randomR = random(10f, 20f)
            val newBall = Ball(randomX, randomY, randomR)
            balls.add(newBall)
            quadTree.insert(newBall)
        }
    }

    override fun draw() {
        background(0)
        balls.forEach { it.render(this) }
        quadTree.render(this)

        val mouseHitBox = HitBox(
            mouseX - MOUSE_BOUNDARY_RADIUS,
            mouseY - MOUSE_BOUNDARY_RADIUS,
            MOUSE_BOUNDARY_RADIUS * 2,
            MOUSE_BOUNDARY_RADIUS * 2
        )
        quadTree.queryRange(mouseHitBox).forEach {
            noFill()
            stroke(255f)
            strokeWeight(1f)
            ellipse(it.position.x, it.position.y, it.radius, it.radius)
        }
        mouseHitBox.render(this)
    }
}

fun main() {
    PApplet.main(Main::class.java.name)
}