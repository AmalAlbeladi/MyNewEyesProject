package com.example.myneweyes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mauth;
    private EditText signupUserName, signupPhoneNumber, signupEmail, EmailOfRelated, signupPassword;
    private Button signupButton;
    private TextView loginRedirectText;
    private RadioButton bstudent;
    private RadioButton bassistance;
    private RadioGroup userType;
    private Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()_[{}]:;',?/*~$^+=<>]).{6,20}$");
    String usertype,temp_key;
    DatabaseReference root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mauth = FirebaseAuth.getInstance();

        signupButton = findViewById(R.id.signup_button);
        signupButton.setOnClickListener(this);

        signupUserName = findViewById(R.id.signup_userName);
        signupEmail = findViewById(R.id.signup_email);
        EmailOfRelated = findViewById(R.id.EmailOfRelated);
        signupPassword = findViewById(R.id.signup_password);
        signupPhoneNumber = findViewById(R.id.signup_phoneNumber);
        bstudent = findViewById(R.id.radioBilndStudent);
        bassistance = findViewById(R.id.radioBlindAssistant);


        loginRedirectText = findViewById(R.id.loginRedirectText);
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();

    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.loginRedirectText:
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                break;

            case R.id.signup_button:
                register();
                break;
        }


    }

    private void register() {
        // validate
        String userName = signupUserName.getText().toString().trim();
        String email = signupEmail.getText().toString().trim();
        String pass = signupPassword.getText().toString().trim();
        String phoneNumber = signupPhoneNumber.getText().toString().trim();
        String emailOfRelated = EmailOfRelated.getText().toString().trim();

        // validate of all feild
        if (userName.isEmpty() && email.isEmpty() && pass.isEmpty() && phoneNumber.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "All field required", Toast.LENGTH_LONG).show();
        }
        //check of username
        if(userName.isEmpty()){
            signupUserName.setError("Full Name is required");
            signupUserName.requestFocus();
            return;
        }
        //check of email
        if(email.isEmpty()){
            signupEmail.setError("Email is required");
            signupEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmail.setError("Please provide valid Email with xx@xxx.xxx format");
            signupEmail.requestFocus();
        }
        //check of phone number
        if (phoneNumber.isEmpty()) {
            signupPhoneNumber.setError("Phone number is required");
            signupPhoneNumber.requestFocus();
            return;
        }
        if (!phoneNumber.matches("^05\\d{8}$")){
            signupPhoneNumber.setError("Please enter valid phone number with 05xxxxxxxx format");
            signupPhoneNumber.requestFocus();
            return;
        }
        //check of password
        if(pass.isEmpty()){ //you can also check the num. of chars.
            signupPassword.setError("Password is required");
            signupPassword.requestFocus();
            return;
        }
        if( !PASSWORD_PATTERN.matcher(pass).matches()){
            signupPassword.setError("\"Password too weak please enter a valid password "+
                    "(al least 1 digit, 1 upper case letter, 1 lower case letter, 1 special character, " +
                    "no spaces and no shorter than 6 items)");
            signupPassword.requestFocus();
            return;
        }

        if (!bstudent.isChecked() && !bassistance.isChecked()){
            Toast.makeText(SignUpActivity.this,"Please select your sign up type", Toast.LENGTH_LONG).show();

        }

        if(bstudent.isChecked()){
            usertype = "Blind Student";
        }

        if (bassistance.isChecked()){
            usertype = "Blind assistant";
        }


        String finalUsertype = usertype;
        /*mauth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Users Users = new Users(userName, email, phoneNumber,pass, finalUsertype);
                    FirebaseDatabase.getInstance().getReference("Users_Register_Info")
                            .child(FirebaseAuth.getInstance().getCurrentUser()
                                    .getUid()).setValue(Users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "User has been register successfully", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Failed to register! Try again", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                } else {
                    Toast.makeText(SignUpActivity.this, "Failed to register! ", Toast.LENGTH_LONG).show();

                }
            }
        });*/

        mauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    /*Users Users = new Users(userName, email, phoneNumber,pass, finalUsertype);
                    FirebaseDatabase.getInstance().getReference("Users_Register_Info")
                            .child(FirebaseAuth.getInstance().getCurrentUser()
                                    .getUid()).setValue(Users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "User has been register successfully", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Failed to register! Try again", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });*/
                    sendemailnotificationmessage(userName, email, phoneNumber,pass, finalUsertype, emailOfRelated);
                }
                else
                {
                    String message=task.getException().getMessage();
                    Toast.makeText(SignUpActivity.this, "Error "+message, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    private void sendusertologinactivity(String userName,String email,String phoneNumber,String pass,String finalUsertype,String emailOfRelated)
    {
        root= FirebaseDatabase.getInstance().getReference().child("Users_Register_Info");
        Map<String, Object> map1 = new HashMap<>();
        temp_key = root.push().getKey();
        root.updateChildren(map1);

        Map<String, Object> map = new HashMap<>();
        temp_key = root.push().getKey();
        root.updateChildren(map);

        DatabaseReference user = root.child(temp_key);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("userName", userName);
        map2.put("email",  email);
        map2.put("phoneNumber",  phoneNumber);
        map2.put("pass",  pass);
        map2.put("finalUsertype",  finalUsertype);
        map2.put("related",  emailOfRelated);


        user.updateChildren(map2);

        Intent loginintent=new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(loginintent);
        finish();
    }


    private void sendemailnotificationmessage(String userName,String email,String phoneNumber,String pass,String finalUsertype, String emailOfRelated)
    {
        FirebaseUser user = mauth.getCurrentUser();
        if(user !=null)
        {

            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(SignUpActivity.this, "Registration successful ,Please verify your account", Toast.LENGTH_LONG).show();
                        sendusertologinactivity(userName, email, phoneNumber,pass, finalUsertype, emailOfRelated);
                        mauth.signOut();
                    }
                    else
                    {
                        String error=task.getException().getMessage();
                        Toast.makeText(SignUpActivity.this, "Error: "+error, Toast.LENGTH_SHORT).show();
                        mauth.signOut();
                    }
                }
            });
        }
    }
}


