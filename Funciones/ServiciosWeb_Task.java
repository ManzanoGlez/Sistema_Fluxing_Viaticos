package manzano.utj.sistemafluxing.Funciones;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;

public class ServiciosWeb_Task extends AsyncTask<Void,Void,String> {

    private Context httpContext;//contexto
    private String resultado_API ="";
    private String linkrequestAPI="";
    private JSONObject jsonObject;
    private AsyncResponse delegate;

    //constructor del hilo (Asynctask)
    public ServiciosWeb_Task(Context ctx, String linkAPI, JSONObject jsonObject,AsyncResponse delegate){

        this.httpContext=ctx;
        this.linkrequestAPI=linkAPI;
        this.jsonObject = jsonObject;
        this.delegate = delegate;

    }

    public interface AsyncResponse {
        void processFinish(String output);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

     @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        resultado_API =s;
             Toast.makeText(httpContext, resultado_API,Toast.LENGTH_LONG).show();
         delegate.processFinish(resultado_API);

    }


    @Override
    protected String doInBackground(Void... params) {
        String result= null;

        String wsURL = linkrequestAPI;
        try {
            // se crea la conexion al api: http://localhost:15009/WEBAPIREST/api/persona
            URL url = new URL(wsURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //crear el objeto json para enviar por POST
            JSONObject parametrosPost = jsonObject;

            //DEFINIR PARAMETROS DE CONEXION
            urlConnection.setReadTimeout(15000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");// se puede cambiar por delete ,put ,etc
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);


            //OBTENER EL RESULTADO DEL REQUEST
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(parametrosPost));
            writer.flush();
            writer.close();
            os.close();

            int responseCode=urlConnection.getResponseCode();// conexion OK?
            if(responseCode== HttpURLConnection.HTTP_OK){
                BufferedReader in= new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuffer sb= new StringBuffer("");
                String linea="";
                while ((linea=in.readLine())!= null){
                    sb.append(linea);
                    break;

                }
                in.close();
                result= sb.toString();
            }
            else{
                result= new String("Error: "+ responseCode);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  result;
    }


    //Transformar JSON Obejct a String
    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }

}


/* Copiar en activity donde quieras consumir servicios



   1-

// implementas asyctesponse para guardar el resultado del servicio en la activity
 implements ServiciosWeb_Task.AsyncResponse





   2-

// Ejecuta metodo consumirwebservice  aqui envias parametros y url



try{
 String url = getString(R.string.URL_API) + getString(R.string.sw_login);
    System.out.println(url);
        JSONObject parametrosPost = new JSONObject();
        parametrosPost.put("correo", "yeyo@correo.com");
        parametrosPost.put("contrasena", "123");
        parametrosPost.put("info_dispositivo", "webservice");
        consumirWebService(url,parametrosPost);
    }catch (Exception e){
        Toast.makeText(this, "No se pudo ejecutar el servicio web", Toast.LENGTH_SHORT).show();
        System.out.println(e.getMessage());
       }



    3-

    //Consume servicio web


    public void consumirWebService(String url,JSONObject parametros){
        new ServiciosWeb_Task(getApplicationContext(), url, parametros,this).execute();
    }
    // Recibe el resultado del webservice
    @Override
    public void processFinish(String json){

        try {
            JSONObject obj = new JSONObject(json);
            JSONObject jsonobj = new JSONObject(URLDecoder.decode(obj.toString(),"UTF-8"));
            JSONArray jsonarray = jsonobj.getJSONArray("data");
            for(int i = 0;i<jsonarray.length();i++){
                String correo = jsonarray.getJSONObject(i).getString("correo");
                System.out.println(correo);
            }
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }
    }



// FIN Consume servicio web


*/