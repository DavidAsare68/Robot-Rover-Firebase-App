package rc.rover;

import android.graphics.Color;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnRegister_Click(View view) {
        Intent i = new Intent(MainActivity.this, Register.class);
        startActivity(i);
    }

    public void btnLogin_Click(View view) {
        Intent i = new Intent(MainActivity.this, Login.class);
        startActivity(i);
    }
}
