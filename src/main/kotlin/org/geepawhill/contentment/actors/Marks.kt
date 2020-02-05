package org.geepawhill.contentment.actors

import javafx.scene.Group
import org.geepawhill.contentment.actor.Actor
import org.geepawhill.contentment.actor.ScriptWorld
import org.geepawhill.contentment.format.Format
import org.geepawhill.contentment.fragments.Entrance
import org.geepawhill.contentment.fragments.Mark
import org.geepawhill.contentment.geometry.Bezier
import org.geepawhill.contentment.geometry.BezierSource
import org.geepawhill.contentment.geometry.Point
import org.geepawhill.contentment.geometry.PointPair
import org.geepawhill.contentment.position.Position
import org.geepawhill.contentment.step.Single
import org.geepawhill.contentment.timing.Timing

class Marks(private val world: ScriptWorld, vararg beziers: Bezier) : Actor {
    private val marks: MutableList<Mark>
    private val entrance: Entrance
    private val group: Group = Group()

    init {
        this.entrance = Entrance(group)
        this.marks = mutableListOf<Mark>()
        for (bezier in beziers) {
            marks.add(Mark(group, BezierSource.value(bezier)))
        }
    }

    override fun format(format: Format) {
        for (mark in marks) {
            mark.format(format)
        }
    }

    override fun draw(ms: Double): Marks {
        for (mark in marks) {
            world.add(Single(Timing.ms(ms / marks.size), mark))
        }
        return this
    }

    override fun at(position: Position) {}

    fun entrance(): Entrance {
        return entrance
    }

    override fun group(): Group {
        return group
    }

    companion object {

        fun makeBox(world: ScriptWorld, points: PointPair): Marks {
            return Marks(world, jiggle(world, points.northLine()),
                    jiggle(world, points.eastLine()), jiggle(world, points.southLine()), jiggle(world, points.westLine()))
        }

        fun makeLine(world: ScriptWorld, points: PointPair): Marks {
            return Marks(world, jiggle(world, points))
        }

        private fun jiggle(world: ScriptWorld, points: PointPair): Bezier {
            val variance = points.distance() * .05
            return Bezier(points.from, world.jiggle(points.along(world.nextDouble()), 1.0, variance),
                    world.jiggle(points.along(world.nextDouble()), 1.0, variance), points.to)
        }

        fun makeArc(world: ScriptWorld, from: Point, to: Point, height: Double): Marks {

            val line = PointPair(from, to)
            val xUnitVector = (from.x - to.x) / line.distance()
            val yUnitVector = (from.y - to.y) / line.distance()
            val firstq = line.along(.25)
            val c1 = Point(firstq.x + (height * yUnitVector), firstq.y + (height * xUnitVector))

            val thirdq = line.along(.75)
            val c2 = Point(thirdq.x + (height * yUnitVector), thirdq.y + (height * xUnitVector))
            return Marks(
                    world,
                    Bezier(from, c1, c2, to)
            )
        }
    }

}