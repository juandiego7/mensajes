package co.com.juandiego.mensajes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.com.juandiego.mensajes.modelo.Cita;

/**
 * Created by Juan Diego on 11/06/2017.
 */

public class Adaptador extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Cita> citas;
    public Adaptador(Context context, List<Cita> citas){
        this.context = context;
        this.citas = citas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_view,parent,false);
        CitaViewHolder cita = new CitaViewHolder(view);
        return cita;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CitaViewHolder)holder).tvMensaje.setText(citas.get(position).getMensaje());
    }

    @Override
    public int getItemCount() {
        return citas.size();
    }

    public static class CitaViewHolder extends RecyclerView.ViewHolder{
        TextView tvMensaje;

        public CitaViewHolder(View itemView){
            super(itemView);
            tvMensaje = (TextView)itemView.findViewById(R.id.tvMensaje);
        }
    }
}
