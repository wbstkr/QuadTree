package io.github.wbstkr

import processing.core.PApplet
import processing.core.PVector

class Ball(
    val position: PVector,
    val diameter: Float,
    var velocity: PVector = PVector.random2D().div(2f)
) {
    val radius get() = diameter / 2f
    val hitBox = HitBox(0f, 0f, diameter, diameter)

    constructor(x: Float, y: Float, d: Float) : this(PVector(x, y), d)

    fun containsPoint(point: PVector): Boolean {
        return position.dist(point) < radius
    }

    fun update(canvas: PApplet) {
        position.add(velocity)
        if (position.x < 0 - radius) position.x += canvas.width + diameter
        if (position.x > canvas.width + radius) position.x -= canvas.width + diameter
        if (position.y < 0 - radius) position.y += canvas.height + diameter
        if (position.y > canvas.height + radius) position.y -= canvas.height + diameter
        hitBox.upperLeft.x = position.x - radius
        hitBox.upperLeft.y = position.y - radius
    }

    fun collide(otherBall: Ball) {
        val delta = PVector.sub(otherBall.position, this.position)
        val dist = delta.mag()
        val minDist = this.radius + otherBall.radius

        if (dist < minDist && dist > 0f) {
            val overlap = minDist - dist
            val push = delta.copy().normalize().mult(overlap / 2f)
            this.position.sub(push)
            otherBall.position.add(push)

            val normal = delta.copy().normalize()
            val relativeVelocity = PVector.sub(this.velocity, otherBall.velocity)
            val speed = relativeVelocity.dot(normal)

            if (speed > 0f) {
                val impulse = normal.mult(speed)
                this.velocity.sub(impulse)
                otherBall.velocity.add(impulse)
            }
        }
    }

    fun render(canvas: PApplet) {
        canvas.fill(255f, 50f)
        canvas.noStroke()
        canvas.ellipse(position.x, position.y, diameter, diameter)
    }
}