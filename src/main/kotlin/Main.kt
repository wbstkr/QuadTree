package io.github.wbstkr

import processing.core.PApplet
import processing.core.PVector

private const val NUM_OF_BALLS = 4000
private const val MOUSE_BOUNDARY_RADIUS = 50f

class Main : PApplet() {
    var useQuadTree = true

    val balls = mutableListOf<Ball>()
    lateinit var quadTree: QuadTree

    override fun settings() {
        size(600, 600)
    }

    override fun setup() {
        repeat(NUM_OF_BALLS) {
            val randomX = random(width.toFloat())
            val randomY = random(height.toFloat())
            val randomD = random(5f, 10f)
            val newBall = Ball(randomX, randomY, randomD)
            balls.add(newBall)
        }
    }

    override fun draw() {
        background(0)

        balls.forEach { it.update(this) }

        if (useQuadTree) runQuadTree()
        else runWithoutQuadTree()

        balls.forEach { it.render(this) }
        mouseInteraction()

        fill(255)
        textSize(16f)
        val modeText = if (useQuadTree) "Mode: QuadTree O(n log n)" else "Mode: Naive O(n^2)"
        text("$modeText | FPS: ${frameRate.toInt()}", 10f, 20f)
    }

    private fun runQuadTree() {
        quadTree = QuadTree(0f, 0f, width.toFloat(), height.toFloat())
        balls.forEach { quadTree.insert(it) }

        balls.forEach { ball ->
            val ballsToCheck = quadTree.queryRange(ball.hitBox)
            ballsToCheck.forEach { otherBall ->
                if (ball != otherBall) ball.collide(otherBall)
            }
        }
        quadTree.render(this)
    }

    private fun runWithoutQuadTree() {
        for (i in 0 until balls.size) {
            for (j in i + 1 until balls.size) {
                balls[i].collide(balls[j])
            }
        }
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

    override fun keyPressed() {
        if (key == ' ') useQuadTree = !useQuadTree
    }
}

fun main() {
    PApplet.main(Main::class.java.name)
}