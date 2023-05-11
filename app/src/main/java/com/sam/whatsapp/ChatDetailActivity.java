package com.sam.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sam.whatsapp.databinding.ActivityChatDetailBinding;

public class ChatDetailActivity extends AppCompatActivity {


    ActivityChatDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}