package manzano.utj.sistemafluxing.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manzano.utj.sistemafluxing.Funciones.Datos_Locales;
import manzano.utj.sistemafluxing.Funciones.RecyclerViewAdaptador;
import manzano.utj.sistemafluxing.Funciones.SQL_Conexion;
import manzano.utj.sistemafluxing.Funciones.Scanner_Factura;
import manzano.utj.sistemafluxing.Inicio;
import manzano.utj.sistemafluxing.Modelos.Factura;
import manzano.utj.sistemafluxing.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class EscanerFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView vista_escaner;
    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerViewFactura;
    private RecyclerViewAdaptador adaptadorFactuta;
    int ID_Viaje;

    public EscanerFragment() {
        // Required empty public constructor
    }


    public List<Factura> obtenerFactutas( View v){
        List<Factura> facturas = new ArrayList<>();

        try {

            ResultSet rs;
            PreparedStatement pst;

            String Query = "SELECT RFC_Emisor,RFC_Receptor,Subtotal,Iva,Total,Rubro,ID_Factura FROM FACTURA WHERE ID_Empleado = ? order by ID_Factura desc";
            pst = new SQL_Conexion().ConnectSQL().prepareStatement(Query);
            pst.setInt(1, new Datos_Locales(getContext()).obtenerID());

            new SQL_Conexion().ConnectSQL().prepareStatement(Query);
            rs = pst.executeQuery();

            while (rs.next()) {

                facturas.add(new Factura(

                        rs.getString(1)+"",
                        rs.getString(2)+"",
                        "Subtotal: $"+rs.getString(3)+
                                "\nIVA: $" +rs.getString(4)+
                                "\nTotal:$" +rs.getString(5),
                        rs.getString(6)+""));

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

        return facturas;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_escaner, container, false);

        recyclerViewFactura = view.findViewById(R.id.recyclerFactura);
        recyclerViewFactura.setLayoutManager(new LinearLayoutManager(getContext()));

        adaptadorFactuta = new RecyclerViewAdaptador(obtenerFactutas(view));
        recyclerViewFactura.setAdapter(adaptadorFactuta);

        //Escanear
        FloatingActionButton fab = view.findViewById(R.id.Btn_Scanner);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    ResultSet rs;
                    PreparedStatement pst;

                    String Query = "exec sp_Verificar_Viaje ?;";
                    pst = new SQL_Conexion().ConnectSQL().prepareStatement(Query);

                    pst.setInt(1, new Datos_Locales(getContext()).obtenerID());

                    new SQL_Conexion().ConnectSQL().prepareStatement(Query);
                    rs = pst.executeQuery();

                    while (rs.next()) {

                        if (rs.getString(1).equals("Sin tiene viajes activos")) {
                            Snackbar snackbar = Snackbar.make(getView(), "Solo puedes escanear si tienes un viaje activo", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            ID_Viaje = rs.getInt(2);
                            Escanear();
                        }
                    }

                } catch (SQLException e) {
                    System.out.println("SQL  error : " + e.getMessage());
                    Snackbar snackbar = Snackbar.make(getView(), "Sin conexión, intenta mas tarde", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } catch (NullPointerException e) {
                    System.out.println("null error : " + e.getMessage());
                    Snackbar snackbar = Snackbar.make(getView(), "Sin conexión, intenta mas tarde", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } catch (Exception e) {
                    System.out.println("Error general : " + e.getMessage());
                    Snackbar snackbar = Snackbar.make(getView(), "Sin conexión, intenta mas tarde", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        return view;
    }

    // Escaner
    public void Escanear() {
        vista_escaner = new ZXingScannerView(getContext());
        vista_escaner.setResultHandler(this);
        getActivity().setContentView(vista_escaner);
        vista_escaner.startCamera();
    }

    // Recibe el resultado del escaneo y lo guarda en la var dato_scaneo
    @Override
    public void handleResult(Result result) {

        String dato_scaneo = result.getText();
        vista_escaner.stopCamera();

        new Scanner_Factura(dato_scaneo, ID_Viaje, getContext(), getActivity());

    }

    //Cosas del fragmento
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
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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

