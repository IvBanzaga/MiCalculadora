package com.vanzaga.micalculadora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.round
import kotlin.math.sqrt

/**
 * Clase principal de la aplicación
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    /**
     * Variables de la calculadora
     * @property firstNumber: Primer número de la operación
     * @property secondNumber: Segundo número de la operación
     * @property operation: Operación a realizar
     * @property screen: Pantalla de la calculadora
     */

    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var operation: String? = null
    private lateinit var screen: TextView

    /**
     * Función onCreate
     * @param savedInstanceState: Estado de la instancia
     * Habilitamos el modo EdgeToEdge
     * @see https://developer.android.com/guide/topics/ui/look-and-feel/edge-to-edge
     * Inicializamos la vista de la actividad
     * @see https://developer.android.com/guide/components/activities/activity-lifecycle
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        /**
         * Inicializamos los elementos de la interfaz
         */
        operation = null // Inicializar las operacines con valor nulo

        /*
         * Inicializamos los botones
         */
        val bt0: Button = findViewById(R.id.bt0)
        val bt1: Button = findViewById(R.id.bt1)
        val bt2: Button = findViewById(R.id.bt2)
        val bt3: Button = findViewById(R.id.bt3)
        val bt4: Button = findViewById(R.id.bt4)
        val bt5: Button = findViewById(R.id.bt5)
        val bt6: Button = findViewById(R.id.bt6)
        val bt7: Button = findViewById(R.id.bt7)
        val bt8: Button = findViewById(R.id.bt8)
        val bt9: Button = findViewById(R.id.bt9)
        val btComma: Button = findViewById(R.id.btComma)
        val btPlus: Button = findViewById(R.id.btplus)
        val btMinus: Button = findViewById(R.id.btMinus)
        val btMul: Button = findViewById(R.id.btMul)
        val btDiv: Button = findViewById(R.id.btDiv)
        val btClear: Button = findViewById(R.id.btClear)
        val btEqual: Button = findViewById(R.id.btEqual)
        val btAc: Button = findViewById(R.id.btAc)
        val btRaiz: Button = findViewById(R.id.btRaiz)

        /**
         * Inicializamos la pantalla
         */
        screen = findViewById(R.id.screen)

        /**
         * Asignamos el evento click a los botones
         * Utilizamos un forEach para asignar el evento a todos los botones
         * @see https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/for-each.html
         * @see https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/array-of.html
         */
        arrayOf(
            bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, btComma,
            btPlus, btMinus, btMul, btDiv, btClear, btEqual, btAc, btRaiz
        ).forEach { it.setOnClickListener(this) }

    }

    /**
     * Función para manejar los eventos de los botones
     * @param view: Vista del botón presionado
     * Utilizamos when para asignar la función correspondiente, es como un switch en Java
     * @see https://kotlinlang.org/docs/control-flow.html#when-expression
     * Reescribimos la función onClick de la interfaz View.OnClickListener y manejamos los eventos de los botones
     */
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.bt0 -> numeroPresionado("0")
            R.id.bt1 -> numeroPresionado("1")
            R.id.bt2 -> numeroPresionado("2")
            R.id.bt3 -> numeroPresionado("3")
            R.id.bt4 -> numeroPresionado("4")
            R.id.bt5 -> numeroPresionado("5")
            R.id.bt6 -> numeroPresionado("6")
            R.id.bt7 -> numeroPresionado("7")
            R.id.bt8 -> numeroPresionado("8")
            R.id.bt9 -> numeroPresionado("9")
            R.id.btComma -> numeroPresionado(".")
            R.id.btplus, R.id.btMinus, R.id.btMul, R.id.btDiv -> operaciones(view.id)
            R.id.btClear -> limpiarPantalla()
            R.id.btEqual -> calcularResultado()
            R.id.btAc -> deleteUlitmoDigito()
            R.id.btRaiz -> calcularRaiz()
        }
    }

    /**
     * Funcion para manejar los numeros
     * @param number: Número presionado
     * Si el número es 0 y no es una coma, mostramos el número
     * Si no, concatenamos el número
     * @see https://kotlinlang.org/docs/basic-syntax.html#string-templates
     */
    private fun numeroPresionado(number: String) {
        screen.text = if (screen.text == "0" && number != ",") { // Si el número es 0 y no es una coma
            number
        } else {
            "${screen.text}$number" // Concatenar el número
        }
    }

    /**
     * Función para manejar las operaciones
     * @param opId: Identificador de la operación
     * Utilizamos when para asignar la operación correspondiente, es como un switch en Java
     * @see https://kotlinlang.org/docs/control-flow.html#when-expression
     */
    private fun operaciones(opId: Int) {
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
    private fun calcularResultado() {
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
    private fun calcularRaiz() {
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
     * Función para limpiar la pantalla con la tecla DEL
     * Limpiamos la pantalla, los números y la operación
     */
    private fun limpiarPantalla() {
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
    private fun deleteUlitmoDigito() {
        screen.text = if (screen.text.length > 1) {
            screen.text.dropLast(1)
        } else {
            "0"
        }
    }
}
