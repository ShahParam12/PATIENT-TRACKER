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

import com.example.patienttracker.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class DoctAdapter  extends FirebaseRecyclerAdapter<Doct, DoctAdapter.DoctViewHolder> {
    private Context context;
    public DoctAdapter(@NonNull FirebaseRecyclerOptions<Doct> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull DoctViewHolder holder, final int position, @NonNull final Doct Doct) {

        holder.Name.setText(Doct.getName());
        holder.Lastname.setText(Doct.getLastname());
        holder.Age.setText(Doct.getAge());
        holder.Specialist.setText(Doct.getSpecialist());
        holder.Phone.setText(Doct.getPhone());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Doct")
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
                        .setContentHolder(new ViewHolder(R.layout.content2))
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .create();

                View holderView =(LinearLayout) dialog.getHolderView();
                final EditText Name=holderView.findViewById(R.id.text_name);
                final EditText Lastname=holderView.findViewById(R.id.text_last);
                final EditText Age=holderView.findViewById(R.id.text_age);
                final EditText Specialist=holderView.findViewById(R.id.text_special);
                final EditText Phone=holderView.findViewById(R.id.text_phone);
                Button Update=holderView.findViewById(R.id.update);

                Name.setText(Doct.getName());
                Lastname.setText(Doct.getLastname());
                Age.setText(Doct.getAge());
                Specialist.setText(Doct.getSpecialist());
                Phone.setText(Doct.getPhone());

                Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("name",Name.getText().toString());
                        map.put("lastname",Lastname.getText().toString());
                        map.put("age",Age.getText().toString());
                        map.put("specialist",Specialist.getText().toString());
                        map.put("phone",Phone.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Doct")
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
    public DoctViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doct, parent, false);
        return new DoctViewHolder(view);
    }

    class DoctViewHolder extends RecyclerView.ViewHolder{
        public TextView Age;
        public TextView Lastname;
        public TextView Name;
        public TextView Phone;
        public TextView Specialist;

        public ImageView edit;
        public ImageView delete;

        public DoctViewHolder(@NonNull View itemView) {
            super(itemView);
            Age=(TextView)itemView.findViewById(R.id.text_age);
            Lastname=(TextView)itemView.findViewById(R.id.text_last);
            Name=(TextView)itemView.findViewById(R.id.text_name);
            Phone=(TextView)itemView.findViewById(R.id.text_phone);
            Specialist=(TextView)itemView.findViewById(R.id.text_special);
            edit=(ImageView)itemView.findViewById(R.id.edit);
            delete=(ImageView)itemView.findViewById(R.id.delete);
        }
    }
}
