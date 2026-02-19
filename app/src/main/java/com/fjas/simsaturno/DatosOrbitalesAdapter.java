package com.fjas.simsaturno;




import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DatosOrbitalesAdapter extends RecyclerView.Adapter<DatosOrbitalesAdapter.ViewHolder> {

    private List<DatoOrbital> listaDatos;

    public DatosOrbitalesAdapter(List<DatoOrbital> listaDatos) {
        this.listaDatos = listaDatos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvRadioOrbital, tvPeriodicidad;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvRadioOrbital = itemView.findViewById(R.id.tvRadioOrbital);
            tvPeriodicidad = itemView.findViewById(R.id.tvPeriodicidad);
        }
    }

    @NonNull
    @Override
    public DatosOrbitalesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dato_orbital, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatoOrbital dato = listaDatos.get(position);
        holder.tvNombre.setText(dato.getNombre());
        holder.tvRadioOrbital.setText("Distancia a Saturno: " + dato.getRadioOrbital() + " km");
        holder.tvPeriodicidad.setText("Periodo orbital: " + dato.getPeriodicidad() + " días");
    }

    @Override
    public int getItemCount() {
        return listaDatos.size();
    }
}