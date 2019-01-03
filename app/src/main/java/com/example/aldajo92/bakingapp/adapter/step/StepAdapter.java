package com.example.aldajo92.bakingapp.adapter.step;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.models.ui.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder> {

    private final StepListItemClickListener stepListItemClickListener;
    private List<Step> steps;

    public StepAdapter(StepListItemClickListener stepListItemClickListener) {
        this.stepListItemClickListener = stepListItemClickListener;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View rootView = layoutInflater.inflate(R.layout.view_item_step, parent, false);
        return new StepViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Step step = steps.get(position);
        holder.bind(step, stepListItemClickListener);
    }

    @Override
    public int getItemCount() {
        return steps == null ? 0 : steps.size();
    }

    public void setData(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    public List<Step> getData() {
        return steps;
    }

}
