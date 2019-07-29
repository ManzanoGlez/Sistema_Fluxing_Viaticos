package app.Emtech.Alesa.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
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
import app.Emtech.Alesa.Functions.Auth;
import app.Emtech.Alesa.StartActivity;
import app.Emtech.Alesa.R;


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
        Button Btn_login = view.findViewById(R.id.Btn_inicioSesion);

        //Todo StartActivity de sesión

        Btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Txt_Email.getText().toString().isEmpty() || !Txt_Email.getText().toString().contains("@")) {
                    Snackbar.make(getView(), "Debes poner un correo valido", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (Txt_password.getText().toString().length() < 5) {
                    Snackbar.make(getView(), "La contraseña debe tener al menos 6 digitos", Snackbar.LENGTH_LONG).show();
                    return;

                }

                // consulta o  servicio para obtener los sig valores
                loginService(Txt_Email.getText().toString(), Txt_password.getText().toString());
            }
        });


        return view;
    }


    public void loginService(final String email, final String password) {


        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = getString(R.string.EndPoint) + "user/login";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String request) {

                        try {

                            JSONObject requestJson = new JSONObject(request);
                              JSONObject success = requestJson.getJSONObject("success");

                         JSONObject user = success.getJSONObject("user");

                            String id = user.getString("id");
                            String name = user.getString("name");
                            String email = user.getString("email");
                            String api_token = success.getString("api_token");

                      new Auth(getContext()).setAuth(id, name, email, api_token);
                            Intent intent = new Intent(getActivity(), StartActivity.class);
                            getActivity().startActivity(intent);


                        } catch (Exception e) {
                            Log.e("JSON RESPONSE", e.getMessage());
                        }


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
                            Snackbar.make(getView(), errorText, Snackbar.LENGTH_LONG).show();
                            System.out.println(errorText);
                        }

                        if (networkResponse.statusCode == HttpStatus.SC_UNPROCESSABLE_ENTITY) {
                            // HTTP Status Code: 422 Unauthorized
                            JSONObject errorData = errorJson.getJSONObject("error");
                            if (errorData.has("email")) {
                                String errorMessage = errorData.getJSONArray("email").get(0).toString();
                                Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
                                System.out.println(errorMessage);
                            }

                            if (errorData.has("password")) {
                                String errorMessage = errorData.getJSONArray("password").get(0).toString();
                                Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG).show();
                                System.out.println(errorMessage);
                            }


                        }


                        if (networkResponse.statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR || networkResponse.statusCode == HttpStatus.SC_NOT_FOUND) {
                            // HTTP Status Code: 500 Unauthorized
                            Snackbar.make(getView(), "Revisa tu conexión a Internet", Snackbar.LENGTH_LONG).show();
                            System.out.println("Revisa tu conexión a Internet");
                        }

                    }

                } catch (Throwable t) {
                    Log.e("JSON RESPONSE", "Could not parse malformed JSON: " + jsonError);
                }

            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

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
