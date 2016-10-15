package cortez.pdsofttrab2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //field
    Button newUserButton;
    TextView usernameTextView;
    List<User> users = new LinkedList<User>();
    DBAdapter db = new DBAdapter(this);
    TextView passwordTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newUserButton = (Button) findViewById(R.id.newUserButton);
        usernameTextView = (TextView) findViewById(R.id.userEditText);
        passwordTextView = (TextView) findViewById(R.id.senhaEditText);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.newUserButton:
                Intent createNewuserIntent = new Intent(this, NewUser.class);
                startActivity(createNewuserIntent);
                break;

            case R.id.loginButton:
                //checando se a senha confere com o usuario
                users = db.searchFor("usuario",usernameTextView.getText().toString(),"tabela");
                String senhaDigitada = passwordTextView.getText().toString();
                if(users.get(0).getSenha().equals(passwordTextView.getText().toString())){
                    Intent loginIntent = new Intent(this, UserScreen.class);
                    loginIntent.putExtra("username", usernameTextView.getText().toString());
                    startActivity(loginIntent);
                }
                else {
                    Toast t = Toast.makeText(this, "Usuario ou senha incorretos", Toast.LENGTH_LONG);
                    t.show();
                }
                break;
        }
    }
}
