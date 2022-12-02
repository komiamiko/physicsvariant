import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage
import kotlin.concurrent.thread

class Main : Application() {
    var hb1 = Pane()
    var scene = Scene(hb1, 1600.0, 900.0)

    override fun start(stage: Stage) {
        // set up UI
        stage.minWidth = 400.0
        stage.minHeight = 225.0
        stage.title = "Physics Playground prototype"
        stage.scene = scene
        stage.show()
        // TODO set up listeners
        // begin ticking
        thread{
            while(!stage.isShowing) {
                Thread.sleep(33)
            }
            while(stage.isShowing) {
                Thread.sleep(33)
                // TODO tick
            }
        };
    }
}