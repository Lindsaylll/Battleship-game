package cs349.a3battleship

import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.Game
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment

class FleetView(
    private val additx: Double,
    val game: Game,
    private val model: Model


//    private val model: cs349.a3battleship.Model

): Pane() , IView  {


    override fun updateView(toNotify:Boolean) {
        // for debug
//        println("${game.gameState.toString()}")
        if(game.gameState.toString() == "WonAI"){
            l.text = "You were defeated!"
            l.translateX = 10.0 + additx
        } else if(game.gameState.toString() == "WonHuman"){
            l.text = "You won!"
            l.translateX = 45.0 + additx
            //for debug
//             println("sunk ships: ${game.listofSunkShip}")
            var listofSunkShip = game.findSunkShip()
            // check if this ship is destroyed, if it is it remains on the board; otherwise, it is placed back
            if(!listofSunkShip.contains("Destroyer")){
                rec1.translateX = 0.0
                rec1.translateY = 0.0
            }
            // check if this ship is destroyed, if it is it remains on the board; otherwise, it is placed back
            if(!listofSunkShip.contains("Cruiser")){
                rec2.translateX = 0.0
                rec2.translateY = 0.0
            }
            // check if this ship is destroyed, if it is it remains on the board; otherwise, it is placed back
            if(!listofSunkShip.contains("Submarine")){
                rec3.translateX = 0.0
                rec3.translateY = 0.0
            }
            // check if this ship is destroyed, if it is it remains on the board; otherwise, it is placed back
            if(!listofSunkShip.contains("Battleship")){
                rec4.translateX = 0.0
                rec4.translateY = 0.0
            }
            // check if this ship is destroyed, if it is it remains on the board; otherwise, it is placed back
            if(!listofSunkShip.contains("Carrier")){
                rec5.translateX = 0.0
                rec5.translateY = 0.0
            }
        }
    }

    // helper to draw the fleets with required length
    private fun drawrec(x:Double, y:Double, wid:Double, hei:Double, tp:Int): Rectangle {
        val rec = Rectangle(wid,hei)
        if(tp == 1){
            rec.fill  = ImagePattern(Image("Destroyer.png"))
        }
        if(tp == 2){
            rec.fill  = ImagePattern(Image("Cruiser.png"))
        }
        if(tp == 3){
            rec.fill  = ImagePattern(Image("Submarine.png"))
        }
        if(tp == 4){
            rec.fill  = ImagePattern(Image("Battleship.png"))
        }
        if(tp == 5){
            rec.fill  = ImagePattern(Image("Carrier.png"))
        }
        rec.opacity = 0.5
        rec.x = x
        rec.y = y
        return rec

    }
    // draw all the fleets
    private var rec1 = drawrec(5.0+ additx, 25.0, 26.0,55.0,1)
    private var rec2 = drawrec(36.0+ additx, 25.0, 26.0,85.0,2)
    private var rec3 = drawrec(67.0+ additx, 25.0, 26.0,85.0,3)
    private var rec4 = drawrec(98.0+ additx, 25.0, 26.0,115.0,4)
    private var rec5 = drawrec(129.0+ additx, 25.0, 26.0,145.0,5)
    private var l = Label("My Fleet")
    init {
        this.height = 250.0
        // required style and size
        val header = Font.font("Arial", FontWeight.BOLD, 16.0)
        l.font = header
        l.textAlignment = TextAlignment.CENTER
        // arrange layout
        l.prefHeight = 25.0
        l.translateX = 50.0 + additx
        // add to pane
        this.children.addAll(l,rec1,rec2,rec3,rec4,rec5)
        // add to model for later updateView
        model.addView(this)
    }

}