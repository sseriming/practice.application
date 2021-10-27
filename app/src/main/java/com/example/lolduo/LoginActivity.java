package com.example.lolduo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;         //firebase 인증처리
    private DatabaseReference mDatabaseRef;     //실시간 데이터베이스
    private EditText mEtEmail, mEtPwd;          //로그인 입력필트
    EditText id, pwd;
    Button btn;
    String loginId,loginPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Lolduo");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);

        Button btn_login = findViewById(R.id.btn_login);


        id = (EditText)findViewById(R.id.et_email) ;
        pwd = (EditText)findViewById(R.id.et_pwd);
        btn = (Button)findViewById(R.id.btn_login);
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        //처음에 sharedpreference에 아무런 정보도 없으므로 값을 저장할 키 생성

        // getstring의 첫번째 인자는 저장될 키, 두번째 인자는 값이다
        loginId = auto.getString("et_email",null);
        loginPwd = auto.getString("et_pwd",null);
        //처음엔 값이 없으므로 키값은 원하는 것으로 하고 값을 null로 준다.

        if(loginId != null && loginPwd != null) {
            if(loginId.equals("sunflower@gmail.com")&& loginPwd.equals("test123")) {
                Toast.makeText(LoginActivity.this, "자동 로그인 되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } else if(loginId ==null && loginPwd == null) {
            btn_login.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if(id.getText().toString().equals("sunflower@gmail.com")&&pwd.getText().toString().equals("test123")) {
                        SharedPreferences auto = getSharedPreferences("auto",Activity.MODE_PRIVATE);

                        SharedPreferences.Editor autoLogin = auto.edit();
                        autoLogin.putString("et_email",id.getText().toString());
                        autoLogin.putString("et_pwd",pwd.getText().toString());

                        autoLogin.commit(); //commit을 꼭 해줘야 저장이 됨
                        Toast.makeText(LoginActivity.this, id.getText().toString()+"님 환영합니다.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    //로그인 요청
                    String strEmail = mEtEmail.getText().toString();
                    String strPwd = mEtPwd.getText().toString();


                    mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                //로그인 성공
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish(); //현재 엑티비티 파괴
                            }else {
                                Toast.makeText(LoginActivity.this, "로그인 실패..",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

        }

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}