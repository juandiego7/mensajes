package co.com.juandiego.mensajes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String CEDULA = "cedula";
    public static final String CONFIG = "config";
    EditText etNumero;
    Button btnIngresar;
    SharedPreferences sharedPref;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = this.getSharedPreferences(CONFIG,0);

        intent = new Intent(this,CitaActivity.class);

        if (!sharedPref.getString(CEDULA,"").equals("")){
            startActivity(intent);
        }

        etNumero = (EditText)findViewById(R.id.etNumero);
        btnIngresar = (Button)findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String cedula = etNumero.getText().toString();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CEDULA, cedula);
        editor.commit();

        startActivity(intent);

    }
}
