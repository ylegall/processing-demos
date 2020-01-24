package org.ygl.processing.demos

import org.ygl.pkx.circle
import org.ygl.pkx.launchApplet
import org.ygl.pkx.magnitudeSquared
import org.ygl.pkx.minus
import org.ygl.pkx.plusAssign
import org.ygl.pkx.pvector
import org.ygl.pkx.times
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PGraphics
import processing.core.PVector


private const val WIDTH = 800
private const val HEIGHT = 800
private const val SPRING = 0.1f
private const val DAMPING = 0.91f
private const val FORCE_FACTOR = 57f
private const val PARTICLE_SIZE = 16
private const val WIDTH_IN_PARTICLES = WIDTH / PARTICLE_SIZE


class SpringParticles: PApplet() {

    private class Particle(
            x: Number,
            y: Number
    ) {
        var position = PVector(x.toFloat(), y.toFloat())
        var anchor = position.copy()
        var velocity = pvector(0, 0)
        var blue = 0f

        fun update(mousePosition: PVector?) {
            val mouseForce = if (mousePosition != null) {
                var force = mousePosition - position
                val distanceSquared = force.magnitudeSquared()
//                blue = (distanceSquared / 789f).coerceIn(0f, 255f)
                if (distanceSquared > 3000f || distanceSquared < 0.001f) {
                    pvector(0, 0)
                } else {
                    force /= distanceSquared
                    force *= FORCE_FACTOR
                    force
                }
            } else {
                pvector(0, 0)
            }
            var anchorForce = (anchor - position)
            anchorForce *= SPRING
            anchorForce -= mouseForce
            velocity += anchorForce
            velocity *= DAMPING
            position += velocity
            position.x.coerceIn(0f, WIDTH.toFloat())
            position.y.coerceIn(0f, HEIGHT.toFloat())
        }

        fun draw(g: PGraphics) {
            g.fill(g.color(255 - blue, 16f, blue))
            g.circle(position.x, position.y, PARTICLE_SIZE)
        }
    }

    private val bgColor = color(140, 150, 160)
    private var mousePosition: PVector? = null
    private val particles = Array(WIDTH_IN_PARTICLES * HEIGHT / PARTICLE_SIZE) {
        Particle((it % WIDTH_IN_PARTICLES) * PARTICLE_SIZE, (it / WIDTH_IN_PARTICLES) * PARTICLE_SIZE)
    }

    override fun settings() {
        size(WIDTH, HEIGHT)
    }

    override fun setup() {
        frameRate(48f)
        ellipseMode(PConstants.CORNER)
    }

    override fun mouseMoved() {
        mousePosition = pvector(mouseX, mouseY)
    }

    override fun draw() {
        background(bgColor)
        particles.forEach {
            it.update(mousePosition)
            it.draw(g)
        }
    }
}

fun main() {
    launchApplet<SpringParticles>()
}