package com.vanzaga.micalculadora

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.round
import kotlin.math.sqrt
import com.vanzaga.micalculadora.logica.Operaciones

/**
 * Clase principal de la aplicación
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    /**
     * Inicializamos la clase Operaciones
     */
    val calcular = Operaciones()

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
        calcular.operation = null // Inicializar las operacines con valor nulo

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
        calcular.screen = findViewById(R.id.screen)

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
            R.id.bt0 -> calcular.numeroPresionado("0")
            R.id.bt1 -> calcular.numeroPresionado("1")
            R.id.bt2 -> calcular.numeroPresionado("2")
            R.id.bt3 -> calcular.numeroPresionado("3")
            R.id.bt4 -> calcular.numeroPresionado("4")
            R.id.bt5 -> calcular.numeroPresionado("5")
            R.id.bt6 -> calcular.numeroPresionado("6")
            R.id.bt7 -> calcular.numeroPresionado("7")
            R.id.bt8 -> calcular.numeroPresionado("8")
            R.id.bt9 -> calcular.numeroPresionado("9")
            R.id.btComma -> calcular.numeroPresionado(".")
            R.id.btplus, R.id.btMinus, R.id.btMul, R.id.btDiv -> calcular.operaciones(view.id)
            R.id.btClear -> calcular.limpiarPantalla()
            R.id.btEqual -> calcular.calcularResultado()
            R.id.btAc -> calcular.deleteUlitmoDigito()
            R.id.btRaiz -> calcular.calcularRaiz()
        }
    }

}
