package com.whitespace.mydailyvuadmin.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whitespace.mydailyvuadmin.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class RoutineAdapter extends FirestoreRecyclerAdapter<Routine, RoutineAdapter.NoteHolder> {

    private OnItemClickListener listener;
    private Context mContext;

    public RoutineAdapter(@NonNull FirestoreRecyclerOptions<Routine> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Routine model) {
        holder.startTime.setText(model.getStartTime());
        holder.endTime.setText(model.getEndTime());
        holder.subject.setText(model.getSubject());
        holder.teacher.setText(model.getTeacher());
        holder.routine.setText(model.getRoutine());
        holder.room.setText(model.getRoom());
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_layout,
                parent, false);
        return new NoteHolder(view);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView startTime;
        TextView endTime;
        TextView subject;
        TextView teacher;
        TextView routine;
        TextView room;

        public NoteHolder(View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            subject = itemView.findViewById(R.id.subject);
            teacher = itemView.findViewById(R.id.teacher);
            routine = itemView.findViewById(R.id.routine);
            room = itemView.findViewById(R.id.room);

            mContext = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
