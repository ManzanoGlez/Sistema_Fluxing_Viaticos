package manzano.utj.sistemafluxing.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import manzano.utj.sistemafluxing.Funciones.Scanner_Factura;
import manzano.utj.sistemafluxing.Inicio;
import manzano.utj.sistemafluxing.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class EscanerFragment extends Fragment implements  ZXingScannerView.ResultHandler {

    private ZXingScannerView vista_escaner;

    private OnFragmentInteractionListener mListener;

    public EscanerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_escaner, container, false);

        //Escanear
        FloatingActionButton fab = view.findViewById(R.id.Btn_Scanner);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Escanear();
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

        new Scanner_Factura(dato_scaneo,getContext());

        Intent intent = new Intent(getActivity(), Inicio.class);
        getActivity().startActivity(intent);

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

