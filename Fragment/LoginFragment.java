package manzano.utj.sistemafluxing.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import manzano.utj.sistemafluxing.Funciones.Datos_Locales;
import manzano.utj.sistemafluxing.Funciones.SQL_Conexion;
import manzano.utj.sistemafluxing.Inicio;
import manzano.utj.sistemafluxing.R;


public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        System.out.println("Entra login");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        final EditText Txt_Email = view.findViewById(R.id.Txt_Email);
        final EditText Txt_password = view.findViewById(R.id.Txt_password);
        Button Btn_inicioSesion = view.findViewById(R.id.Btn_inicioSesion);

        //Todo Inicio de sesión
        Btn_inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar snackbar;
                SQL_Conexion sql = new SQL_Conexion();

                if (!sql.Validate_Connection()) {

                    snackbar = Snackbar.make(getView(), "Sin conexion a internet, intente mas tarde", Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else {

        if(!Txt_Email.getText().toString().equals("1") && !Txt_Email.getText().toString().equals("1")) { // 1 1 para pruebas rapidas

             if (Txt_Email.getText().toString().isEmpty() || !Txt_Email.getText().toString().contains("@")) {
                 snackbar = Snackbar.make(getView(), "Debes poner un correo valido", Snackbar.LENGTH_LONG);
                 snackbar.show();
                 return;
             }

             if (Txt_password.getText().toString().length() < 5) {
                 snackbar = Snackbar.make(getView(), "La contraseña debe tener al menos 6 digitos", Snackbar.LENGTH_LONG);
                 snackbar.show();
                 return;

             }
        }
                   try {

                        ResultSet rs;
                        PreparedStatement pst;

                          String Query = "exec sp_Login_Usuarios ?,?;";
                        pst = sql.ConnectSQL().prepareStatement(Query);


                        pst.setString(1, Txt_Email.getText().toString());
                        pst.setString(2, Txt_password.getText().toString());
                        sql.ConnectSQL().prepareStatement(Query);
                        rs = pst.executeQuery();

                        while (rs.next()) {

                            if (rs.getString(1).equals("FALSE")) {

                                snackbar = Snackbar.make(getView(), "Cuenta no encontrada", Snackbar.LENGTH_LONG);
                                snackbar.show();

                            }else{

               Toast.makeText(getContext(), "Bienvenido " + rs.getString(2), Toast.LENGTH_SHORT).show();
                String correo = rs.getString(14);

                  new Datos_Locales(getContext()).AgregarUsuario(rs.getString(2).trim());

                                Intent intent = new Intent(getActivity(), Inicio.class);
                                getActivity().startActivity(intent);

                            }
                        }



                    } catch (SQLException e) {
                        System.out.println("SQL  error : " + e.getMessage());
                    } catch (NullPointerException e) {
                        System.out.println("null error : " + e.getMessage());

                } catch (Exception e) {
                    System.out.println("Error general : " + e.getMessage());
                }

          }
            }
        });




     return view;
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
