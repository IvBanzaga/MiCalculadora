package com.vanzaga.micalculadora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.round
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var operation: String? = null
    private lateinit var screen: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        /**
         * Inicializamos los elementos de la interfaz
         */
        operation = null // Inicializar las operacines con valor nulo

        // Inicializar los botones y pantalla
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
        screen = findViewById(R.id.screen)

        // Asignar eventos a los botones
        arrayOf(
            bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, btComma,
            btPlus, btMinus, btMul, btDiv, btClear, btEqual
        ).forEach { it.setOnClickListener(this) }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.bt0 -> onNumberPressed("0")
            R.id.bt1 -> onNumberPressed("1")
            R.id.bt2 -> onNumberPressed("2")
            R.id.bt3 -> onNumberPressed("3")
            R.id.bt4 -> onNumberPressed("4")
            R.id.bt5 -> onNumberPressed("5")
            R.id.bt6 -> onNumberPressed("6")
            R.id.bt7 -> onNumberPressed("7")
            R.id.bt8 -> onNumberPressed("8")
            R.id.bt9 -> onNumberPressed("9")
            R.id.btComma -> onNumberPressed(".")
            R.id.btplus, R.id.btMinus, R.id.btMul, R.id.btDiv -> onOperationPressed(view.id)
            R.id.btClear -> clearScreen()
            R.id.btEqual -> calculateResult()
        }
    }

    private fun onNumberPressed(number: String) {
        screen.text = if (screen.text == "0" && number != ",") {
            number
        } else {
            "${screen.text}$number"
        }
    }

    private fun onOperationPressed(opId: Int) {
        firstNumber = screen.text.toString().toDouble()
        operation = when (opId) {
            R.id.btplus -> "+"
            R.id.btMinus -> "-"
            R.id.btMul -> "*"
            R.id.btDiv -> "/"
            else -> null
        }
        screen.text = "0"
    }

    private fun calculateResult() {
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

    private fun clearScreen() {
        screen.text = "0"
        firstNumber = 0.0
        secondNumber = 0.0
        operation = null
    }
}
