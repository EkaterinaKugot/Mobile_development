fun main() {
    println("Move:")

    val movable: Movable = Rect(0,0,1,1)
    println("The original figures: $movable")
    movable.move(1,1)
    println(movable)
    println()

    val f1: Figure = Rect(-7,3,4,2)
    val f2: Figure = Circle(2, -2, 2)
    val f3: Figure = Square(3, 6, 3)

    println("The original figures:")

    println(f1)
    println(f2)
    println(f3)
    println()

    println("Area:")

    println("Rect: ${f1.area()}")
    println("Circle: ${f2.area()}")
    println("Square: ${f3.area()}")
    println()

    val t1: Transforming = Rect(-7,3,4,2)
    val t2: Transforming = Circle(2, -2, 2)
    val t3: Transforming = Square(3, 6, 3)

    println("Resize *3:")

    t1.resize(3)
    t2.resize(3)
    t3.resize(3)

    println(t1)
    println(t2)
    println(t3)
    println()

    println("The figures returned to their original state.\nThe rotation of the rectangle, circle and square is relative to the center.")
    println()

    val tf1: Transforming = Rect(-7,3,4,2)
    val tf2: Transforming = Circle(2, -2, 2)
    val tf3: Transforming = Square(3, 6, 3)

    println("Rotate Clockwise:")

    tf1.rotate(RotateDirection.Clockwise, -3, -2)
    tf2.rotate(RotateDirection.Clockwise, -3, -2)
    tf3.rotate(RotateDirection.Clockwise,  -3, -2)

    println(tf1)
    println(tf2)
    println(tf3)
    println()

    println("Returning the figures to their original coordinates.")
    println("Rotate CounterClockwise:")

    tf1.rotate(RotateDirection.CounterClockwise, -3, -2)
    tf2.rotate(RotateDirection.CounterClockwise, -3, -2)
    tf3.rotate(RotateDirection.CounterClockwise, -3, -2)

    println(tf1)
    println(tf2)
    println(tf3)
}