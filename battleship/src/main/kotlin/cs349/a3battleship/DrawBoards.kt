package cs349.a3battleship

import cs349.a3battleship.model.Game
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.*
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment




class FmView(
//    private val model: cs349.a3battleship.Model,
// to make different view for Mine/Opponent board
    private val play: String,
    private val game: Game,
    private val model: Model
): GridPane(){



    //to creat the labels 1 to 10 as a HBox item
    // font is 12.0 as required
    private fun nums() : HBox {
        var rslt = HBox()
        for (i in  1..10){
            val l = Label("${i.toString()}")
            //to place each num at correct position in the hbox; each of them have match size with each cell
            l.prefWidth = 30.0
            l.font = Font.font(12.0)
            rslt.children.add(l)
        }
        return rslt
    }

    // to creat the labels A to J as a VBox item
    // font is 12.0 as required
    private fun letters() : VBox {
        var rslt = VBox()
        for(c in "ABCDEFGHIJ"){
            val l = Label("$c")
            // to place each letter at correct position in the vbox; each of them have match size with each cell
            l.prefHeight = 30.0
            l.font = Font.font(12.0)
            rslt.children.add(l)
        }
        return rslt
    }

    init {
        // to control translateX when place item on pane
        val addit = 525.0 // all x + addit
        // required style of "Opponent's Formation" and "My Formation"
        val header = Font.font("Arial", FontWeight.BOLD, 16.0)
        var l: Label
        // to create different header for different board
        if(play == "Mine") {
            l = Label("My Formation")
        } else {
            l = Label("Opponent's Formation")
        }
        // setup style
        l.font = header
        l.textAlignment = TextAlignment.CENTER
        // to locate at different but correct position for different board
        if(play == "Mine") {
            l.translateX =95.0
        } else {
            l.translateX = 60.0 + addit
        }
        // height is 25.0 as required
        l.prefHeight = 25.0

        // create the nums by using helper function
        val nums1 = nums()
        nums1.alignment = Pos.CENTER
        // height is 25.0 as required
        nums1.prefHeight = 25.0
        if(play == "Mine") {
            nums1.translateX = 10.0
        } else {
            nums1.translateX = 10.0 +   260.0
        }
        val nums2 = nums()
        nums2.alignment = Pos.CENTER
        // height is 25.0 as required
        nums2.prefHeight = 25.0
        if(play == "Mine") {
            nums2.translateX = 10.0
        } else {
            nums2.translateX = 10.0 +  260.0
        }

        // create the letters by using helper function
        val letters1 = letters()
        letters1.alignment = Pos.CENTER
        // width is 25.0 as required
        letters1.prefWidth = 25.0
        if(play == "Opponent"){
            letters1.translateX = addit
        }
        val letters2 = letters()
        letters2.alignment = Pos.CENTER
        // width is 25.0 as required
        letters2.prefWidth = 25.0

        // add them to pane
        add(l, 1,0)
        add(nums1, 1,1)
        add(letters1, 0,2)
        add(letters2, 2,2)
        // make different board for different player; one can be clicked and one should always update view
        if(play == "Mine"){
            val boardGraph = BoardView2(0.0, game, model)
            add(boardGraph, 1,2)
        } else {
            val  boardGraph = BoardView(addit, game, model)
            add(boardGraph, 1,2)
        }
        add(nums2, 1,3)
    }
}