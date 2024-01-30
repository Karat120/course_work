package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static int MAX_MESSAGE=170;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    EditText mEditTextMessage;
    Button mSendButton;
    ArrayList <String> messages =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextMessage=findViewById(R.id.message_input);
        mSendButton=findViewById(R.id.send_message_b);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mag= mEditTextMessage.getText().toString();
                if(mag.equals("")){
                    Toast.makeText(getApplicationContext() , "Введите сообщение",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mag.length()>MAX_MESSAGE){
                    Toast.makeText(getApplicationContext() , "Слишком длинное сообщение",Toast.LENGTH_SHORT).show();
                    return;
                }
                myRef.push().setValue(mag);
                mEditTextMessage.setText("");
            }
        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String mag=snapshot.getValue(String.class);
                messages.add(mag);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}

