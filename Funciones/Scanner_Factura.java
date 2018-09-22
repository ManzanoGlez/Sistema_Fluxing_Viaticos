package manzano.utj.sistemafluxing.Funciones;

import android.content.Context;
import android.widget.Toast;

import com.google.zxing.Result;

import java.text.DecimalFormat;

import manzano.utj.sistemafluxing.Fragment.EscanerFragment;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class Scanner_Factura{


    private String URL_Factura;
    private String RFCReceptor = null;
    private String RFCEmisor = null;
    private String Monto = null;
    private String FolioFiscal = null;






    public Scanner_Factura(String dato, Context contexto) {


        if(dato.contains("&id=") && dato.contains("&re=") && dato.contains("&rr=") && dato.contains("&tt=")) {

            URL_Factura = dato;
            SacarDatos(URL_Factura);
        } else {
            Toast.makeText(contexto, "Codigo QR no es valido", Toast.LENGTH_SHORT).show();
        }



    }

    private void SacarDatos(String result) {


        String[] ParametrosUrl = result.split("&");
        for (String datos : ParametrosUrl) {

            FolioFiscal = datos.contains("id=") ? datos.substring(3,datos.length()) : FolioFiscal;
            RFCEmisor = datos.contains("re=") ? datos.substring(3,datos.length()) : RFCEmisor;
            RFCReceptor = datos.contains("rr=") ?  datos.substring(3,datos.length()) : RFCReceptor;
            Monto = datos.contains("tt=") ?
                 FormatoMonto("0000000000.##",Double.parseDouble(datos.substring(3,datos.length())) ): Monto;

        }

        System.out.println(URL_Factura);
        System.out.println(FolioFiscal);
        System.out.println(RFCEmisor);
        System.out.println(RFCReceptor);
        System.out.println(Monto);

    }/// Metodo para sacar los valores de la cadena obtenida por el escaner


     private String FormatoMonto(String Formato, double val ) {
         DecimalFormat fm = new DecimalFormat(Formato);
         String salida = fm.format(val);
         salida = salida.replaceFirst ("^0*", "");;
         return salida;
  }


}