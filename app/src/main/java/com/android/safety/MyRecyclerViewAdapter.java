package com.android.safety;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.android.safety.databinding.ItemTripBinding;
import com.android.safety.locations.Feature;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements CustomClickListener {

    private List<Feature> dataModelList;
    private Context context;

    public MyRecyclerViewAdapter(List<Feature> dataModelList, Context ctx) {
        this.dataModelList = dataModelList;
        context = ctx;
    }

    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        ItemTripBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_trip, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Feature dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemRowBinding.setItemClickListener(this);
    }


    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemTripBinding itemRowBinding;

        public ViewHolder(ItemTripBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        public void bind(Feature obj) {
            itemRowBinding.setVariable(BR.model, obj);
            itemRowBinding.executePendingBindings();

            itemRowBinding.llDirection.setOnClickListener(view -> {
                if(obj.getGeometry().getCoordinates().size() >= 2) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?daddr=" + obj.getGeometry().getCoordinates().get(1) + "," + obj.getGeometry().getCoordinates().get(0)));
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Direction data is not available!", Toast.LENGTH_SHORT).show();
                }
            });

            itemRowBinding.llData.setOnClickListener(view -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.wikidata.org/wiki/" + obj.getProperties().getWikidata()));
                context.startActivity(browserIntent);
            });

            itemRowBinding.llMap.setOnClickListener(view -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.openstreetmap.org/" + obj.getProperties().getOsm()));
                context.startActivity(browserIntent);
            });

//            final String[] tags = obj.getProperties().getKinds().split(",");
//            for(int i = 0; i < tags.length; i++) {
//                AppCompatTextView btn = new AppCompatTextView(context);
//                btn.setText(tags[i]);
//                btn.setTextColor(ContextCompat.getColor(context,R.color.white));
//                btn.setBackgroundColor(ContextCompat.getColor(context,R.color.cyan_light));
//                btn.setPadding(5, 4, 5, 4);
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                params.setMargins(0,0,10,0);
//                btn.setLayoutParams(params);
//                itemRowBinding.llTagsData.addView(btn);
//                int idx = itemRowBinding.llTagsData.indexOfChild(btn);
//                btn.setTag(Integer.toString(idx));
//            }
//            itemRowBinding.llTagsData.invalidate();
        }
    }

    public void cardClicked(Feature f) {
        Toast.makeText(context, "You clicked " + f.getProperties().getName(),
                Toast.LENGTH_LONG).show();
    }
}