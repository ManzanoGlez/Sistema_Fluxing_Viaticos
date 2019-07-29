package app.Emtech.Alesa.Functions;


import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import app.Emtech.Alesa.Fragment.DetailReceiptFragment;
import app.Emtech.Alesa.Models.Receipts;
import app.Emtech.Alesa.R;

public class RecyclerView_ReceiptAdapter extends RecyclerView.Adapter<RecyclerView_ReceiptAdapter.ViewHolder> {

    private List<Receipts> receiptsList;
    private View view;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_folio, txt_driver, txt_agent, txt_type, txt_status;

        private ViewHolder(View itemView) {
            super(itemView);

            txt_folio = itemView.findViewById(R.id.txt_folio);
            txt_driver = itemView.findViewById(R.id.txt_driver);
            txt_agent = itemView.findViewById(R.id.txt_agent);
            txt_type = itemView.findViewById(R.id.txt_type);
            txt_status = itemView.findViewById(R.id.txt_status_receipt);
        }
    }

    public RecyclerView_ReceiptAdapter(List<Receipts> receiptsList) {
        this.receiptsList = receiptsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receipt, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.txt_folio.setText(String.valueOf(receiptsList.get(position).getId()));
        holder.txt_driver.setText(receiptsList.get(position).getDriver());
        holder.txt_agent.setText(receiptsList.get(position).getAgent());
        holder.txt_type.setText(receiptsList.get(position).getType());
        holder.txt_status.setText(receiptsList.get(position).getStatus());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get items
                JSONArray Items = receiptsList.get(position).getItems();
                try {
                    for (int j = 0; j < Items.length() ; j++) {
                        JSONObject product = Items.getJSONObject(j);
                        System.out.println(product.getString("barcode"));
                    }
                } catch (Exception e) {
                    Log.e("JSON RESPONSE", e.getMessage());
                }

                ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.container, new DetailReceiptFragment()).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return receiptsList.size();
    }

}
