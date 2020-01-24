package org.ygl.processing.demos

import org.ygl.pkx.launchApplet
import org.ygl.pkx.raidans
import org.ygl.pkx.ellipse
import org.ygl.pkx.translate
import org.ygl.pkx.withTransform
import processing.core.PApplet
import processing.core.PConstants

private const val WIDTH = 800
private const val HEIGHT = 800
private const val TILE_SIZE = 16
private const val DOT_SIZE = 12
private const val WIDTH_IN_TILES = WIDTH / TILE_SIZE
private const val ROTATION_DELTA = 3

class RotatingDots: PApplet() {

    private val bgColor = color(64, 128, 255, 255)
    private val tileColor = color(128, 64, 32, 255)
    private val offsets = IntArray(WIDTH_IN_TILES * HEIGHT / TILE_SIZE) {
        it
    }.also { println(it) }

    override fun settings() {
        size(WIDTH, HEIGHT)
    }

    override fun setup() {
        frameRate(48f)
        background(bgColor)
        fill(tileColor)
        noStroke()
        ellipseMode(PConstants.CORNER)
    }

    override fun draw() {
        background(bgColor)
        for (i in offsets.indices) {
            offsets[i] = offsets[i] + ROTATION_DELTA
            val x = (i % WIDTH_IN_TILES) * TILE_SIZE
            val y = (i / WIDTH_IN_TILES) * TILE_SIZE

            withTransform {
                translate(x + TILE_SIZE/2, y + TILE_SIZE/2)
                rotate(raidans(offsets[i]))
                ellipse(-TILE_SIZE/2, -TILE_SIZE/2, DOT_SIZE, DOT_SIZE)
            }
        }

    }
}

fun main() {
    launchApplet<RotatingDots>()
}
