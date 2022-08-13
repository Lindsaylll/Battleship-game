package cs349.a3battleship

import cs349.a3battleship.model.Game
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.Pane
import javafx.stage.Stage
import kotlin.system.exitProcess

class Battleship : Application() {

    override fun start(stage: Stage) {
        // the model lives in Main
        val model = Model()

        //window name
        stage.title = "A3 Battleship (x645liu)"
        // the main interface layout
        val root = Pane()
        root.isFocusTraversable = true
        // to control translateX when place item on root
        val addit = 360.0 // all x + addit

        //game variable to control the process of the game loop
        var game = Game(10, true)


        // two button to control start/end process
        val bu1 = Button("Start Game")
        bu1.prefWidth = 160.0
        // required height of button
        bu1.prefHeight = 20.0
        bu1.isDisable = true
        bu1.translateX = addit
        bu1.translateY = 300.0
        val bu2 = Button("Exit Game")
        bu2.prefWidth = 160.0
        // required height of button
        bu2.prefHeight = 20.0
        bu2.translateX = addit
        bu2.translateY = 327.0
        bu2.setOnAction { exitProcess(0) } // to end process

        // MovableManager receive the game and also bu1 to indicate when the setup is finished and the bu1 is enabled
        val mover = MovableManager(root, game, bu1)  // might be only the fleets change later

        // right board for AI
        val lft =  FmView("Opponent", game, model)
        root.children.add(lft)

        // left board for Human to place ships
        val rig = FmView("Mine", game, model)
        root.children.add(rig)



        // middle is the where the fleets' home
        val md = FleetView(addit, game, model)

//        val header = Font.font("Arial", FontWeight.BOLD, 16.0)
//        var l = Label("My Fleet")
//        l.font = header
//        l.textAlignment = TextAlignment.CENTER
//        l.prefHeight = 25.0
//        l.translateX = 50.0 + addit

//        var rec1 = drawrec(5.0+ addit, 25.0, 26.0,55.0)
//        var rec2 = drawrec(36.0+ addit, 25.0, 26.0,85.0)
//        var rec3 = drawrec(67.0+ addit, 25.0, 26.0,85.0)
//        var rec4 = drawrec(98.0+ addit, 25.0, 26.0,115.0)
//        var rec5 = drawrec(129.0+ addit, 25.0, 26.0,145.0)
//        mover.makeMovable(rec1,3,0)

        // make every fleets in FleetView movable
        mover.makeMovable(md.children[1],3,0)
        mover.makeMovable(md.children[2],2,1)
        mover.makeMovable(md.children[3],4,2)
        mover.makeMovable(md.children[4],0,3)
        mover.makeMovable(md.children[5], 1,4)

        // add them to the main interface layout
        root.children.addAll(md,bu1,bu2/*, rec1,*/ /*rec2,rec3,rec4,rec5*/)


        var computer = AI(game)

        // after this the game process will be at game.init state
        game.startGame()

        // layout of the scene
        val scene = Scene(root, 875.0, 375.0)
        stage.scene = scene
        stage.isResizable = false
        stage.show()
    }
}