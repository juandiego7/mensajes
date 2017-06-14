    package co.com.juandiego.mensajes;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

    public class NumeroActivity extends AppCompatActivity implements View.OnClickListener{

        final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 100;

        Button btnVerNumero;
        TextView tvNumero;
        TelephonyManager mTelephonyManager;
        int permissionCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numero);btnVerNumero = (Button)findViewById(R.id.btnVerNumero);
        tvNumero = (TextView)findViewById(R.id.tvNumero);

        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        btnVerNumero.setOnClickListener(this);

        solicitarPermiso();


    }

        @Override
        public void onClick(View view) {
            // tvNumero.setText("Permiso: "+permissionCheck);
            AccountManager am = AccountManager.get(this);
            Account[] accounts = am.getAccounts();
            String phoneNumber = "vacio";

            ArrayList googleAccounts = new ArrayList();
            for (Account ac : accounts) {
                String acname = ac.name;
                String actype = ac.type;

                System.out.println("Accounts : " + acname + ", " + actype);
                if(actype.equals("com.whatsapp")){
                    phoneNumber = ac.name;
                }
            }
            Toast.makeText(this,phoneNumber,Toast.LENGTH_LONG).show();
            tvNumero.setText("Mi numero: "+mTelephonyManager.getLine1Number());
        }


        public void solicitarPermiso(){
            //permissionCheck = ContextCompat.checkSelfPermission(this,
            //       Manifest.permission.READ_PHONE_STATE);

            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                btnVerNumero.setEnabled(false);
                Toast.makeText(this,"permiso negado",Toast.LENGTH_LONG).show();

                // Should we show an explanation?
           /* if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {

                Toast.makeText(this,"explicacion",Toast.LENGTH_LONG).show();

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {*/
                Toast.makeText(this,"solicitud",Toast.LENGTH_LONG).show();
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                //}
            }else if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"permiso ortogado",Toast.LENGTH_LONG).show();
                btnVerNumero.setEnabled(true);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode,
                                               String permissions[], int[] grantResults) {
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                        btnVerNumero.setEnabled(true);

                    } else {

                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                        btnVerNumero.setEnabled(false);
                    }
                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request
            }
        }
    }