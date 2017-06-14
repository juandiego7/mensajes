package co.com.juandiego.mensajes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.com.juandiego.mensajes.modelo.Cita;

public class CitaActivity extends AppCompatActivity{


    public static final String CITAS = "citas";
    public static final String NUMERO = "numero";

    private RecyclerView recyclerView;
    private String cedula;
    private SharedPreferences sharedPref;

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<Cita,Adaptador.CitaViewHolder> mFirebaseAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewCitas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPref = this.getSharedPreferences(MainActivity.CONFIG,0);
        cedula = sharedPref.getString(MainActivity.CEDULA,"");

        setTitle(cedula);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdpater = new FirebaseRecyclerAdapter<Cita, Adaptador.CitaViewHolder>(
                Cita.class,
                R.layout.card_view,
                Adaptador.CitaViewHolder.class,
                mFirebaseDatabaseReference.child(CITAS).child(cedula)) {
            @Override
            protected void populateViewHolder(Adaptador.CitaViewHolder viewHolder, Cita model, int position) {
                viewHolder.tvMensaje.setText(model.getMensaje());
                //SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yy HH:MM:SS");
                //viewHolder.tvHora.setText(formateador.format(new Date()));

            }
        };

        mFirebaseAdpater.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.scrollToPosition(0);
            }
        });


        recyclerView.setAdapter(mFirebaseAdpater);

        Intent intent = new Intent(this, ServicioNotificacion.class);
        intent.putExtra("cedula",cedula);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.registrar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnRegistrar:
                Intent i = new Intent(this,RegistrarActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
