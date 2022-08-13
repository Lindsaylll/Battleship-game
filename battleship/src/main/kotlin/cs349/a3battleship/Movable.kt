
package cs349.a3battleship

import cs349.a3battleship.model.Cell
import cs349.a3battleship.model.Game
import cs349.a3battleship.model.Orientation
import cs349.a3battleship.model.Player
import cs349.a3battleship.model.ships.ShipType
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.transform.Transform
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.random.Random

class MovableManager(parent: Node, var game:Game, var bu1: Button) {
    private var movingNode: Node? = null
    private val types = ShipType.values().toList()
    private var ox = 0.0
    private var idx = 0
    private var oy = 0.0
    private var midYs = listOf(52.5, 67.5, 67.5, 82.5,97.5)
    private var fleetsXpos = listOf(365.0, 396.0, 427.0, 458.0, 489.0)
    private var ty = 0.0
    private var orientation = 1 // 1 is VERTICAL
    private var sType = 0
    private var cell = Cell(100,100)

    // find the correct cell to place the ship (snap)
    private fun findCell(x:Double, y: Double){
        var xid = (x/30.0).toInt()
        var yid = (y/30.0).toInt()
        cell = Cell(xid, yid)
            // used for dubug
//        println("findcell renewing cell value $xid, $yid")
//        return listOf<Int>(xid, yid)
//        println("xid: $xid;;;;   yid: $yid")
    }


    // check if the cell is valid ot place teh ship by call helper in game class
    private  fun checkvalid(ori:Int): Boolean{
        val tmpShip = game.placeShip(Player.Human, types[sType], Orientation.values()[ori], cell)
        if(tmpShip == null){
            return false
        } else {
            cell = tmpShip.getCells()[0]
            return true
        }
    }

    init {

        // bellowing is referred to cs349-public/input/dragging
        parent.addEventHandler(MouseEvent.MOUSE_CLICKED) { e ->
            val node = movingNode
            if (node != null) {
                if (e.button == MouseButton.PRIMARY) {
                    // used for debug
                    println("drop '$node'")
                    // find where will the ship be placed
                    findCell(max((e.sceneX-25.0), 0.0), max(0.0,e.sceneY-midYs[idx]))
                    // if it is valid to drop at this location, it will snap to this cell; otherwise it will be put back to original location
                    if(checkvalid(node.id.toInt()) == false) {
                        // used for debug
//                        println("     not valid       ")
//                        println("${node.layoutX}")
                        node.translateX = 0.0
                        node.translateY = 0.0
                    } else {
                        // snap to the cell
                        node.translateX = -(338.0+idx*31) + cell.x * 30
                        node.translateY = cell.y*30.0+25.0 + 3.0
                        // ask game to record that human placed the ship into this cell
                        game.placeShip(Player.Human, types[sType], Orientation.values()[node.id.toInt()], cell)
                    }
                    // used for debug
//                    println("$select")
//                    println("${e.sceneX}")
//                    println("${e.sceneY}")

                    movingNode = null
                    // if the ships are all at valid cell, then the game is able to start
                    if(game.getShipsPlacedCount(Player.Human) ==5) {
                        bu1.isDisable = false
//                        if the strat button is click, the game will loop to next period:game.HumanFire
                        bu1.setOnAction {
                            game.startGame()
                            // tell updateView that all have set up
                            game.setupedAll = true
                            println("${game.gameState}")
                            // after start the button is not able to be clicked anymore
                            bu1.isDisable = true
                        }
                    }
                } else if (e.button == MouseButton.SECONDARY) {
//                    // donothing rotation is disabled
//                    // we want to modify orientation to another of the two values once we have a right click
//                    // if (orientation == 1) (1-1)/-1 == 0
//                    // if (orientation == 0) (0-1)/-1 == 1 so this equation can switch it between
//                    orientation = (node.id.toInt() - 1) / -1
//                    // record current state for each node
//                    node.id = orientation.toString()
////                    node.transforms.add(Transform.translate())
//                    if(orientation == 0){
//                        // rotate to horizontal
//                        node.transforms.add(Transform.rotate(-90.0, e.sceneX,e.sceneY))
//                    } else {
//                        // rotate to vertical
//                        node.transforms.add(Transform.rotate(90.0, e.sceneX,e.sceneY))
//                    }
//                    // used for debug
////                    println("rotation: $orientation")
                }
            }
        }
        // when the mouse moving, we need to track how much we moved
        parent.addEventFilter(MouseEvent.MOUSE_MOVED) { e ->
            val node = movingNode
            if (node != null) {
                node.translateX = e.sceneX + ox
                node.translateY = e.sceneY + oy
                // we don't want to drag the background too
                e.consume()
            }
        }
    }

    // referred to cs349-public/input/dragging
    fun makeMovable(node: Node, tp: Int, indx:Int) {
        node.onMouseClicked = EventHandler { e ->
            // is game is not started, it can be left-clicked; otherwise not
            if (movingNode == null && e.button == MouseButton.PRIMARY && !game.setupedAll) {
//                println("${game.gameState}")
                // initialize the state of the node to indicate that it is now vertical
                if(node.id==null){
                    node.id = "1"
                }
                // this is for recording the type of the ship
                idx = indx
                println("click '$node'")
                // find the cell where the ship was placed and clicked
                findCell(max((e.sceneX-25.0), 0.0), max(0.0,e.sceneY-midYs[indx]))
//                since we pick it up again, we should remove it from where we placed it before; it is not placed, nothing happens
                game.removeShip(Player.Human, cell)
                // type of the ship
                sType = tp
                //used for debug
//                println("$sType")  // correct
//                println("${e.sceneX}")
//                println("${e.sceneY}")
                this.movingNode = node
                ox = node.translateX - e.sceneX
                oy = node.translateY - e.sceneY
                // every time we pick up the ship, we need to check if this pick will make anyship absent form the board, if yes, the
                // start button should not be able to click on
                if(game.getShipsPlacedCount(Player.Human) !=5) {bu1.isDisable = true}
                // we don't want to drag the background too
                e.consume()
            }
        }
    }
}




