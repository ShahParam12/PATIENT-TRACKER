package com.example.patienttracker;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class PostAdapter extends FirebaseRecyclerAdapter<Member, PostAdapter.PastViewHolder> {
private Context context;

    public PostAdapter(@NonNull FirebaseRecyclerOptions<Member> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PastViewHolder holder, final int position, @NonNull final Member model) {

        holder.name.setText(model.getName());
        holder.time.setText(model.getTime());
        holder.date.setText(model.getDate());
        holder.reason.setText(model.getReason());


        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Member")
                        .child(getRef(position).getKey())
                        .setValue(null)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
            }
        });


        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new ViewHolder(R.layout.content))
                        .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();

                View holderView =(LinearLayout) dialog.getHolderView();
                final EditText name=holderView.findViewById(R.id.name);
                final EditText time=holderView.findViewById(R.id.time);
                final EditText date=holderView.findViewById(R.id.date);
                final EditText reason=holderView.findViewById(R.id.reason);
                Button Update=holderView.findViewById(R.id.Update);

                name.setText(model.getName());
                time.setText(model.getTime());
                date.setText(model.getDate());
                reason.setText(model.getReason());

                Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("time",time.getText().toString());
                        map.put("date",date.getText().toString());
                        map.put("reason",reason.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Member")
                                .child(getRef(position).getKey())
                                .updateChildren(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        dialog.dismiss();
                                    }
                                });

                    }
                });

                dialog.show();
            }
        });

    }

    @NonNull
    @Override
    public PastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post, parent, false);

        return new PastViewHolder(view);
    }

    class PastViewHolder extends RecyclerView.ViewHolder {

        TextView name,time,date,reason;
        ImageView btn_edit,btn_delete;

        public PastViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.textViewName);
            time=itemView.findViewById(R.id.textViewTime);
            date=itemView.findViewById(R.id.textViewDate);
            reason=itemView.findViewById(R.id.textViewReason);
            btn_edit=itemView.findViewById(R.id.button_edit);
            btn_delete=itemView.findViewById(R.id.button_delete);
        }
    }
}
