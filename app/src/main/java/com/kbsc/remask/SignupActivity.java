package com.kbsc.remask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.StringTokenizer;

public class SignupActivity extends AppCompatActivity {
    final static String TAG = "SignupActivity";

    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;

    EditText etName;
    EditText etEmail;
    EditText etPw;
    EditText etPwConfirm;
    EditText etPhone;
    Button btnConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        if(firebaseAuth.getCurrentUser() != null){
            FirebaseAuth.getInstance().signOut();
        }

        etName = (EditText)findViewById(R.id.etSignup_name);
        etEmail = (EditText)findViewById(R.id.etSignup_email);
        etPw = (EditText)findViewById(R.id.etSignup_password);
        etPwConfirm = (EditText)findViewById(R.id.etSignup_passwordconfirm);
        etPhone = (EditText)findViewById(R.id.etSignup_phone);
        btnConfirm = (Button)findViewById(R.id.btnSignup_confirm);

        btnConfirm.setOnClickListener(v -> {
            if(!etName.getText().toString().equals("") && !etEmail.getText().toString().equals("")
                    && !etPw.getText().toString().equals("") && !etPwConfirm.getText().toString().equals("")
                    && !etPhone.getText().toString().equals("")){
                if(!etPw.getText().toString().equals(etPwConfirm.getText().toString())){
                    Toast.makeText(SignupActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    createNewUser(etName.getText().toString(), etEmail.getText().toString(), etPw.getText().toString(), etPhone.getText().toString());
                }
            } else{
                Toast.makeText(SignupActivity.this, "정보를 모두 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void createNewUser(String name, String email, String pw, String phone){
        User user = new User(name, email, pw, phone, false, "");
        Log.d(TAG, "user: " + user.toString());

        firebaseAuth.createUserWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                            StringTokenizer stk = new StringTokenizer(email,"@");
                            dbRef.child("users").child(stk.nextToken()).setValue(user);

                            Intent intent = new Intent(SignupActivity.this, MypageActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(SignupActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(SignupActivity.this,"비밀번호가 간단해요.." ,Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(SignupActivity.this,"email 형식에 맞지 않습니다." ,Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(SignupActivity.this,"이미존재하는 email 입니다." ,Toast.LENGTH_SHORT).show();
                            } catch(Exception e) {
                                Toast.makeText(SignupActivity.this,"다시 확인해주세요.." ,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}