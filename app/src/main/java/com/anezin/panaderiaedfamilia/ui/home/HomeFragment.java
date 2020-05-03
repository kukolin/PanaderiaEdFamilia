package com.anezin.panaderiaedfamilia.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anezin.panaderiaedfamilia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("Usuarios");

    TextView mConditionTextView;
    TextView estadoFioreTw;

    RecyclerView recyclerView;
    ArrayList<String> listDatos = new ArrayList<String>();

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        mConditionTextView = root.findViewById(R.id.text_home);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView1);
        //recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));

        refrescarLista();


        mConditionRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listDatos.clear();

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    listDatos.add(child.getKey());

                    for (DataSnapshot child2 : child.getChildren()) {
                        listDatos.add(child2.getValue().toString());
                    }


                }
                refrescarLista();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return root;
    }


    private void refrescarLista(){
        AdapterDatos adapter = new AdapterDatos(listDatos);
        recyclerView.setAdapter(adapter);
    }

}
