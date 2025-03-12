package com.vanzaga.micalculadora.services

import android.widget.TextView
import com.vanzaga.micalculadora.R
import kotlin.math.round

open class Calculator(protected val screen: TextView) {

    /**
     * Variables de la calculadora
     * @property firstNumber: Primer número de la operación
     * @property secondNumber: Segundo número de la operación
     * @property operation: Operación a realizar
     */

    var firstNumber = 0.0
    var secondNumber = 0.0
    var operation: String? = null

    /**
     * Función para manejar las operaciones
     * @param opId: Identificador de la operación
     * Utilizamos when para asignar la operación correspondiente, es como un switch en Java
     * @see https://kotlinlang.org/docs/control-flow.html#when-expression
     */

    open fun operaciones(opId: Int) {
        firstNumber = screen.text.toString().toDouble()
        operation = when (opId) {
            R.id.btplus -> "+"
            R.id.btMinus -> "-"
            R.id.btMul -> "*"
            R.id.btDiv -> "/"
            R.id.btRaiz -> "√"
            else -> null
        }
        if (operation != "√") {
            screen.text = "0"
        }
    }

    /**
     * Función para calcular el resultado de operaciones básicas
     * Utilizamos when para asignar la operación correspondiente, es como un switch en Java
     * @see https://kotlinlang.org/docs/control-flow.html#when-expression
     */

    open fun calcularResultado() {
        secondNumber = screen.text.toString().toDouble()
        val result = when (operation) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
            else -> 0.0
        }
        // Redondear a dos decimales
        val roundedResult = round(result * 100) / 100

        // Mostrar sin decimales si es entero
        screen.text = if (roundedResult % 1 == 0.0) {
            roundedResult.toInt().toString()
        } else {
            roundedResult.toString()
        }
    }

    /**
     * Función para calcular la raíz cuadrada
     * Utilizamos la función sqrt de la clase Math
     * @see https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.math/sqrt.html
     */

    open fun calcularRaiz() {
        firstNumber = screen.text.toString().toDouble()

        // Validamos que el número no sea negativo
        val result = if (firstNumber >= 0) Math.sqrt(firstNumber) else Double.NaN

        // Redondear a dos decimales
        val roundedResult = round(result * 100) / 100

        // Mostrar sin decimales si es entero
        screen.text = if (roundedResult % 1 == 0.0) {
            roundedResult.toInt().toString()
        } else {
            roundedResult.toString()
        }
    }

    /**
     * Funcion para manejar los numeros
     * @param number: Número presionado
     * Si el número es 0 y no es una coma, mostramos el número
     * Si no, concatenamos el número
     * @see https://kotlinlang.org/docs/basic-syntax.html#string-templates
     */

    open fun numeroPresionado(number: String) {
        screen.text = if (screen.text == "0" && number != ",") { // Si el número es 0 y no es una coma
            number
        } else {
            "${screen.text}$number" // Concatenar el número
        }
    }
}