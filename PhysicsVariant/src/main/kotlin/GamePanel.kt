import game.World
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import kotlin.math.hypot

class GamePanel: Canvas() {

    var world: World? = null

    var cameraX = 0.0
    var cameraY = 0.0

    fun updateData(world: World) {
        this.world = world
    }

    fun tick() {
        val world = world
        if(world != null) {
            cameraX += (world.player.x - cameraX) * 0.02
            cameraY += (world.player.y - cameraY) * 0.02
        }
    }

    fun paint() {
        // get drawing context
        val gc = graphicsContext2D
        // figure out the drawing region
        val cwidth = width
        val cheight = height
        // clear with background
        gc.fill = Color.WHITE
        gc.fillRect(0.0, 0.0, cwidth, cheight)
        val world = world ?: return
        // figure out camera transform
        val xmul = hypot(cwidth, cheight) / 40
        val ymul = -xmul
        val xadd = cwidth * 0.5 - cameraX * xmul
        val yadd = cheight * 0.5 - cameraY * ymul
        // show surfaces
        gc.lineWidth = 3.0
        for(surface in world.level.surfaces) {
            gc.stroke = Color.BLACK
            gc.strokeLine(surface.x1 * xmul + xadd, surface.y1 * ymul + yadd, surface.x2 * xmul + xadd, surface.y2 * ymul + yadd)
        }
        // show player
        gc.fill = Color.BLUE
        gc.fillRect(
            (world.player.x - world.player.radius) * xmul + xadd,
            (world.player.y + world.player.radius) * ymul + yadd,
            world.player.radius * 2 * xmul,
            -world.player.radius * 2 * ymul
        )
        gc.strokeRect(
            (world.player.x - world.player.radius) * xmul + xadd,
            (world.player.y + world.player.radius) * ymul + yadd,
            world.player.radius * 2 * xmul,
            -world.player.radius * 2 * ymul
        )
    }

}