package manzano.utj.sistemafluxing;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.zxing.Result;

import manzano.utj.sistemafluxing.Fragment.LoginFragment;
import manzano.utj.sistemafluxing.Funciones.Datos_Locales;
import manzano.utj.sistemafluxing.Funciones.Scanner_Factura;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Inicio extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        ZXingScannerView.ResultHandler,
        LoginFragment.OnFragmentInteractionListener{


    private ZXingScannerView vista_escaner;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CargarLayoutInicial();
        PidePermisos();

       CerrarSesion();


        ValidarSesion();

    }


    public void ValidarSesion(){

        FloatingActionButton fab = findViewById(R.id.Btn_Scanner);

        if(!new Datos_Locales(this).ComprobarUsuario()){
            //Carga fragment de conexión com  opcion predeterminada
            getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, new LoginFragment()).commit();

            getSupportActionBar().hide();
            fab.setVisibility(View.INVISIBLE);
        }else{

            getSupportActionBar().show();
            fab.setVisibility(View.VISIBLE);
        }
    }

    public void CerrarSesion(){
        new Datos_Locales(this).EliminarUsuario("Usuario");
    }

    public void CargarLayoutInicial(){

        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



 //Escanear
        FloatingActionButton fab = findViewById(R.id.Btn_Scanner);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Escanear(view);
            }
        });



    }

    public void PidePermisos() {//Permiso de camara

        /*Pide el permiso la primera vez que abre la app*/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("Permiso Activado");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            Toast.makeText(this, "La applicación no podra funcionar con normalidad sin el permiso de la camara.", Toast.LENGTH_SHORT).show();
        }

        /*Pide el permiso si detecta que se quito el permiso que abre la app*/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
        /*Fin Permiso de la Camara*/

    }//Permiso de camara

    // al precionar el boton de back
    @Override
    public void onBackPressed() {

        try {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }catch (NullPointerException e){
            CargarLayoutInicial();
            vista_escaner.stopCamera();
        }
    }


    //Menu de 3 puntitos
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // Fin Menu de 3 puntitos

    // Menu hamburguesa
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_scaner) {

        } else if (id == R.id.nav_captura_manual) {

        } else if (id == R.id.nav_mis_facturas) {

        } else if (id == R.id.nav_perfil) {

        } else if (id == R.id.nav_proyectos) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //Fin  Menu hamburguesa

    // Escaner
    public void Escanear(View v) {
        vista_escaner = new ZXingScannerView(this);
        vista_escaner.setResultHandler(this);
        setContentView(vista_escaner);
        vista_escaner.startCamera();
    }

    // Recibe el resultado del escaneo y lo guarda en la var dato_scaneo
    @Override
    public void handleResult(Result result) {

        String dato_scaneo = result.getText();
        CargarLayoutInicial();
        vista_escaner.stopCamera();
        new Scanner_Factura(dato_scaneo,this);
    }

    @Override public void onFragmentInteraction(Uri uri) {}


}
