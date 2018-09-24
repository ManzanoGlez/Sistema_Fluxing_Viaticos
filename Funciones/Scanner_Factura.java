package manzano.utj.sistemafluxing.Funciones;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import manzano.utj.sistemafluxing.Inicio;
import manzano.utj.sistemafluxing.R;


public class Scanner_Factura{

    private String URL_Factura;
    private int ID_Viaje;

    private Context contexto;
    private Activity activity;

    public Scanner_Factura(String dato,int ID_Viaje, Context contexto, Activity activity) {

        this.contexto = contexto;
        this.activity = activity;

        if(dato.contains("&id=") && dato.contains("&re=") && dato.contains("&rr=") && dato.contains("&tt=")) {

            URL_Factura = dato;
            this.ID_Viaje = ID_Viaje;
            ObtenerInformacionFactura(URL_Factura);

        }else {
            Toast.makeText(contexto, "Codigo QR no es valido", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, Inicio.class);
            activity.startActivity(intent);

        }
    }

    private void ObtenerInformacionFactura(String result) {

         String RFCReceptor=null, RFCEmisor=null,FolioFiscal=null, Monto=null;
         Double Total,SubTotal,Iva;

        String[] ParametrosUrl = result.split("&");
        for (String datos : ParametrosUrl) {

            FolioFiscal = datos.contains("id=") ? datos.substring(3,datos.length()) : FolioFiscal;
            RFCEmisor = datos.contains("re=") ? datos.substring(3,datos.length()) : RFCEmisor;
            RFCReceptor = datos.contains("rr=") ?  datos.substring(3,datos.length()) : RFCReceptor;
            Monto = datos.contains("tt=") ?
                 FormatoMonto("0000000000.##",Double.parseDouble(datos.substring(3,datos.length())) ): Monto;
        }

        Total = Double.parseDouble(Monto);
        Iva = new BigDecimal((Total / 1.16) * 0.16) .setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        SubTotal = new BigDecimal((Total - Iva)).setScale(2, RoundingMode.HALF_EVEN).doubleValue();

        guardarFactura(URL_Factura,FolioFiscal,RFCEmisor,RFCReceptor,Total,Iva,SubTotal);

    }/// Metodo para sacar los valores de la cadena obtenida por el escaner



    private void guardarFactura(final String URL_Factura,final  String FolioFiscal, final String RFCEmisor, final String RFCReceptor,final  Double Total,final Double Iva, final Double SubTotal){



        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(contexto);
        dlgAlert.setTitle("¡Escaneo Exitoso!");
        dlgAlert.setIcon(android.R.drawable.ic_dialog_info);
        dlgAlert.setMessage("¿Deseas guardar esta factura?\n\n" +
                "RFC Emisor : " + RFCEmisor +"\n"+
                "RFC Receptor : " + RFCReceptor +"\n"+
                "Subtotal : $" + SubTotal +"\n"+
                "Iva : $" + Iva +"\n"+
                "Total : $" + Total +"\n"+
                "Folio Fiscal : " + FolioFiscal);
        dlgAlert.setCancelable(false);

        dlgAlert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

      String[] s = { "Avión", "Gasolina", "Autobus ", "UBER", "Alimentos ", "Caseta","Hospedaje ", "Otros"};

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(contexto);
                dlgAlert.setTitle("¡Rubro!");
                dlgAlert.setIcon(android.R.drawable.ic_menu_save);
                dlgAlert.setMessage("¿Seleccióna el rubro al que quieres agregar esta factura?");
                dlgAlert.setCancelable(false);

                ArrayAdapter<String> adp = new ArrayAdapter<String>(activity,R.layout.spinner_item_rubro, s);
                final Spinner sp_Rubro = new Spinner(activity);
                sp_Rubro.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
                sp_Rubro.setAdapter(adp);


                dlgAlert.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        try {
                            PreparedStatement pst;
                            String Query = "exec sp_Captura_Factura ?,?,?,?,?,?,?,?,?,?,?,?;";
                            pst = new SQL_Conexion().ConnectSQL().prepareStatement(Query);

                            pst.setInt(1, new Datos_Locales(contexto).obtenerID());
                            pst.setInt(2, ID_Viaje);
                            pst.setString(3, sp_Rubro.getSelectedItem().toString());
                            pst.setString(4, "3.3");
                            pst.setString(5, "MXN");
                            pst.setDouble(6, SubTotal);
                            pst.setDouble(7, Iva);
                            pst.setDouble(8, Total);
                            pst.setString(9, RFCEmisor);
                            pst.setString(10, "N/a");
                            pst.setString(11, RFCReceptor);
                            pst.setString(12 , new Datos_Locales(contexto).obtenerUsuario());

                            new SQL_Conexion().ConnectSQL().prepareStatement(Query);
                            pst.executeUpdate();

                            Intent intent = new Intent(activity, Inicio.class);
                            activity.startActivity(intent);

                            Toast.makeText(contexto, "Factura agregada correctamente ", Toast.LENGTH_SHORT).show();


                        } catch (SQLException e) {
                            System.out.println("SQL  error : " + e.getMessage());
                        } catch (NullPointerException e) {
                            System.out.println("null error : " + e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Error general : " + e.getMessage());
                        }
                    }
                });

                dlgAlert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Intent intent = new Intent(activity, Inicio.class);
                        activity.startActivity(intent);
                    }
                });

                dlgAlert.setView(sp_Rubro);
                dlgAlert.create().show();
            }
        });

        dlgAlert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
                Intent intent = new Intent(activity, Inicio.class);
                activity.startActivity(intent);
            }
        });
        dlgAlert.create().show();
    }


     private String FormatoMonto(String Formato, double val ) {
         DecimalFormat fm = new DecimalFormat(Formato);
         String salida = fm.format(val);
         salida = salida.replaceFirst ("^0*", "");;
         return salida;
  }





}