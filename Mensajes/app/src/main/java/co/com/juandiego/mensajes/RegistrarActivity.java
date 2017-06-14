package co.com.juandiego.mensajes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.com.juandiego.mensajes.modelo.Cita;

public class RegistrarActivity extends AppCompatActivity {

    EditText etNumero;
    EditText etMensaje;
    Button btnRegistrar;

    private DatabaseReference mFirebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        setTitle("Registrar mensaje");

        etNumero = (EditText)findViewById(R.id.etNumero);
        etMensaje = (EditText)findViewById(R.id.etMensaje);
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etMensaje.getText().toString().equals("")){
                    Cita cita = new Cita("no",etMensaje.getText().toString());
                    mFirebaseDatabaseReference.child(CitaActivity.CITAS).child(etNumero.getText().toString()).push().setValue(cita);
                    Toast.makeText(getApplicationContext(),"Mensaje registrado",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
