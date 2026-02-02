package io.github.wbstkr

import processing.core.PApplet
import processing.core.PVector

private const val NUM_OF_BALLS = 5000
private const val MOUSE_BOUNDARY_RADIUS = 50f

class Main : PApplet() {
    val balls = mutableListOf<Ball>()
    lateinit var quadTree: QuadTree

    override fun settings() {
        size(600, 600)
    }

    override fun setup() {
        repeat(NUM_OF_BALLS) {
            val randomX = random(width.toFloat())
            val randomY = random(height.toFloat())
            val randomD = random(10f, 20f)
            val newBall = Ball(randomX, randomY, randomD)
            balls.add(newBall)
        }
    }

    override fun draw() {
        quadTree = QuadTree(0f, 0f, width.toFloat(), height.toFloat())

        background(0)

        balls.forEach {
            it.update(this)
            quadTree.insert(it)
        }
        balls.forEach { ball ->
            val ballHitBox = HitBox(
                ball.position.x - ball.radius, ball.position.y - ball.radius, ball.diameter, ball.diameter
            )
            val ballsToCheck = quadTree.queryRange(ballHitBox)
            ballsToCheck.forEach { otherBall -> ball.collide(otherBall) }
        }

        balls.forEach { it.render(this) }
        quadTree.render(this)

        mouseInteraction()
    }

    private fun mouseInteraction() {
        val mouseHitBox = HitBox(
            mouseX - MOUSE_BOUNDARY_RADIUS,
            mouseY - MOUSE_BOUNDARY_RADIUS,
            MOUSE_BOUNDARY_RADIUS * 2,
            MOUSE_BOUNDARY_RADIUS * 2
        )
        quadTree.queryRange(mouseHitBox).forEach {
            noFill()
            if (it.containsPoint(PVector(mouseX.toFloat(), mouseY.toFloat()))) {
                stroke(0f, 255f, 0f)
            } else stroke(255f)
            strokeWeight(1f)
            ellipse(it.position.x, it.position.y, it.diameter, it.diameter)
        }
        mouseHitBox.render(this)
    }
}

fun main() {
    PApplet.main(Main::class.java.name)
}