package io.github.wbstkr

import processing.core.PApplet

class QuadTree(val boundary: HitBox, val capacity: Int = 4) {
    val balls = mutableSetOf<Ball>()
    var northWest: QuadTree? = null
    var northEast: QuadTree? = null
    var southWest: QuadTree? = null
    var southEast: QuadTree? = null
    val children get() = listOfNotNull(northWest, northEast, southWest, southEast)
    var subdivided = false

    constructor(x: Float, y: Float, w: Float, h: Float, capacity: Int = 4) : this(HitBox(x, y, w, h), capacity)

    fun insert(ball: Ball): Boolean {
        if (!boundary.containsPoint(ball.position)) return false
        if (balls.size < capacity) {
            balls.add(ball)
            return true
        }
        if (!subdivided) subdivide()
        children.forEach { if (it.insert(ball)) return true }
        return false
    }

    fun subdivide() {
        if (subdivided) return
        val newWidth = boundary.width / 2f
        val newHeight = boundary.height / 2f
        val centerX = boundary.left + newWidth
        val centerY = boundary.top + newHeight
        northWest = QuadTree(boundary.left, boundary.top, newWidth, newHeight)
        northEast = QuadTree(centerX, boundary.top, newWidth, newHeight)
        southWest = QuadTree(boundary.left, centerY, newWidth, newHeight)
        southEast = QuadTree(centerX, centerY, newWidth, newHeight)
        subdivided = true
    }

    fun queryRange(range: HitBox): Set<Ball> {
        val ballsInRange = mutableSetOf<Ball>()
        if (!boundary.intersectsHitBox(range)) return ballsInRange
        balls.forEach {
            if (range.containsPoint(it.position)) ballsInRange.add(it)
        }
        if (!subdivided) return ballsInRange
        children.forEach { ballsInRange.addAll(it.queryRange(range)) }
        return ballsInRange
    }

    fun render(canvas: PApplet) {
        if (subdivided) children.forEach { it.render(canvas) }
        else boundary.render(canvas)
    }
}