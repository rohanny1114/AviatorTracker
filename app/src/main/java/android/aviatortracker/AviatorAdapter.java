package android.aviatortracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class AviatorAdapter extends RecyclerView.Adapter<AviatorAdapter.Holder> {
    int position;
    ArrayList<Aviator> aviators = null;
    AviatorViewModel aviatorViewModel;
    public AviatorAdapter(ArrayList<Aviator> aviators, SavedActivity aviatorSavedActivity, AviatorDAO aDAO){
        this.aviators = aviators;
    }
    // Interface for adapter
    private OnItemClickListener itemClickListener;
    private AviatorDAO aDAO;
    public AviatorAdapter(ArrayList<Aviator> aviators, OnItemClickListener listener, AviatorDAO aDAO) {
        this.aviators = aviators;
        this.itemClickListener = listener;
        this.aDAO = aDAO;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aviator_result_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Aviator aviator = aviators.get(position);
        holder.resultStatus.setText(aviator.getStatus());
        holder.resultDstName.setText(aviator.getDstName());
        holder.resultFlight.setText(aviator.getFlightCode());
        holder.resultAirline.setText(aviator.getAirline());

        // Set click listener
        holder.itemView.setOnClickListener(c -> {
            if (itemClickListener != null) {
                itemClickListener.onAviatorSelected(aviator);
            }
        });
    }

    @Override
    public int getItemCount() {
        return aviators.size();
    }

    // Add a method to get the ViewHolder

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setAviators(ArrayList<Aviator> aviators) {
        this.aviators = aviators;
        // Notify the adapter that the data has changed
//        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {
        //String status, String srcCode, String dstCode, String dstName, String flightCode,String airline
        TextView resultStatus;
        TextView resultFlight;
        TextView resultAirline;
        TextView resultDstName;

        public Holder(@NonNull View itemView) {
            super(itemView);
            int position = getAbsoluteAdapterPosition();

            resultStatus = itemView.findViewById(R.id.resultStatus);
            resultDstName = itemView.findViewById(R.id.resultDstName);
            resultFlight = itemView.findViewById(R.id.resultFlight);
            resultAirline = itemView.findViewById(R.id.resultAirline);
            itemView.setOnClickListener(c -> {
                AviatorAdapter.this.setPosition(position);
            });
        }

    }
    public interface OnItemClickListener {
        void onAviatorSelected(Aviator selectedAviator);
    }

}


