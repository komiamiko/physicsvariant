import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.layout.Pane
import javafx.stage.Stage
import model.KeyState
import java.util.concurrent.SynchronousQueue
import kotlin.concurrent.thread

class Main : Application() {
    var hb1 = Pane()
    var scene = Scene(hb1, 1600.0, 900.0)

    override fun start(stage: Stage) {
        val keyState = KeyState(left = false, right = false, up = false, down = false, jump = false)
        var gamePanel = GamePanel()
        hb1.children.add(gamePanel)
        // set up UI
        stage.minWidth = 400.0
        stage.minHeight = 225.0
        stage.title = "Physics Playground prototype"
        stage.scene = scene
        stage.show()
        // listeners
        val keyEvents = SynchronousQueue<KeyEvent>()
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
                    }
                }
            }
        };
    }
}