package cs349.a3battleship


class Model {

    //region Observer Related

    // all views of this model
    private val views: ArrayList<IView> = ArrayList()
    var toNotify = true

    // method that the views can use to register themselves with the cs349.a3battleship.Model
    // once added, they are told to update and get state from the cs349.a3battleship.Model
    fun addView(view: IView) {
        views.add(view)
    }

    fun removeView(view: IView) {
        views.remove(view)
    }

    // the model uses this method to notify all Views that the data has changed
    // the expectation is that the Views will refresh themselves to display new data when appropriate
     fun notifyObservers() {
        for (view in views) {
            view.updateView(toNotify)
        }
    }
    fun turnOffNotify() {
        toNotify = false
    }



    init {

    }
}