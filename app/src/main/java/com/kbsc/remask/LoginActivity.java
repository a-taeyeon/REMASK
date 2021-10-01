package com.kbsc.remask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    private final static String TAG = "LoginActivity";

    FirebaseAuth firebaseAuth;

    EditText etEmail;
    EditText etPw;
    Button btnLogin;
    TextView tvErrorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = firebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            FirebaseAuth.getInstance().signOut();
        }

        tvErrorMsg = (TextView) findViewById(R.id.tvLogin_errorMsg);
        etEmail = (EditText) findViewById(R.id.etLogin_email);
        etPw = (EditText) findViewById(R.id.etLogin_password);
        btnLogin = (Button) findViewById(R.id.btnLogin_login);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pw = etPw.getText().toString().trim();

            if(!email.equals("") && !pw.equals("")) {
                firebaseAuth.signInWithEmailAndPassword(email, pw)
                        .addOnCompleteListener(LoginActivity.this, task -> {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MypageActivity.class);
                                startActivity(intent);
                            } else {
                                tvErrorMsg.setVisibility(View.VISIBLE);
                                tvErrorMsg.setText("아이디 또는 비밀번호가 잘못 입력 되었습니다.");
                            }
                        });
            }else if(email.equals("")){
                tvErrorMsg.setVisibility(View.VISIBLE);
                tvErrorMsg.setText("이메일을 입력해 주세요");
            }else if(pw.equals("")){
                tvErrorMsg.setVisibility(View.VISIBLE);
                tvErrorMsg.setText("비밀번호를 입력해 주세요");
            }
        });
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.tvLogin_goSignup:
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                break;
        }
    }
}