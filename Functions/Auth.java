package app.Emtech.Alesa.Functions;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.Emtech.Alesa.R;

public class Auth {


    private Context context;
    private JSONObject Auth;


    public Auth(Context context) {
        this.context = context;
    }

    public Boolean checkAuth() {
        SharedPreferences preferences = context.getSharedPreferences("authData", Context.MODE_PRIVATE);


        String id = preferences.getString("id", "false");
        String name = preferences.getString("name", "false");
        String email = preferences.getString("email", "false");
        String api_token = preferences.getString("api_token", "false");

        System.out.println("Usuario activo { id:" + id + ", Nombre: " + name + ",Correo :" + email + "}");

        return !id.equals("false") || !name.equals("false") || !email.equals("false") || !api_token.equals("false");

    } // Comprueba si existe usuario si no pide registro

    public void setAuth(String id, String name, String email, String api_token) {
        SharedPreferences preferences = context.getSharedPreferences("authData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("id", id);
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("api_token", api_token);
        editor.apply();
    }

    public void logout() {
        SharedPreferences preferences = context.getSharedPreferences("authData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("id");
        editor.remove("name");
        editor.remove("email");
        editor.remove("api_token");
        editor.apply();
    }

    public JSONObject getAuth() {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = context.getString(R.string.EndPoint) + "user/getAuth";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject requestJson = new JSONObject(response);
                            Auth = requestJson.getJSONObject("success");
                        } catch (Throwable t) {
                            Log.e("JSON RESPONSE", "Could not parse malformed JSON: " + t.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                        Auth = null;
                        logout();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + getToken());
                params.put("Accept", "application/json");


                return params;
            }
        };
        queue.add(postRequest);

        return Auth;
    }


    public void updateAuth() {

    }


    public String getToken() {
        SharedPreferences preferences = context.getSharedPreferences("authData", Context.MODE_PRIVATE);

        return preferences.getString("api_token", "NO TOKEN");
    }


}
