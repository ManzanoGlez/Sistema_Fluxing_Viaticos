package manzano.utj.sistemafluxing.Funciones;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import manzano.utj.sistemafluxing.Modelos.Factura;
import manzano.utj.sistemafluxing.R;

import static android.content.Context.CLIPBOARD_SERVICE;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtRfcEmisor, txtRfcReceptor, txtMonto, txtRubro;



        public ViewHolder(View itemView) {
            super(itemView);


            txtRfcEmisor = itemView.findViewById(R.id.txtre);
            txtRfcReceptor = itemView.findViewById(R.id.txtrr);
            txtMonto = itemView.findViewById(R.id.txtmonto);
            txtRubro = itemView.findViewById(R.id.txtrubro);
        }
    }


        public List<Factura> FactutaLista;
        public RecyclerViewAdaptador(List<Factura> FactutaLista){
            this.FactutaLista = FactutaLista;
        }


    View v;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemfactura,parent,false);

           v =view;
        ViewHolder viewHolder = new ViewHolder(view);
 return viewHolder;
 }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.txtRfcEmisor.setText(FactutaLista.get(position).getRFC_EMISOR());
        holder.txtRfcReceptor.setText(FactutaLista.get(position).getRFC_RECEPTOR());
        holder.txtMonto.setText(FactutaLista.get(position).getMONTO());
        holder.txtRubro.setText(FactutaLista.get(position).getRUBRO());



        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Portapapeles = "Factura :" +
                                      "\nRFC emisor : " + FactutaLista.get(position).getRFC_EMISOR() +
                                      "\nRFC receptor : " + FactutaLista.get(position).getRFC_RECEPTOR() +
                                      "\nMonto : " + FactutaLista.get(position).getMONTO() +
                                      "\nRubro : " + FactutaLista.get(position).getRUBRO();

                ClipData clip = ClipData.newPlainText("Factura", Portapapeles);
                ClipboardManager clipboard = (ClipboardManager)v.getContext().getSystemService(CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(clip);


                Toast.makeText(v.getContext(), "Copiado", Toast.LENGTH_SHORT).show();
            }
        });



    }
    @Override
    public int getItemCount() {
        return FactutaLista.size();
    }


}
