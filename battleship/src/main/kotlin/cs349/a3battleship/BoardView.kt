package cs349.a3battleship

import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.CellState
import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Player
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.paint.Color.color
import javafx.scene.shape.Rectangle


// this is right(AI's) board where human perform attack
class BoardView(
    private val additx: Double,
    val game: Game,
    private val model: Model


//    private val model: cs349.a3battleship.Model

): Pane(){

    // to store all the fleets' image we made for later modification
    private var recList = mutableListOf<Rectangle>()

    // find all cells that have state.ShipSunk; change these cells' color to DARKGRAY
    private  fun checkSuckCell(){
        for(i in 1..10){
            for (j in 1..10){
                if(game.getBoard(Player.AI)[i-1][j-1] == CellState.ShipSunk) {
                    recList[(i-1)*10 + j-1].fill = Color.DARKGRAY
                }
            }
        }
    }


    init {


        // this pane's layout the board is 300 * 300
        this.setWidth(300.0)
        this.setHeight(300.0)

// used for debug
//        print("$ht")
//        print("$wd")

        // make this pane visible, otherwise it has some error with showing up
        this.isVisible = true
        // get width of this pane, should be 300.0
        val wd = getWidth()

        // to track the correct position of the cell
        var xpos = 0.0 + additx
        var ypos = 0.0
        // width of each square cell
        var widthEach = wd/ 10

        // loop over every row ever column
        for(i in 1..10){
            for (j in 1..10){
                // create rectangle wait to be draw on board
                val rec = Rectangle(widthEach,widthEach)
                // find its correct location
                xpos = widthEach * (j-1) + additx
                ypos = widthEach * (i-1)
                // rectangle's color is similar to light blue
                rec.fill = color(0.0, 0.8,0.8)
//                rectangle is stroked as required
                rec.stroke = Color.BLACK
                // place rectangle at the correct position
                rec.x = xpos
                rec.y = ypos
                // store rectangles for later modification
                recList.add(rec)
                // used for debug
//                rec.id = ((i-1)*10 + j-1).toString()

                // when click on the cell, it will be attacked
                rec.addEventFilter(MouseEvent.MOUSE_CLICKED){e ->
                    // this checks if anyone win the game; if yes, then nothing can be attacked anymore
                    if(model.toNotify){
                        // this checks if all ships are placed
                        if(game.setupedAll) {
                           var thisState = game.getBoard(Player.AI)[i-1][j-1]
                            if(thisState != CellState.Attacked){
                                val cell = Cell(j-1, i-1 ,true)
                                // used for debug
//                        println("${cell}")
//                        println("${game.getBoard(Player.Human)}")
//                        println("${game.getBoard(Player.AI)}")

                                // as game to perform attack
                                game.attackCell(cell)
                                // find sated of the attacked cell which cna be ShipHit/ShipSunk/just be Attacked
                                thisState = game.getBoard(Player.AI)[i-1][j-1]
                                // change cell to the color corresponding to its state
                                if(thisState == CellState.Attacked) {
                                    rec.fill = Color.LIGHTGRAY
                                } else if (thisState == CellState.ShipHit) {
                                    rec.fill = Color.CORAL
                                } else if (thisState == CellState.ShipSunk) {
                                    checkSuckCell()
                                }

                                // used for debug
//                        for (i in game.getBoard(Player.AI)){
//                            for (j in i){
//                                println("$j")
//                            }
//
//                        }
//                        println("${game.getAttackedCells(Player.AI).last()}")

                                // tell the model to update view after (the attack form human & the attack form AI)
                                model.notifyObservers()
                                // check if anyone wins the game/ the game should continue
                                if(game.gameState.toString() == "WonHuman" || game.gameState.toString() == "WonAI") {
//                            println("${game.onPlayerWon}")
                                    // if anyone wins the model will record that, and notify to update view to "you won"/"you were defeated"
                                    model.turnOffNotify()
                                    model.notifyObservers()
//                                bu1.isDisable = true
                                }
                            }

                        }
                    }
                }
                this.children.add(rec)
            }
        }
    }
}