package pasindu.dev.classie.srilanka_covid_19.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import pasindu.dev.classie.srilanka_covid_19.R;

public class HospitalsAdapter extends RecyclerView.Adapter<HospitalsAdapter.ViewHolder> {
    private Map<String, String> map;
    List<Object> listOfDates = new ArrayList<>();
    List<Object> listOfCount = new ArrayList<>();

    public HospitalsAdapter(Map<String, String> data) {
        map = data;
    }

    @Override
    public HospitalsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_row, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(HospitalsAdapter.ViewHolder holder, int position) {

        for (Map.Entry entry : map.entrySet()) {
            System.out.println("Asasasa" + entry);
            listOfDates.add(entry);
        }
        Log.d("position", "holder : " + position);
        holder.txtDate.setText("Date : "+listOfDates.get(position).toString().split("=")[0]);
        holder.txtCount.setText("Count : "+listOfDates.get(position).toString().split("=")[1]);
//        holder.txtCount.setText(listOfCount.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return this.map.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtDate;
        private TextView txtCount;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.txtDate = view.findViewById(R.id.txt_date);
            this.txtCount = view.findViewById(R.id.txt_count);
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.txtDate.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}