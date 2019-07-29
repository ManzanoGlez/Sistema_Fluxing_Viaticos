package app.Emtech.Alesa.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.zxing.Result;
import app.Emtech.Alesa.Functions.Scanner_Factura;
import app.Emtech.Alesa.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class DetailReceiptFragment extends Fragment implements ZXingScannerView.ResultHandler,
        SwipeRefreshLayout.OnRefreshListener {

    private ZXingScannerView scannerView;
    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout swipeRefreshLayout;

    public DetailReceiptFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_receipt, container, false);

        //Refrescar
        swipeRefreshLayout = view.findViewById(R.id.refreshSwipe);
        swipeRefreshLayout.setOnRefreshListener(this);

     //   getReceipts();

        //Boton escaner
        FloatingActionButton fab = view.findViewById(R.id.Btn_Scanner);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scanner();
            }
        });

        return view;
    }

    // Escaner
    public void scanner() {
        scannerView = new ZXingScannerView(getContext());
        scannerView.setResultHandler(this);
        getActivity().setContentView(scannerView);
        scannerView.startCamera();
    }

    // Recibe el resultado del escaneo y lo guarda en la var dato_scaneo
    @Override
    public void handleResult(Result result) {

        String resultText = result.getText();
        scannerView.stopCamera();

        new Scanner_Factura(resultText, getContext(), getActivity());
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new DetailReceiptFragment()).commit();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
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

