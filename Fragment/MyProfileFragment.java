package app.Emtech.Alesa.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.Emtech.Alesa.Functions.SQL_Conexion;
import app.Emtech.Alesa.Models.Employee;
import app.Emtech.Alesa.R;

public class MyProfileFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    static EditText txt_nombres, txt_apellidos, txt_nss,txt_contacto,txt_rfc,
            txt_fecha_nacimiento, txt_direccion, txt_correo,txt_puesto, txt_area,
            txt_fecha_ingreso,txt_tipo_cuenta,txt_contrasena;

    static Button btn_guardar,btn_editar;

    private String contrasena = null;
    private String curp = null;
    private String salario = null;
    private boolean habilitado = false;

    public MyProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_profile, container, false);


        txt_nombres = v.findViewById(R.id.txt_nombres);
        txt_apellidos = v.findViewById(R.id.txt_apellidos);
        txt_nss = v.findViewById(R.id.txt_nss);
        txt_direccion = v.findViewById(R.id.txt_direccion);
        txt_contacto = v.findViewById(R.id.txt_contacto);
        txt_rfc = v.findViewById(R.id.txt_rfc);
        txt_fecha_nacimiento = v.findViewById(R.id.txt_fecha_nacimiento);

        txt_puesto = v.findViewById(R.id.txt_puesto);
        txt_area = v.findViewById(R.id.txt_area);
        txt_fecha_ingreso = v.findViewById(R.id.txt_fecha_ingreso);

        txt_correo = v.findViewById(R.id.txt_correo);
        txt_tipo_cuenta = v.findViewById(R.id.txt_tipo_cuenta);
        txt_contrasena = v.findViewById(R.id.txt_contrasena);

        btn_guardar= v.findViewById(R.id.btn_guardar);
        btn_editar = v.findViewById(R.id.btn_editar);


        //Desactiva todos los editetxt
        this.setEnableForm(habilitado);

        //Habilita el formulario
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!habilitado){

                    habilitado = true;
                    setEnableForm(habilitado);
                    btn_editar.setText("Cancelar");

                }else{

                    habilitado = false;
                    setEnableForm(habilitado);
                    btn_editar.setText("Editar");
                }

            }
        });


        //Guarda el formulario
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    ResultSet rs;
                    PreparedStatement pst;

                    String Query = Employee.Query_Update;

                    pst = new SQL_Conexion().ConnectSQL().prepareStatement(Query);

                  //  pst.setInt(1,  new Auth(getContext()).getToken());
                    pst.setString(2,txt_nss.getText().toString());
                    pst.setString(3,txt_nombres.getText().toString());
                    pst.setString(4,txt_apellidos.getText().toString());
                    pst.setString(5,txt_direccion.getText().toString());
                    pst.setString(6,txt_contacto.getText().toString());
                    pst.setString(7,txt_rfc.getText().toString().toUpperCase());
                    pst.setString(8, getFormatDate(txt_fecha_nacimiento.getText().toString()));
                    pst.setString(9,curp.toUpperCase());
                    pst.setString(10,txt_correo.getText().toString());
                    pst.setString(11,getFormatDate(txt_fecha_ingreso.getText().toString()));
                    pst.setString(12,txt_puesto.getText().toString());
                    pst.setString(13,txt_area.getText().toString());
                    pst.setString(14,salario);

                    if (txt_contrasena.getText().length() == 0) {
                        pst.setString(15,contrasena);
                    }else{
                        pst.setString(15,txt_contrasena.getText().toString());
                    }

                    pst.setString(16,txt_tipo_cuenta.getText().toString());

                    new SQL_Conexion().ConnectSQL().prepareStatement(Query);
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        Snackbar snackbar;

                        if(rs.getString(1).equals("No se cambio contraseña") ||rs.getString(1).equals("Se cambio contraseña") ){
                             snackbar = Snackbar.make(v,"Cambios guardados" , Snackbar.LENGTH_LONG);
                        }else{
                            snackbar = Snackbar.make(v,"Ups,hubo un problema al guardar cambios, intenta mas tarde" , Snackbar.LENGTH_LONG);
                        }

                        habilitado = false;
                        setEnableForm(habilitado);
                        snackbar.show();


                    }
                } catch (SQLException e) {
                    System.out.println("SQL  error : " + e.getMessage());
                    Snackbar snackbar = Snackbar.make(v, "Sin conexión, intenta mas tarde", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } catch (NullPointerException e) {
                    System.out.println("null error : " + e.getMessage());
                    Snackbar snackbar = Snackbar.make(v, "Sin conexión, intenta mas tarde", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } catch (Exception e) {
                    System.out.println("Error general : " + e.getMessage());

                }


            }
        });


        //Lista la información del perfil
        try{
            ResultSet rs;
            PreparedStatement pst;

            String Query = Employee.Query_Select_Where;

            pst = new SQL_Conexion().ConnectSQL().prepareStatement(Query);
          //  pst.setInt(1,  new Auth(getContext()).obtenerID());

            new SQL_Conexion().ConnectSQL().prepareStatement(Query);
            rs = pst.executeQuery();

            while (rs.next()) {

                txt_nss.setText(rs.getString(1));
                txt_nombres.setText(rs.getString(2));
                txt_apellidos.setText(rs.getString(3));
                txt_direccion.setText(rs.getString(4));
                txt_contacto.setText(rs.getString(5));
                txt_rfc.setText(rs.getString(6).toUpperCase());
                txt_fecha_nacimiento.setText( rs.getString(7));
                curp = rs.getString(8).toUpperCase();
                txt_correo.setText(rs.getString(9));

                txt_puesto.setText(rs.getString(11));
                txt_area.setText(rs.getString(12));
                salario = rs.getString(13);
                txt_fecha_ingreso.setText(rs.getString(10));

                txt_tipo_cuenta.setText(rs.getString(15));
                contrasena = rs.getString(14);

            }
        } catch (SQLException e) {
            System.out.println("SQL  error : " + e.getMessage());
            Snackbar snackbar = Snackbar.make(v, "Sin conexión, intenta mas tarde", Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (NullPointerException e) {
            System.out.println("null error : " + e.getMessage());
            Snackbar snackbar = Snackbar.make(v, "Sin conexión, intenta mas tarde", Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (Exception e) {
            System.out.println("Error general : " + e.getMessage());

        }


        return v;
    }


    public static String getFormatDate(String textDate){

       String part[] = textDate.split("/");

        return  part[2]+ "-"+ part[0]+ "-"+ part[1];
    }

    public static void setEnableForm(Boolean active){

        txt_nss.setEnabled(false);
        txt_nombres.setEnabled(active);
        txt_apellidos.setEnabled(active);
        txt_direccion.setEnabled(active);
        txt_contacto.setEnabled(active);
        txt_rfc.setEnabled(false);
        txt_fecha_nacimiento.setEnabled(false);

        txt_puesto.setEnabled(false);
        txt_area.setEnabled(false);
        txt_fecha_ingreso.setEnabled(false);

        txt_correo.setEnabled(false);
        txt_tipo_cuenta.setEnabled(false);
        txt_contrasena.setEnabled(active);

        btn_guardar.setVisibility( active ? View.VISIBLE :  View.GONE );


    }

    // TODO: Rename method, update argument and hook method into UI event
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
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
