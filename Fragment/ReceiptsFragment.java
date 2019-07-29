package app.Emtech.Alesa.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.Emtech.Alesa.Functions.Auth;
import app.Emtech.Alesa.Functions.RecyclerView_ReceiptAdapter;
import app.Emtech.Alesa.Models.Receipts;
import app.Emtech.Alesa.R;


public class ReceiptsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private RecyclerView_ReceiptAdapter recyclerView_receiptAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    List<Receipts> receipts = new ArrayList<>();
    LinearLayoutManager manager;

    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;

    public ReceiptsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_receipts, container, false);

        //Progress bar
        progressBar = view.findViewById(R.id.progressBar);

        //Refrescar
       // swipeRefreshLayout = view.findViewById(R.id.refreshSwipe);
      //  swipeRefreshLayout.setOnRefreshListener(this);

        manager = new LinearLayoutManager(getContext());

        //Tarjetas de receipts
        recyclerView = view.findViewById(R.id.recyclerReceipts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems   =   container.getChildCount();
                totalItems     =   manager.getItemCount();
                scrollOutItems =   manager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems)){
                    //fetch

                    isScrolling = false;

                    getNextPageReceipts();

                    System.out.println("pedir mas ");

                }
            }
        });

        getReceipts();

        return view;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new ReceiptsFragment()).commit();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
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

    private void getReceipts() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = getContext().getString(R.string.EndPoint) + "receipt/show";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject success = response.getJSONObject("success");

                            //Paginate
                            int current_page = success.getInt("current_page");
                            String first_page_url = success.getString("first_page_url");
                            int from = success.getInt("from");
                            int last_page = success.getInt("last_page");
                            String last_page_url = success.getString("last_page_url");
                            String next_page_url = success.getString("next_page_url");
                            int per_page = success.getInt("per_page");
                            String prev_page_url = success.getString("prev_page_url");
                            int to = success.getInt("to");
                            int total = success.getInt("total");

                            //Data
                            JSONArray data = success.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {

                                JSONObject receipt_item = data.getJSONObject(i);

                                //System.out.println(receipt_item.toString());

                                int id = receipt_item.getInt("id");
                                String name = receipt_item.getString("name");
                                String provider = receipt_item.getString("provider");
                                String type_receipt = receipt_item.getString("type_receipt");
                                String status = receipt_item.getString("status");
                                JSONArray Items = receipt_item.getJSONArray("items");

                                receipts.add(new Receipts(
                                        id,
                                        name,
                                        provider,
                                        type_receipt,
                                        status,
                                        Items));
                            }


                            recyclerView_receiptAdapter = new RecyclerView_ReceiptAdapter(receipts);
                            recyclerView.setAdapter(recyclerView_receiptAdapter);

                        } catch (Exception e) {
                            Log.e("JSON RESPONSE", e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + new Auth(getContext()).getToken());
                params.put("Accept", "application/json");


                return params;
            }
        };
        queue.add(request);
    }

    private void getNextPageReceipts(){
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("Pido mas registros");
                receipts.add(new Receipts(
                        123,
                        "123",
                        "!",
                        "123",
                        "123",
                        new JSONArray()));

                recyclerView_receiptAdapter.notifyDataSetChanged();

              //  progressBar.setVisibility(View.GONE);
            }
        }, 5000);
    }

}

