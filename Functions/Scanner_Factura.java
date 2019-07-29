package app.Emtech.Alesa.Functions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.Emtech.Alesa.R;
import app.Emtech.Alesa.StartActivity;


public class Scanner_Factura {


    private Context context;
    private Activity activity;

    public Scanner_Factura(String scanner_text, Context context, Activity activity) {

        this.context = context;
        this.activity = activity;

        // ObtenerInformacionFactura(URL_Factura);

        confirmScanner(scanner_text);

    }


    private void confirmScanner(final String Scanner_text) {

        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
        dlgAlert.setTitle("¡Escaneo Exitoso!");
        dlgAlert.setIcon(android.R.drawable.ic_dialog_info);
        dlgAlert.setMessage(Scanner_text);
        dlgAlert.setCancelable(false);

        dlgAlert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                getInfoBarcode(Scanner_text);


            }
        });

        dlgAlert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
                Intent intent = new Intent(activity, StartActivity.class);
                activity.startActivity(intent);
            }
        });
        dlgAlert.create().show();
    }


    private void getInfoBarcode(final String code) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = context.getString(R.string.EndPoint) + "item/scan";

        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("JSON RESPONSE", response);
                try {

                    JSONObject requestJson = new JSONObject(response);
                    JSONObject success = requestJson.getJSONObject("success");

                    int receipt_id  = success.getInt("id");
                    String name_receipt = success.getString("name");
                    String agent = success.getString("agent");
                    String provider = success.getString("provider");
                    String direction = success.getString("direction");
                    String city = success.getString("city");
                    String telephone = success.getString("telephone");
                    String email = success.getString("email");
                    String total = success.getString("total");
                    String observations = success.getString("observations");
                    String type_receipt = success.getString("type_receipt");
                    String MSI = success.getString("MSI");
                    String status = success.getString("status");
                    int complete = success.getInt("complete");

                    JSONObject item = success.getJSONObject("Item");

                    int item_id  = item.getInt("id");
                    String warehouse  = item.getString("warehouse");
                    String status_item  = item.getString("status");
                    int active  = item.getInt("active");
                    String barcode  = item.getString("barcode");

                    JSONObject product = item.getJSONObject("product");

                    int product_id  = product.getInt("id");
                    String name_product  = product.getString("name");
                    String description  = product.getString("description");
                    String model  = product.getString("model");
                    String price  = product.getString("price");
                    String category  = product.getString("category");
                    int active_product  = product.getInt("active");



            Toast.makeText(context, "Producto agregado correctamente ", Toast.LENGTH_SHORT).show();




                } catch (Exception e) {

                    Toast.makeText(context, "Error al leer codigo, intentelo mas tarde. ", Toast.LENGTH_SHORT).show();

                    Log.e("JSON RESPONSE", e.getMessage());
                }

                Intent intent = new Intent(activity, StartActivity.class);
                activity.startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("VOLLEY_onErrorResponse", error.toString());

                String jsonError = null;

                try {

                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {

                        jsonError = new String(networkResponse.data);

                        JSONObject errorJson = new JSONObject(jsonError);

                        if (networkResponse.statusCode == HttpStatus.SC_BAD_REQUEST) {
                            // HTTP Status Code: 400 Unauthorized
                            String errorText = (String) errorJson.get("error");
                            System.out.println(errorText);
                            Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show();
                        }
                        if (networkResponse.statusCode == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
                            // HTTP Status Code: 422 Unauthorized
                            JSONObject errorData = errorJson.getJSONObject("error");

                            if (errorData.has("codigo")) {
                                String errorMessage = errorData.getJSONArray("codigo").get(0).toString();
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                                System.out.println(errorMessage);
                            }
                        }
                        if (networkResponse.statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR || networkResponse.statusCode == HttpStatus.SC_NOT_FOUND) {
                            // HTTP Status Code: 500 Unauthorized
                            System.out.println("Revisa tu conexión a Internet");
                            Toast.makeText(context, "Revisa tu conexión a Internet", Toast.LENGTH_SHORT).show();

                        }
                    }
                } catch (Throwable t) {
                    Log.e("JSON RESPONSE", "Could not parse malformed JSON: " + jsonError);
                }

                Intent intent = new Intent(activity, StartActivity.class);
                activity.startActivity(intent);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("codigo", code);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + new Auth(context).getToken());
                params.put("Accept", "application/json");
                return params;
            }
        };

        queue.add(sr);



    }

}