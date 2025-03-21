package com.vanzaga.micalculadora.services

import android.widget.TextView
import com.vanzaga.micalculadora.R
import kotlin.math.round

/**
 * Clase para manejar las operaciones de la calculadora
 * @property screen: Pantalla de la calculadora
 */
open class Calculator(protected val screen: TextView) {

    /**
     * Variables de la calculadora
     * @property firstNum: Primer número de la operación
     * @property secondNum: Segundo número de la operación
     * @property operacion: Operación a realizar
     */

    var firstNum = 0.0 // Inicializar el primer número en 0
    var secondNum = 0.0 // Inicializar el segundo número en 0
    var operacion: String? = null // Inicializar la operación como nula
    var memoria = 0.0 // Inicializar la memoria en 0

    /**
     * Función para manejar las operaciones
     * @param opId: Identificador de la operación
     * Utilizamos when para asignar la operación correspondiente, es como un switch en Java
     * @see https://kotlinlang.org/docs/control-flow.html#when-expression
     */

    open fun operaciones(opId: Int) {
        val txtPantalla = screen.text.toString()
        val sigOperacion = when (opId) { // Asignar la operación correspondiente
            R.id.btplus -> "+"
            R.id.btMinus -> "-"
            R.id.btMul -> "*"
            R.id.btDiv -> "/"
            R.id.btRaiz -> "√"
            else -> null
        }
        if (sigOperacion != null && sigOperacion != "√") { // Si no es raíz cuadrada, agregar la operación a la pantalla
            screen.text = "$txtPantalla$sigOperacion" // Concatenar la operación al texto actual
        } else if (sigOperacion == "√") { // Si es raíz cuadrada, calcular inmediatamente
            calcularRaiz()
        }
    }

    /**
     * Función para calcular el resultado de operaciones básicas
     * Utilizamos when para asignar la operación correspondiente, es como un switch en Java
     * @see https://kotlinlang.org/docs/control-flow.html#when-expression
     */

    open fun calcularResultado() {
        // Obtener el texto actual de la pantalla
        val txtPantalla = screen.text.toString()

        // Patrones para encontrar números y operadores
        val expresionNum = Regex("(?<=^|[+*/-])-?\\d+(\\.\\d+)?")
        val expresionSimb = Regex("[+\\-*/]")

        // Encontrar números y operadores en el texto actual
        val num = expresionNum.findAll(txtPantalla).map { it.value.toDouble() }.toMutableList()
        val operadores = expresionSimb.findAll(txtPantalla).map { it.value }.toMutableList()

        // Si no hay números o solo hay un número y no es una operación de raíz cuadrada, mostrar mensaje
        if (num.size < 2 && (operadores.isEmpty() || operadores[0] != "√")) {
            screen.text = "Error"
            return
        }

        // Calcular el resultado de la operación
        var resultado = num[0] // Inicializar el resultado con el primer número
        for (i in operadores.indices) { // Iterar sobre los operadores
            val siguienteNumero = num[i + 1] // Obtener el siguiente número
            resultado = when (operadores[i]) { // Realizar la operación correspondiente
                "+" -> resultado + siguienteNumero
                "-" -> resultado - siguienteNumero
                "*" -> resultado * siguienteNumero
                "/" -> if (siguienteNumero != 0.0) resultado / siguienteNumero else Double.NaN // Validar división por cero
                else -> resultado // Si no es una operación válida, mantener el resultado actual
            }
        }

        val redondea = round(resultado * 100) / 100
        val resultText = if (redondea % 1 == 0.0) { // Redondear a dos decimales
            redondea.toInt().toString() // Mostrar sin decimales si es entero
        } else { // Mostrar sin decimales si es entero
            redondea.toString()
        }

        // Mostrar el resultado
        screen.text = resultText
        firstNum = redondea
        secondNum = 0.0
        operacion = null
    }


    /**
     * Función para calcular la raíz cuadrada
     * Utilizamos la función sqrt de la clase Math
     * @see https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.math/sqrt.html
     */

    open fun calcularRaiz() {
        firstNum = screen.text.toString().toDouble() // Convertir el texto a número

        // Validamos que el número no sea negativo
        val resultado = if (firstNum >= 0) Math.sqrt(firstNum) else Double.NaN

        // Redondear a dos decimales
        val redondea = round(resultado * 100) / 100

        // Mostrar sin decimales si es entero
        screen.text = if (redondea % 1 == 0.0) { // Redondear a dos decimales
            redondea.toInt().toString() // Mostrar sin decimales si es entero
        } else { // Mostrar con decimales si no es entero
            redondea.toString()
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
                number // Mostrar el número
            } else { // Si no
                "${screen.text}$number" // Concatenar el número
            }
    }

    /**
     * Funciones de memoria
     */

    open fun limpiarMemoria() { // Limpiar memoria
        memoria = 0.0
    }

    open fun mostrarMemoria() { // Mostrar memoria
        screen.text = if (memoria % 1 == 0.0) { // Redondear a dos decimales
            memoria.toInt().toString() // Mostrar sin decimales si es entero
        } else {
            memoria.toString() // Mostrar con decimales si no es entero
        }
    }

    open fun sumarMemoria() { // Sumar a memoria
        val convertirNumero = screen.text.toString().toDoubleOrNull() // Convertir el texto a número
        if (convertirNumero != null) { // Si se pudo convertir
            val resultado = convertirNumero + memoria // Sumar el número a la memoria
            screen.text = if (resultado % 1 == 0.0) { // Redondear a dos decimales
                resultado.toInt().toString() // Mostrar sin decimales si es entero
            } else { // Mostrar con decimales si no es entero
                resultado.toString()
            }
        }
    }

    open fun restarMemoria() { // Restar a memoria
        val convertirNumero = screen.text.toString().toDoubleOrNull()
        if (convertirNumero != null) {
            val resultado = convertirNumero - memoria
            screen.text = if (resultado % 1 == 0.0) { // Redondear a dos decimales
                resultado.toInt().toString() // Mostrar sin decimales si es entero
            } else { // Mostrar con decimales si no es entero
                resultado.toString()
            }
        }
    }

    open fun almacenarNumero() { // Almacenar en memoria y limpiar pantalla
        memoria = screen.text.toString().toDouble() // Convertir el texto a número
        screen.text = "0" // Limpiar la pantalla
    }
}