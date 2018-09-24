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
        String Correo = preferences.getString("Correo", "Correo no registrado");
        String ID_Empleado = preferences.getString("ID", "ID no registrado");

        System.out.println("Usuario activo { ID :" + ID_Empleado +" Usuario activo  : " + Usuario + " Correo :" + Correo +"}");

        return Usuario.equals("Usuario no registrado") && Correo.equals("Correo no registrado") && ID_Empleado.equals("ID no registrado")
                 ? false:true;
    } // Comprueba si existe usuario si no pide registro


    public void AgregarUsuario(String Usuario,String Correo,String id) {
        // Button btn = v.findViewById(R.id.);
        SharedPreferences preferences = contexto.getSharedPreferences("DataUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Usuario", Usuario);
        editor.putString("Correo", Correo);
        editor.putString("ID", id);
        editor.apply();
    }// Agrega el nombre con el que haces los registros


    public void EliminarUsuario() {
        SharedPreferences preferences = contexto.getSharedPreferences("DataUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("Usuario");
        editor.remove("Correo");
        editor.remove("ID");
        editor.apply();
    }


    public int obtenerID() {
        SharedPreferences preferences = contexto.getSharedPreferences("DataUsuario", Context.MODE_PRIVATE);
         return Integer.parseInt(preferences.getString("ID", "ID no registrado"));
    }

    public String obtenerUsuario() {
        SharedPreferences preferences = contexto.getSharedPreferences("DataUsuario", Context.MODE_PRIVATE);
        return preferences.getString("Usuario", "Usuario no registrado");
    }

    public String obtenerCorreo() {
        SharedPreferences preferences = contexto.getSharedPreferences("DataUsuario", Context.MODE_PRIVATE);
        return preferences.getString("Correo", "Correo no registrado");
    }


}
