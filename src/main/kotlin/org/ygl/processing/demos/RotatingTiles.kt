package org.ygl.processing.demos

import org.ygl.pkx.launchApplet
import org.ygl.pkx.raidans
import org.ygl.pkx.rect
import org.ygl.pkx.translate
import org.ygl.pkx.withTransform
import processing.core.PApplet
import processing.core.PConstants

private const val WIDTH = 800
private const val HEIGHT = 800
private const val TILE_SIZE = 16
private const val WIDTH_IN_TILES = WIDTH / TILE_SIZE
private const val ROTATION_DELTA = 1

class RotatingTiles: PApplet() {

    private val bgColor = color(32, 64, 128, 255)
    private val tileColor = color(64, 128, 255, 255)
    private val offsets = IntArray(WIDTH / TILE_SIZE * HEIGHT / TILE_SIZE) {
        it % WIDTH_IN_TILES + it / WIDTH_IN_TILES
    }

    override fun settings() {
        size(WIDTH, HEIGHT, PConstants.P2D)
    }

    override fun setup() {
        frameRate(48f)
        background(bgColor)
        fill(tileColor)
        noStroke()
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
                rect(-TILE_SIZE/2, -TILE_SIZE/2, TILE_SIZE, TILE_SIZE)
            }
        }

    }
}

fun main() {
    launchApplet<RotatingTiles>()
}
