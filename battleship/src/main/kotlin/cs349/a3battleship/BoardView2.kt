package cs349.a3battleship

import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.CellState
import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Player
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.paint.Color.color
import javafx.scene.shape.Rectangle

// this is left(Human's) board where human perform attack
class BoardView2(
    private val additx: Double,
    val game: Game,
    private val model: Model

): Pane() , IView  {
    // updateView after everytime AI attacked
    override fun updateView(toNotify:Boolean) {
        this.setWidth(300.0)
        this.setHeight(300.0)
//        this.isVisible = true
        val wd = getWidth()
        var widthEach = wd/ 10
        this.children.clear()
        if(toNotify){
            redraw(widthEach)
        } else {  // finish game
            drawOff(widthEach)
        }
    }

    // draw board when game finished, with all suck cell DARKGRAY; else return to original
    private  fun drawOff(widthEach:Double){
        var xpos:Double
        var ypos:Double
        for(i in 1..10) {
            for (j in 1..10) {
                val rec = Rectangle(widthEach,widthEach)
                xpos = widthEach * (j-1) + additx
                ypos = widthEach * (i-1)
                val thisState = game.getBoard(Player.Human)[i-1][j-1]
                // check if this cell's state is sunk; if yes, then turn it to DARKGRAY; else turn it back to blue
                if (thisState == CellState.ShipSunk) {
                    rec.fill = Color.DARKGRAY
                } else {
                    rec.fill = color(0.0, 0.8,0.8)
                }
                rec.stroke = Color.BLACK
                rec.x = xpos
                rec.y = ypos
                this.children.add(rec)
            }
        }
    }

    // draw board everytime a ship is sunk
    private  fun redraw(widthEach:Double){
        var xpos = 0.0 + additx
        var ypos = 0.0
        for(i in 1..10) {
            for (j in 1..10) {
                val rec = Rectangle(widthEach,widthEach)
                xpos = widthEach * (j-1) + additx
                ypos = widthEach * (i-1)
                val thisState = game.getBoard(Player.Human)[i-1][j-1]
                // check possible state and draw board to corresponding color
                if(thisState == CellState.Attacked) {
                    rec.fill = Color.LIGHTGRAY
                } else if (thisState == CellState.ShipHit) {
                    rec.fill = Color.CORAL
                } else if (thisState == CellState.ShipSunk) {
                    rec.fill = Color.DARKGRAY
                } else {
                    rec.fill = color(0.0, 0.8,0.8)
                }
                rec.stroke = Color.BLACK
                rec.x = xpos
                rec.y = ypos
                this.children.add(rec)
            }
        }
    }

    // helper function to draw the board at very begin, every cell is blue
    private  fun draw(widthEach:Double){
        var xpos = 0.0 + additx
        var ypos = 0.0
        for(i in 1..10){
            for (j in 1..10){
                val rec = Rectangle(widthEach,widthEach)
                xpos = widthEach * (j-1) + additx
                ypos = widthEach * (i-1)
                rec.fill = color(0.0, 0.8,0.8)
                rec.stroke = Color.BLACK
                rec.x = xpos
                rec.y = ypos
                rec.id = ((i-1)*10 + j-1).toString()
                this.children.add(rec)
            }
        }
    }


    init {
        // set up layout
        this.setWidth(300.0)
        this.setHeight(300.0)
        // get the width of this pane; should be 300.0
        val wd = getWidth()
        // width of each square cell
        var widthEach = wd/ 10
        // ask helper to draw cell with corresponding size
        draw(widthEach)
        // add to model for later updateView
        model.addView(this)
    }
}