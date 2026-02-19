package com.fjas.simsaturno;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PeriodoAdapter extends RecyclerView.Adapter<PeriodoAdapter.ViewHolder> {

    private List<PeriodoSatelite> lista;

    public PeriodoAdapter(List<PeriodoSatelite> lista) {
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreSatelite, tvPeriodo, tvFechaHora;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombreSatelite = itemView.findViewById(R.id.tvNombreSatelite);
            tvPeriodo = itemView.findViewById(R.id.tvPeriodo);
            tvFechaHora = itemView.findViewById(R.id.tvFechaHora);
        }
    }

    @NonNull
    @Override
    public PeriodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_periodo_satelite, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PeriodoSatelite satelite = lista.get(position);
        holder.tvNombreSatelite.setText(satelite.getNombre());
        holder.tvPeriodo.setText("Periodo sinódico: " + satelite.getPeriodo() + " días");
        holder.tvFechaHora.setText("Fecha referencia: " + satelite.getFechaHora());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}

