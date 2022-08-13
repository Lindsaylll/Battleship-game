package cs349.a3battleship.model

enum class CellState {
    Ocean,
    Attacked, //LIGHTGRAY
    ShipHit, //CORAL
    ShipSunk //DARKGRAY
}