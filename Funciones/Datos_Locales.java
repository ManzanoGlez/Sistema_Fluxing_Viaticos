package manzano.utj.sistemafluxing.Funciones;

import android.content.Context;
import android.content.SharedPreferences;

public class Datos_Locales{


    private Context contexto;

    public Datos_Locales(Context contexto) {
        this.contexto = contexto;
    }



    public Boolean ComprobarUsuario() {
        SharedPreferences preferences = contexto.getSharedPreferences("DataUsuario", Context.MODE_PRIVATE);
        String Usuario = preferences.getString("Usuario", "Usuario no registrado");

        System.out.println("El valor del usuario : " + Usuario);

        if (Usuario.equals("Usuario no registrado")) {
        return false;
        }
        return true;

    } // Comprueba si existe usuario si no pide registro


    public void AgregarUsuario(String Usuario) {
        // Button btn = v.findViewById(R.id.);






        SharedPreferences preferences = contexto.getSharedPreferences("DataUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Usuario", Usuario);
        editor.apply();
    }// Agrega el nombre con el que haces los registros



    public void EliminarUsuario(String Usuario) {
        // Button btn = v.findViewById(R.id.);
        SharedPreferences preferences = contexto.getSharedPreferences("DataUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("Usuario");
        editor.apply();
    }// Agrega el nombre con el que haces los registros




}
