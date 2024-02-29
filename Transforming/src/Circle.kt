class Circle(var x: Int, var y: Int, var r: Int) : Figure(0), Transforming {

    override fun toString(): String {
        return "Circle: ($x, $y) radius $r"
    }

    override fun area(): Float {
        return (kotlin.math.PI * r * r).toFloat()
    }

    override fun resize(zoom: Int) {
        r *= zoom
    }

    override fun rotate(direction: RotateDirection, centerX: Int, centerY: Int) {
        val x1 = x - centerX
        val y1 = y - centerY

        x = centerX
        y = centerY

        if (direction == RotateDirection.Clockwise) {
            x += y1
            y -= x1
        } else if (direction == RotateDirection.CounterClockwise){
            x -= y1
            y += x1
        }
    }
}