
class Square(var x: Int, var y:Int, var height:Int): Movable, Figure(0), Transforming {

    override fun toString(): String {
        return "Square: ($x, $y) size $height"
    }

    override fun move(dx: Int, dy: Int) {
        x += dx
        y += dy
    }

    override fun area(): Float {
        return (height * height).toFloat()
    }

    override fun resize(zoom: Int) {
        height *= zoom
    }

    override fun rotate(direction: RotateDirection, centerX: Int, centerY: Int) {
        val dist = (height/ 2.0)

        var cX = x + dist
        var cY = y - dist

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

        x = (cX - dist).toInt()
        y = (cY + dist).toInt()
    }
}