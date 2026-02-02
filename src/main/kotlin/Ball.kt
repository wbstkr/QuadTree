package io.github.wbstkr

import processing.core.PApplet
import processing.core.PVector

class Ball(
    val position: PVector, val diameter: Float, var velocity: PVector = PVector.random2D()
) {
    val radius get() = diameter / 2f

    constructor(x: Float, y: Float, d: Float) : this(PVector(x, y), d)

    fun containsPoint(point: PVector): Boolean {
        return position.dist(point) < radius
    }

    fun intersectsBall(otherBall: Ball): Boolean {
        return position.dist(otherBall.position) < radius + otherBall.radius
    }

    fun update(canvas: PApplet) {
        position.add(velocity)
        if (position.x < 0 - radius) position.x += canvas.width + diameter
        if (position.x > canvas.width + radius) position.x -= canvas.width + diameter
        if (position.y < 0 - radius) position.y += canvas.height + diameter
        if (position.y > canvas.height + radius) position.y -= canvas.height + diameter
    }

    fun collide(otherBall: Ball) {

    }

    fun render(canvas: PApplet) {
        canvas.fill(255f, 50f)
        canvas.noStroke()
        canvas.ellipse(position.x, position.y, diameter, diameter)
    }
}