package io.github.wbstkr

class QuadTree(val boundary: HitBox, val capacity: Int = 4) {
    val balls = mutableSetOf<Ball>()
    var northWest: QuadTree? = null
    var northEast: QuadTree? = null
    var southWest: QuadTree? = null
    var southEast: QuadTree? = null
    var subdivided = false

    constructor(x: Float, y: Float, w: Float, h: Float, capacity: Int = 4) : this(HitBox(x, y, w, h), capacity)
    constructor(x: Float, y: Float, w: Float, h: Float) : this(HitBox(x, y, w, h))

    fun insert(ball: Ball): Boolean {
        if (!boundary.containsPoint(ball.position)) return false
        if (balls.size < capacity) {
            balls.add(ball)
            return true
        }
        if (!subdivided) subdivide()
        if (northWest?.insert(ball) == true) return true
        if (northEast?.insert(ball) == true) return true
        if (southWest?.insert(ball) == true) return true
        if (southEast?.insert(ball) == true) return true
        return false
    }

    fun subdivide() {
        if (subdivided) return
        val newWidth = boundary.width / 2f
        val newHeight = boundary.height / 2f
        northWest = QuadTree(boundary.upperLeft.x, boundary.upperLeft.y, newWidth, newHeight)
        northEast = QuadTree(boundary.upperLeft.x + newWidth, boundary.upperLeft.y, newWidth, newHeight)
        southWest = QuadTree(boundary.upperLeft.x, boundary.upperLeft.y + newHeight, newWidth, newHeight)
        southEast = QuadTree(boundary.upperLeft.x + newWidth, boundary.upperLeft.y + newHeight, newWidth, newHeight)
        subdivided = true
    }

    fun queryRange(range: HitBox): Set<Ball> {
        val ballsInRange = mutableSetOf<Ball>()
        if (!boundary.intersectsHitBox(range)) return ballsInRange
        balls.forEach {
            if (range.containsPoint(it.position)) ballsInRange.add(it)
        }
        if (!subdivided) return ballsInRange
        northWest?.queryRange(range)?.let { ballsInRange.addAll(it) }
        northEast?.queryRange(range)?.let { ballsInRange.addAll(it) }
        southWest?.queryRange(range)?.let { ballsInRange.addAll(it) }
        southEast?.queryRange(range)?.let { ballsInRange.addAll(it) }
        return ballsInRange
    }
}