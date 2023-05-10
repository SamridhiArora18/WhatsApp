package com.sam.whatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.sam.whatsapp.Models.Users;
import com.sam.whatsapp.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    GoogleSignInClient mgoogleSignInClient;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance("https://whatsapp-35c27-default-rtdb.asia-southeast1.firebasedatabase.app");
        progressDialog= new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("We are login your Account");

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                                .build();

        mgoogleSignInClient= GoogleSignIn.getClient(this,gso);


        binding.btnsSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    progressDialog.show();
                    auth.signInWithEmailAndPassword(binding.etEmail.getText().toString(), binding.etPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();

                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }




        });


        binding.txtClickForSignUp.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                    Intent intent = new Intent(SignInActivity.this, SignupActivity.class);
                    startActivity(intent);


            }
        });

        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();
                Intent intent= new Intent(SignInActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });




       if(auth.getCurrentUser()!=null){
           Intent intent= new Intent(SignInActivity.this,MainActivity.class);
            startActivity(intent);

        }



    }

    int RC_SIGN_IN=65;
    private void signIn(){
        Intent signInIntent= mgoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try{
                GoogleSignInAccount account =task.getResult(ApiException.class);
              Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            }
            catch (ApiException e) {
                Log.w("TAG","Google sign in failed",e);

            }
        }
    }
      private void firebaseAuthWithGoogle(String idToken) {
          AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
          auth.signInWithCredential(firebaseCredential)
                  .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()) {
                              // Sign in success, update UI with the signed-in user's information
                              Log.d("TAG", "signInWithCredential:success");
                              FirebaseUser user = auth.getCurrentUser();
                              Users users = new Users();
                              users.setUserId(user.getUid());
                              users.setUsername(user.getDisplayName());
                              users.setProfilepic(user.getPhotoUrl().toString());
                              database.getReference().child("Users").child(user.getUid()).setValue(users);

                              //updateUI(user);
                          } else {
                              // If sign in fails, display a message to the user.
                              Log.d("TAG", "signInWithCredential:failure", task.getException());
                              //updateUI(null);
                          }
                      }
                  });
      }


}