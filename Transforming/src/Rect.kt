class Rect(var x: Int, var y: Int, var width: Int, var height: Int) : Movable, Figure(0), Transforming{
    var color: Int = -1
    lateinit var name: String
    constructor(rect: Rect) : this(rect.x, rect.y, rect.width, rect.height)

    override fun toString(): String {
        return "Rect: ($x, $y), size ($width, $height)"
    }

    override fun move(dx: Int, dy: Int) {
        x += dx
        y += dy
    }

    override fun area(): Float {
        return (width*height).toFloat()
    }

    override fun resize(zoom: Int) {
        width *= zoom
        height += zoom
    }

    override fun rotate(direction: RotateDirection, centerX: Int, centerY: Int) {
        val distW = (width/ 2.0)
        val distH = (height/ 2.0)

        var cX = x + distW
        var cY = y - distH

        val x1 = cX - centerX
        val y1 = cY - centerY

        cX = centerX.toDouble()
        cY = centerY.toDouble()

        if (direction == RotateDirection.Clockwise) {
            cX += y1
            cY -= x1
        } else if (direction == RotateDirection.CounterClockwise){
            cX -= y1
            cY += x1
        }

        x = (cX - distH).toInt()
        y = (cY + distW).toInt()
        var w = width
        width = height
        height = w
    }
}