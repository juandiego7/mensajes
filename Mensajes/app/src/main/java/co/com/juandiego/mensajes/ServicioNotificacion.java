package co.com.juandiego.mensajes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import co.com.juandiego.mensajes.modelo.Cita;

public class ServicioNotificacion extends Service {

    private DatabaseReference mFirebaseDatabaseReference;
    int notificacionId = 1001;
    private String cedula;
    private SharedPreferences sharedPref;

    NotificationCompat.Builder mBuilder;

    public ServicioNotificacion() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        sharedPref = this.getSharedPreferences(MainActivity.CONFIG,0);

        cedula = sharedPref.getString(MainActivity.CEDULA,"");


        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mFirebaseDatabaseReference.child(CitaActivity.CITAS).child(cedula).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Cita cita = dataSnapshot.getValue(Cita.class);


                if (cita.getEstado().equals("no")) {
                    cita.setEstado("si");
                    mFirebaseDatabaseReference.child(CitaActivity.CITAS).child(cedula).child(dataSnapshot.getKey()).setValue(cita);
                    Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    mBuilder = new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.drawable.ic_action_user)
                            .setContentTitle("Confirmación de cita")
                            .setContentText(cita.getMensaje())
                            .setOnlyAlertOnce(true)
                            .setSound(uri)
                            .setVibrate(new long[] {100, 250, 100, 500})
                            .setAutoCancel(true);

                    Intent resultIntent = new Intent(getApplicationContext(), CitaActivity.class);
                    PendingIntent result = PendingIntent.getActivity(getApplicationContext(),0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

                    mBuilder.setContentIntent(result);
                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    // mId allows you to update the notification later on.
                    mNotificationManager.notify(notificacionId, mBuilder.build());

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        return START_STICKY;
    }
}
