package com.example.onlinevoting;


import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {

    EditText candidateInput;
    Button addBtn;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        candidateInput = findViewById(R.id.candidateNameInput);
        addBtn = findViewById(R.id.addCandidateButton);
        db = FirebaseFirestore.getInstance();

        addBtn.setOnClickListener(v -> {
            String name = candidateInput.getText().toString().trim();
            if (!name.isEmpty()) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("name", name);
                data.put("votes", 0);
                db.collection("candidates").add(data)
                        .addOnSuccessListener(r -> {
                            Toast.makeText(this, "Candidate added", Toast.LENGTH_SHORT).show();
                            candidateInput.setText("");
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, "Failed to add", Toast.LENGTH_SHORT).show());
            }
        });
    }
}
