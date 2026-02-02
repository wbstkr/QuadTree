package io.github.wbstkr

import processing.core.PApplet
import processing.core.PVector

class HitBox(val upperLeft: PVector, val dimensions: PVector) {
    val w get() = dimensions.x
    val h get() = dimensions.y
    val l get() = upperLeft.x
    val t get() = upperLeft.y
    val r get() = l + w
    val b get() = t + h

    constructor(x: Float, y: Float, w: Float, h: Float) : this(PVector(x, y), PVector(w, h))

    fun containsPoint(point: PVector): Boolean {
        return point.x > l && point.y > t && point.x < r && point.y < b
    }

    fun intersectsHitBox(otherHitBox: HitBox): Boolean {
        return otherHitBox.l < r && otherHitBox.t < b && otherHitBox.r > l && otherHitBox.b > t
    }

    fun render(canvas: PApplet) {
        with(canvas) {
            noFill()
            stroke(255f, 0f, 0f)
            strokeWeight(1f)
            rect(l, t, w, h)
        }
    }
}