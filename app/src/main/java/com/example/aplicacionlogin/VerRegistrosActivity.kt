import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionlogin.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class VerRegistrosActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registros)

        // Inicialización de FirebaseAuth y FirebaseDatabase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("usuarios")

        // Referencias a los campos de texto en el layout
        val editTextDate3 = findViewById<EditText>(R.id.editTextDate3)
        val editTextDate9 = findViewById<EditText>(R.id.editTextDate9)
        val editTextDate10 = findViewById<EditText>(R.id.editTextDate10)
        val editTextDate11 = findViewById<EditText>(R.id.editTextDate11)
        val editTextDate12 = findViewById<EditText>(R.id.editTextDate12)
        val editTextDate13 = findViewById<EditText>(R.id.editTextDate13)

        // Obtener el usuario actual de FirebaseAuth
        val currentUser = auth.currentUser
        currentUser?.let {
            val userId = it.uid

            // Obtener los datos del usuario desde la base de datos de Firebase
            database.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Obtener los valores de los campos
                        val nombre = snapshot.child("nombre").getValue(String::class.java)
                        val correo = snapshot.child("correo").getValue(String::class.java)
                        val fechaNacimiento = snapshot.child("fechaNacimiento").getValue(String::class.java)
                        val identificacion = snapshot.child("identificacion").getValue(String::class.java)
                        val apoyoDeseado = snapshot.child("apoyoDeseado").getValue(String::class.java)

                        // Establecer los valores en los EditTexts
                        editTextDate3.setText(nombre)
                        editTextDate9.setText(correo)
                        editTextDate10.setText(fechaNacimiento)
                        editTextDate11.setText(identificacion)
                        editTextDate12.setText(apoyoDeseado)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Manejo de errores
                }
            })
        }
    }
}
