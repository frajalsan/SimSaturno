package com.fjas.simsaturno;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class InformacionAdapter extends RecyclerView.Adapter<InformacionAdapter.ViewHolder> {

    private List<InformacionSatelite> lista;

    public InformacionAdapter(List<InformacionSatelite> lista) {
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvMagnitud, tvDiametro, tvDescubridor, tvAnio;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvMagnitud = itemView.findViewById(R.id.tvMagnitud);
            tvDiametro = itemView.findViewById(R.id.tvDiametro);
            tvDescubridor = itemView.findViewById(R.id.tvDescubridor);
            tvAnio = itemView.findViewById(R.id.tvAnio);
        }
    }

    @NonNull
    @Override
    public InformacionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_informacion_satelite, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InformacionSatelite info = lista.get(position);
        holder.tvNombre.setText(info.getNombre());
        holder.tvMagnitud.setText("Magnitud: " + info.getMagnitud());
        holder.tvDiametro.setText("Diámetro: " + info.getDiametro() + " km");
        holder.tvDescubridor.setText("Descubridor: " + info.getDescubridor());
        holder.tvAnio.setText("Año: " + info.getAnio());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
