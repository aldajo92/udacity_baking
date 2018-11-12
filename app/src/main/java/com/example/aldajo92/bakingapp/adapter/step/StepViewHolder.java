package com.example.aldajo92.bakingapp.adapter.step;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.aldajo92.bakingapp.R;
import com.example.aldajo92.bakingapp.models.ui.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.textView_step_count)
    AppCompatTextView stepCount;

    @BindView(R.id.textView_short_description)
    AppCompatTextView shortDescription;

    private StepListItemClickListener stepListItemClickListener;

    private int colorGraySmooth;

    private int colorTransparent;

    public StepViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);

        colorGraySmooth = itemView.getResources().getColor(R.color.gray_smooth);
        colorTransparent = itemView.getResources().getColor(R.color.transparent);
    }

    public void bind(@NonNull Step step, StepListItemClickListener stepListItemClickListener) {
        if (getAdapterPosition() % 2 == 0) {
            itemView.setBackgroundColor(colorGraySmooth);
        } else {
            itemView.setBackgroundColor(colorTransparent);
        }
        this.stepListItemClickListener = stepListItemClickListener;
        stepCount.setText(stepCount.getContext().getString(R.string.step_count, getAdapterPosition()));
        shortDescription.setText(step.getShortDescription());
    }

    @Override
    public void onClick(View v) {
        stepListItemClickListener.onStepItemClick(getAdapterPosition());
    }
}