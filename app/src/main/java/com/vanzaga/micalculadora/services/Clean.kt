package com.vanzaga.micalculadora.services

import android.widget.TextView

open class Clean(screen: TextView) : Calculator(screen) {

    /**
     * Función para limpiar la pantalla con la tecla DEL
     * Limpiamos la pantalla, los números y la operación
     */

    fun limpiarPantalla() {
        screen.text = "0"
        firstNumber = 0.0
        secondNumber = 0.0
        operation = null
    }

    /**
     * Función para eliminar el último dígito con la tecla AC
     * Si la pantalla tiene más de un dígito, eliminamos el último
     * Si no, mostramos 0
     */

    fun deleteUlitmoDigito() {
        screen.text = if (screen.text.length > 1) {
            screen.text.dropLast(1)
        } else {
            "0"
        }
    }
}