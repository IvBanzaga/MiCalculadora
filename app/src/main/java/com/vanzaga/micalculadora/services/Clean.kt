package com.vanzaga.micalculadora.services

import android.widget.TextView

/**
 * Clase Clean
 * Clase que hereda de Calculator
 * Clase que se encarga de limpiar la pantalla
 * @param screen Pantalla de la calculadora
 */
open class Clean(screen: TextView) : Calculator(screen) {

    /**
     * Función para limpiar la pantalla con la tecla DEL
     * Limpiamos la pantalla, los números y la operación
     */

    fun limpiarPantalla() {
        screen.text = "0" // Mostramos 0 en la pantalla
        firstNum = 0.0 // Reiniciamos el primer número
        secondNum = 0.0 // Reiniciamos el segundo número
        operacion = null // Reiniciamos la operación
    }

    /**
     * Función para eliminar el último dígito con la tecla AC
     * Si la pantalla tiene más de un dígito, eliminamos el último
     * Si no, mostramos 0
     */

    fun deleteUlitmoDigito() {
        screen.text = if (screen.text.length > 1) { // Si la pantalla tiene más de un dígito
            screen.text.dropLast(1) // Eliminamos el último dígito
        } else { // Si la pantalla tiene un dígito
            "0" // Mostramos 0
        }
    }
}