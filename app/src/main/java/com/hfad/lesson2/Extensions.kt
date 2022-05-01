package com.hfad.lesson2

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.locks.Condition

fun View.showSnackBar(
    //что принимает на вход
    text: String,
    actionText: String,
    //что нужно делать при нажатии на actionText
    action: (View) -> Unit,
    //задать ему длину
    length: Int = Snackbar.LENGTH_INDEFINITE

) {
    //то что произойдет
    //this - чтобы обратиться к View
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

//extension для управления видимостью View
fun View.hide() {
    if (visibility != View.GONE)
        visibility = View.GONE
}

fun View.show() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
}

//если надо спрятать View в зависимости от какого-то условия.
// Передать в hideIf() ананимную функцию, возвращающую Boolean

fun View.hideIf(condition: () -> Boolean) {
    //если не View.GONE И (важно!) выполнилось то условие по которому нужно скрыть эту View
    if (visibility != View.GONE && condition())
        visibility = View.GONE
}
