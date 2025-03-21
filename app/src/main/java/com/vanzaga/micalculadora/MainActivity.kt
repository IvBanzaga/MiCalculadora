/**
 * Paquete de la aplicación
 * @see https://kotlinlang.org/docs/reference/packages.html
 */
package com.vanzaga.micalculadora

/**
 * Importamos las clases necesarias
 * @see https://kotlinlang.org/docs/reference/packages.html
 */
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vanzaga.micalculadora.services.Clean
import com.vanzaga.micalculadora.services.Calculator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Clase principal de la aplicación
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    /**
     * Inicializamos la clase Calculator y Clean
     * @property calcular: Clase para realizar operaciones
     * @property limpiar: Clase para limpiar la pantalla
     * Utilizamos lateinit para inicializar las variables más adelante
     * @see https://kotlinlang.org/docs/properties.html#late-initialized-properties
     * @see https://kotlinlang.org/docs/classes.html
     * Las declaramos como private para que solo sean accesibles desde esta clase
     * @see https://kotlinlang.org/docs/visibility-modifiers.html
     */

    private lateinit var calcular: Calculator
    private lateinit var limpiar: Clean
    private lateinit var txtViewTime: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            /**
             * Mostrar la hora actual del sistema
             */
            val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            txtViewTime.text = currentTime
            handler.postDelayed(this, 1000)
        }
    }

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
         * Inicializamos la pantalla
         * @see https://developer.android.com/reference/android/widget/TextView
         * Utilizamos findViewById para obtener la vista de la pantalla
         */

        val screen: TextView = findViewById(R.id.screen)

        /**
         * Inicializamos las clases
         * @see https://kotlinlang.org/docs/classes.html
         * @see https://kotlinlang.org/docs/properties.html
         */

        calcular = Calculator(screen)
        limpiar = Clean(screen)

        /*
         * Inicializamos los botones
         * @see https://developer.android.com/reference/android/widget/Button
         * @see https://developer.android.com/reference/android/widget/TextView
         * @see https://developer.android.com/reference/android/widget/EditText
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
        val btMc: Button = findViewById(R.id.btMc)
        val btMr: Button = findViewById(R.id.btMr)
        val btPlusNew: Button = findViewById(R.id.btPlusNew)
        val btMinusNew: Button = findViewById(R.id.btMinusNew)
        val btMemory: Button = findViewById(R.id.btMs)

        /**
         * Mostrar Hora del sistema
         */
        txtViewTime = findViewById(R.id.TxtViewTime)
        handler.post(runnable)

        /**
         * Asignamos el evento click a los botones
         * Utilizamos arrayOf y forEach para asignar el evento click a todos los botones
         * @see https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/for-each.html
         * @see https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/array-of.html
         */

        arrayOf(
            bt0, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, btComma,
            btPlus, btMinus, btMul, btDiv, btClear, btEqual, btAc, btRaiz,
            btMc, btMr, btPlusNew, btMinusNew, btMemory
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
        when (view?.id) { // Obtenemos el ID de la vista
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
            R.id.btClear -> limpiar.limpiarPantalla()
            R.id.btEqual -> calcular.calcularResultado()
            R.id.btAc -> limpiar.deleteUlitmoDigito()
            R.id.btRaiz -> calcular.calcularRaiz()
            R.id.btMc -> calcular.limpiarMemoria()
            R.id.btMr -> calcular.mostrarMemoria()
            R.id.btPlusNew -> calcular.sumarMemoria()
            R.id.btMinusNew -> calcular.restarMemoria()
            R.id.btMs -> calcular.almacenarNumero()
        }
    }

    /**
     * Función onDestroy
     * Detenemos el handler cuando la actividad se destruye
     */

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}