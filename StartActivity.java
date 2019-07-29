package app.Emtech.Alesa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import app.Emtech.Alesa.Fragment.DetailReceiptFragment;
import app.Emtech.Alesa.Fragment.ReceiptsFragment;
import app.Emtech.Alesa.Fragment.LoginFragment;
import app.Emtech.Alesa.Fragment.MyProfileFragment;
import app.Emtech.Alesa.Functions.Auth;

public class StartActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        LoginFragment.OnFragmentInteractionListener,
        DetailReceiptFragment.OnFragmentInteractionListener,
        ReceiptsFragment.OnFragmentInteractionListener,
        MyProfileFragment.OnFragmentInteractionListener {

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadMainLayout();
        requestPermission();
        checkSession();
    }


    public void checkSession() {
        if (!new Auth(this).checkAuth()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment()).commit();
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ReceiptsFragment()).commit();
        }
    }

    public void loadMainLayout() {

        setContentView(R.layout.burger_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void requestPermission() {//Permiso de camara

        /*Pide el permiso la primera vez que abre la app*/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            System.out.println("Permiso Activado");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            Toast.makeText(this, "La aplicación no podra funcionar con normalidad sin el permiso de la camara.", Toast.LENGTH_SHORT).show();
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
        } catch (NullPointerException e) {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
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
        if (id == R.id.btn_menu_logout) {

            new Auth(this).logout();
            checkSession();
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
        Fragment fragment = null;

        if (id == R.id.nav_scaner) {
             fragment = new ReceiptsFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //Fin  Menu hamburguesa

    @Override
    public void onFragmentInteraction(Uri uri) {}


}
