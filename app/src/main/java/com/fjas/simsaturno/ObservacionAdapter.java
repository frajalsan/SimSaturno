package com.fjas.simsaturno;

import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ObservacionAdapter extends RecyclerView.Adapter<ObservacionAdapter.ViewHolder> {
    private List<Observacion> lista;

    public ObservacionAdapter(List<Observacion> lista) {
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSatelite, tvObservacion, tvFechaHora;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSatelite = itemView.findViewById(R.id.tvSatelite);
            tvObservacion = itemView.findViewById(R.id.tvObservacion);
            tvFechaHora = itemView.findViewById(R.id.tvFechaHora);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_observacion, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Observacion obs = lista.get(position);
        holder.tvSatelite.setText("Satélite: " + obs.getSatelite());
        holder.tvObservacion.setText(obs.getTexto());
        holder.tvFechaHora.setText("Fecha: " + obs.getFecha() + "   Hora: " + obs.getHora());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}

