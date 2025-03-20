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
    var memory = 0.0

    /**
     * Función para manejar las operaciones
     * @param opId: Identificador de la operación
     * Utilizamos when para asignar la operación correspondiente, es como un switch en Java
     * @see https://kotlinlang.org/docs/control-flow.html#when-expression
     */

    open fun operaciones(opId: Int) {
        try {
            firstNumber = screen.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            screen.text = "Error"
            return
        }
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
        // Mostrar la operación en curso
        screen.text = if (firstNumber % 1 == 0.0) {
            "${firstNumber.toInt()}${operation}"
        } else {
            "${firstNumber}${operation}"
        }
    }

    /**
     * Función para calcular el resultado de operaciones básicas
     * Utilizamos when para asignar la operación correspondiente, es como un switch en Java
     * @see https://kotlinlang.org/docs/control-flow.html#when-expression
     */

    open fun calcularResultado() {
        val currentText = screen.text.toString()
        val numberPattern = Regex("[-+]?\\d*\\.?\\d+")
        val operatorPattern = Regex("[+\\-*/]")

        // Extraer todos los números y operadores de la pantalla
        val numbers = numberPattern.findAll(currentText).map { it.value.toDouble() }.toList()
        val operators = operatorPattern.findAll(currentText).map { it.value }.toList()

        if (numbers.size < 2 || operators.isEmpty()) {
            screen.text = "Error"
            return
        }

        try {
            var result = numbers[0]
            for (i in operators.indices) {
                val nextNumber = numbers[i + 1]
                result = when (operators[i]) {
                    "+" -> result + nextNumber
                    "-" -> result - nextNumber
                    "*" -> result * nextNumber
                    "/" -> if (nextNumber != 0.0) result / nextNumber else Double.NaN
                    else -> 0.0
                }
            }

            val roundedResult = round(result * 100) / 100
            val resultText = if (roundedResult % 1 == 0.0) {
                roundedResult.toInt().toString()
            } else {
                roundedResult.toString()
            }

            // Mostrar el resultado
            screen.text = resultText
            // Actualizar el primer número para permitir operaciones consecutivas
            firstNumber = roundedResult
            secondNumber = 0.0
            operation = null
        } catch (e: NumberFormatException) {
            screen.text = "Error"
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
        screen.text =
            if (screen.text == "0" && number != ",") { // Si el número es 0 y no es una coma
                number
            } else {
                "${screen.text}$number" // Concatenar el número
            }
    }

    /**
     * Funciones de memoria
     */
    open fun memoryClear() { // Limpiar memoria
        memory = 0.0
    }

    open fun memoryRecall() { // Mostrar memoria
        screen.text = if (memory % 1 == 0.0) {
            memory.toInt().toString()
        } else {
            memory.toString()
        }
    }

    open fun memoryAdd() { // Sumar a memoria
        val currentNumber = screen.text.toString().toDoubleOrNull()
        if (currentNumber != null) {
            val result = currentNumber + memory
            screen.text = if (result % 1 == 0.0) {
                result.toInt().toString()
            } else {
                result.toString()
            }
        } else {
            screen.text = "Error"
        }
    }

    open fun memorySubtract() { // Restar a memoria
        val currentNumber = screen.text.toString().toDoubleOrNull()
        if (currentNumber != null) {
            val result = currentNumber - memory
            screen.text = if (result % 1 == 0.0) {
                result.toInt().toString()
            } else {
                result.toString()
            }
        } else {
            screen.text = "Error"
        }
    }

    open fun memorySave() { // Almacenar en memoria y limpiar pantalla
        try {
            memory = screen.text.toString().toDouble()
            screen.text = "0"
        } catch (e: NumberFormatException) {
            screen.text = "Error"
        }
    }
}