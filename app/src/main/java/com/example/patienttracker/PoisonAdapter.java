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

public class PoisonAdapter extends FirebaseRecyclerAdapter<poison, PoisonAdapter.PoisonViewHolder> {
    Context context;

    public PoisonAdapter(@NonNull FirebaseRecyclerOptions<poison> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final PoisonViewHolder holder, final int position, @NonNull final poison poison) {


        holder.address.setText(poison.getAdd1());
        holder.age.setText(poison.getAge1());
        holder.disease.setText(poison.getDis1());
        holder.lastname.setText(poison.getLn1());
        holder.name.setText(poison.getN1());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("poison")
                        .child(getRef(position).getKey())
                        .setValue(null)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialog = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new ViewHolder(R.layout.patientt))
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();
                View holderView=(LinearLayout) dialog.getHolderView();
                final EditText address=holderView.findViewById(R.id.text_add);
                final EditText age=holderView.findViewById(R.id.text_age);
                final EditText disease=holderView.findViewById(R.id.text_dis);
                final EditText lastname=holderView.findViewById(R.id.text_last);
                final EditText name=holderView.findViewById(R.id.text_name);
                Button update=holderView.findViewById(R.id.update);

                address.setText(poison.getAdd1());
                age.setText(poison.getAge1());
                disease.setText(poison.getDis1());
                lastname.setText(poison.getLn1());
                name.setText(poison.getN1());


                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("add1",address.getText().toString());
                        map.put("age1",age.getText().toString());
                        map.put("dis1",disease.getText().toString());
                        map.put("ln1",lastname.getText().toString());
                        map.put("n1",name.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("poison").child( getRef(position).getKey())
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
    public PoisonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poison, parent, false);

        return new PoisonViewHolder(view);
    }

    class PoisonViewHolder extends RecyclerView.ViewHolder{
        public TextView address;
        public TextView age;
        public TextView disease;
        public TextView lastname;
        public TextView name;


        public ImageView edit;
        public ImageView delete;

        public PoisonViewHolder(@NonNull View itemView) {
            super(itemView);
            address=(TextView)itemView.findViewById(R.id.text_add);
            age=(TextView)itemView.findViewById(R.id.text_age);
            disease=(TextView)itemView.findViewById(R.id.text_dis);
            lastname=(TextView)itemView.findViewById(R.id.text_last);
            name=(TextView)itemView.findViewById(R.id.text_name);
            edit=(ImageView)itemView.findViewById(R.id.edit);
            delete=(ImageView)itemView.findViewById(R.id.delete);
        }
    }
}
