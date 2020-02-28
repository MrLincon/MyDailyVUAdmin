package com.whitespace.mydailyvuadmin.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.whitespace.mydailyvuadmin.R;

public class TeacherAdapter extends FirestoreRecyclerAdapter<Profile, TeacherAdapter.TeacherHolder> {

    private OnItemClickListener listener;
    private Context mContext;

    public TeacherAdapter(@NonNull FirestoreRecyclerOptions<Profile> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TeacherHolder holder, int position, @NonNull Profile model) {
        holder.Container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        Picasso.get().load(model.getImageUrl()).error(R.drawable.user_default).into(holder.imageView);
        holder.name.setText(model.getName());
        holder.designation.setText(model.getDesignation());
        holder.department.setText(model.getDepartment());
    }

    @NonNull
    @Override
    public TeacherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_card_layout,
                parent, false);
        return new TeacherHolder(view);
    }

    class TeacherHolder extends RecyclerView.ViewHolder {
        TextView name,designation,department;
        CircularImageView imageView;
        ConstraintLayout Container;

        public TeacherHolder(View itemView) {
            super(itemView);
            Container = itemView.findViewById(R.id.container);
            imageView = itemView.findViewById(R.id.user_image);
            name = itemView.findViewById(R.id.card_name);
            designation = itemView.findViewById(R.id.card_designation);
            department = itemView.findViewById(R.id.card_department);

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
