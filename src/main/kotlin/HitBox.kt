package io.github.wbstkr

import processing.core.PApplet
import processing.core.PVector

class HitBox(val upperLeft: PVector, val dimensions: PVector) {
    val width get() = dimensions.x
    val height get() = dimensions.y
    val left get() = upperLeft.x
    val top get() = upperLeft.y
    val right get() = left + width
    val bottom get() = top + height

    constructor(x: Float, y: Float, w: Float, h: Float) : this(PVector(x, y), PVector(w, h))

    fun containsPoint(point: PVector): Boolean {
        return point.x > left && point.y > top && point.x < right && point.y < bottom
    }

    fun containsPoint(x: Float, y: Float) = this.containsPoint(PVector(x, y))

    fun intersectsHitBox(otherHitBox: HitBox): Boolean {
        return otherHitBox.left < right && otherHitBox.top < bottom && otherHitBox.right > left && otherHitBox.bottom > top
    }

    fun render(canvas: PApplet) {
        canvas.noFill()
        canvas.stroke(255f, 0f, 0f)
        canvas.strokeWeight(2f)
        canvas.rect(left, top, width, height)
    }
}