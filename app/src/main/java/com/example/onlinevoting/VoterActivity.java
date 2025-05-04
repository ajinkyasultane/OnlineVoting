package com.example.onlinevoting;
package com.example.onlinevotingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.*;
import java.util.*;

public class VoterActivity extends AppCompatActivity {

    LinearLayout candidateListLayout;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter);

        candidateListLayout = findViewById(R.id.candidateList);
        db = FirebaseFirestore.getInstance();

        loadCandidates();
    }

    private void loadCandidates() {
        db.collection("candidates").get().addOnSuccessListener(querySnapshot -> {
            candidateListLayout.removeAllViews();
            for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                String name = doc.getString("name");
                int votes = doc.getLong("votes").intValue();

                Button voteBtn = new Button(this);
                voteBtn.setText(name + " - Vote");
                voteBtn.setOnClickListener(v -> castVote(doc.getId(), votes));

                candidateListLayout.addView(voteBtn);
            }
        });
    }

    private void castVote(String id, int currentVotes) {
        db.collection("candidates").document(id)
                .update("votes", currentVotes + 1)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Vote cast!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to vote", Toast.LENGTH_SHORT).show());
    }
}
