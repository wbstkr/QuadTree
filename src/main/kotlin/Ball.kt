package io.github.wbstkr

import processing.core.PApplet
import processing.core.PVector

class Ball(val position: PVector, val radius: Float) {
    constructor(x: Float, y: Float, radius: Float) : this(PVector(x, y), radius)

    fun containsPoint(point: PVector): Boolean {
        return position.dist(point) < radius
    }

    fun containsPoint(x: Float, y: Float) = this.containsPoint(PVector(x, y))

    fun intersectsBall(otherBall: Ball): Boolean {
        return position.dist(otherBall.position) < radius + otherBall.radius
    }

    fun render(canvas: PApplet) {
        with(canvas) {
            fill(255, 50f)
            noStroke()
            ellipse(position.x, position.y, radius, radius)
        }
    }
}