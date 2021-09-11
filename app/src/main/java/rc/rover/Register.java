package rc.rover;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText txtEmailAddress;
    private EditText txtPassword;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtEmailAddress = (EditText) findViewById(R.id.txtEmailRegistration);
        txtPassword = (EditText) findViewById(R.id.txtPasswordRegister);
        firebaseAuth = firebaseAuth.getInstance();
    }
    public void btnRegisterUser_Click(View view) {
        final ProgressDialog progressDialog = ProgressDialog.show(Register.this, "Please wait..", "Processing true..", true);
        (firebaseAuth.createUserWithEmailAndPassword(txtEmailAddress.getText().toString(), txtPassword.getText().toString()))
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            public void onComplete(@Nullable Task<AuthResult> task) {
                progressDialog.dismiss();

                if(task.isSuccessful()) {
                    Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Register.this, Login.class);
                    startActivity(i);
                }
                else {
                    Log.e("ERROR", task.getException().toString());
                    Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
