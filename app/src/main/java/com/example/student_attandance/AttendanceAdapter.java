package com.example.student_attandance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {

    private List<String> attendanceDates;

    public AttendanceAdapter(List<String> attendanceDates) {
        this.attendanceDates = attendanceDates;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance_date, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        String date = attendanceDates.get(position);
        holder.dateTextView.setText(date);
    }

    @Override
    public int getItemCount() {
        return attendanceDates.size();
    }

    static class AttendanceViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView;

        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.text_view_date);
        }
    }
}
