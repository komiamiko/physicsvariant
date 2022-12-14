import game.Hitbox
import game.Level
import game.World
import integrators.Euler
import integrators.RK4
import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.layout.Pane
import javafx.stage.Stage
import model.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.SynchronousQueue
import kotlin.concurrent.thread

class Main : Application() {
    var hb1 = Pane()
    var scene = Scene(hb1, 1600.0, 900.0)

    override fun start(stage: Stage) {
        // setup test level
        val keyState = KeyState(left = false, right = false, up = false, down = false, jump = false)
        val testGravity = Gravity(15.0, 1.0, keyState, GravityMode.LESS_IF_HOLDING_UP)
        val testDrag = Drag(0.3, DragMode.QUADRATIC_DRAG)
        val testJump = Jump(15.0, 35.0, keyState, JumpMode.STRONGER_IF_HOLDING_UP)
        val testWalk = Walk(6.0)
        val testFriction = Friction(3.0)
        val testForces = listOf(testGravity, testDrag)
        val testSurfaces = listOf(
            Surface(-100.0, -2.0, 10.0, -2.0, true),
            Surface(10.0, -2.0, 10.0, 1.0),
            Surface(10.0, 1.0, 15.0, 1.0, true),
            Surface(15.0, 1.0, 20.0, 4.0),
            Surface(20.0, 4.0, 25.0, 4.0, true),
            Surface(25.0, 4.0, 35.0, -2.0, true),
            Surface(35.0, -2.0, 100.0, -2.0, true)
        )
        val testLevel = Level(Box(1.0, 1.0, 0.0, 0.0, 0.0, 0.0, testForces, testSurfaces), Hitbox(50.0, -2.0, 54.0, 2.0), testSurfaces)
        val testIntegrator = RK4()
        var world = World(keyState, testLevel, Box(1.0, 1.0, 0.0, 0.0, 0.0, 0.0, testForces, testSurfaces), testIntegrator, testGravity, testDrag, testJump, testWalk, testFriction)
        var gamePanel = GamePanel()
        hb1.children.add(gamePanel)
        // set up UI
        stage.minWidth = 400.0
        stage.minHeight = 225.0
        stage.title = "Physics Playground prototype"
        stage.scene = scene
        stage.show()
        // listeners
        val keyEvents = ConcurrentLinkedQueue<KeyEvent>()
        gamePanel.onKeyPressed = EventHandler {
            keyEvents.add(KeyEvent(it.code, true))
        }
        gamePanel.onKeyReleased = EventHandler {
            keyEvents.add(KeyEvent(it.code, false))
        }
        gamePanel.requestFocus()
        // begin ticking
        thread{
            while(!stage.isShowing) {
                Thread.sleep(33)
            }
            while(stage.isShowing) {
                Thread.sleep(33)
                gamePanel.width = stage.width
                gamePanel.height = stage.height
                while(keyEvents.isNotEmpty()) {
                    val ke = keyEvents.remove()
                    if(ke.code == KeyCode.A) {
                        keyState.left = ke.isdown
                    } else if(ke.code == KeyCode.S) {
                        keyState.down = ke.isdown
                    } else if(ke.code == KeyCode.D) {
                        keyState.right = ke.isdown
                    } else if(ke.code == KeyCode.W) {
                        keyState.up = ke.isdown
                    } else if(ke.code == KeyCode.J) {
                        keyState.jump = ke.isdown
                    } else if(ke.code == KeyCode.K && ke.isdown) {
                        // change drag mode
                        testDrag.mode = if(testDrag.mode == DragMode.QUADRATIC_DRAG) DragMode.LINEAR_DRAG else DragMode.QUADRATIC_DRAG
                    }
                }
                world = world.tick(0.033)
                gamePanel.updateData(world)
                gamePanel.tick()
                gamePanel.paint()
            }
        };
    }
}