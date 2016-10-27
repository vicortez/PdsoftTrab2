package cortez.pdsofttrab2;

import android.content.Intent;
import android.content.SharedPreferences;
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
    Button loginButton;
    TextView usernameTextView;
    List<User> users = new LinkedList<User>();
    DBAdapter db = new DBAdapter(this);
    TextView passwordTextView;
    String encryptedPassword;

    private SharedPreferences autoLoginPref;
    private SharedPreferences.Editor autoLoginPrefEditor;
    boolean autologin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newUserButton = (Button) findViewById(R.id.newUserButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        usernameTextView = (TextView) findViewById(R.id.userEditText);
        passwordTextView = (TextView) findViewById(R.id.senhaEditText);
        

        autoLoginPref = getSharedPreferences("autoLogin",0);
        autoLoginPrefEditor = autoLoginPref.edit();

        //função auto-login
        autologin = autoLoginPref.getBoolean("autoLogin",false);

        //////// set to false due developing process
        autologin = false;
        /////// end of corrective code

        if(autologin == true){
            usernameTextView.setText(autoLoginPref.getString("username",""));
            loginButton.performClick();
       }




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
                if(users.size() == 0){
                    Toast t = Toast.makeText(this, "Usuario nao encrontado", Toast.LENGTH_LONG);
                    t.show();
                }
                else {//usuario encontrado

                    //auto filling password if auto-login enabled
                    if(autologin == true) {
                        encryptedPassword = autoLoginPref.getString("password","");
                    }
                    else{//no auto login
                        encryptedPassword = Security.encrypt(passwordTextView.getText().toString());
                    }

                    if (users.get(0).getSenha().equals(encryptedPassword)) {

                        //salvando usuario e senha para proxima inicialização do aplicativo
                        autoLoginPrefEditor.putBoolean("autoLogin",true);
                        autoLoginPrefEditor.putString("username",usernameTextView.getText().toString());
                        autoLoginPrefEditor.putString("password",encryptedPassword);
                        autoLoginPrefEditor.commit();

                        Intent loginIntent = new Intent(this, UserScreen.class);
                        loginIntent.putExtra("username", usernameTextView.getText().toString());
                        startActivity(loginIntent);
                    } else {
                        Toast t = Toast.makeText(this, "Usuario ou senha incorretos", Toast.LENGTH_LONG);
                        t.show();
                    }
                }
                break;
        }
    }

//    private void autoLogin (){
//        Intent loginIntent = new Intent(this, UserScreen.class);
//        loginIntent.putExtra("username", autoLoginPref.getString("username",""));
//        startActivity(loginIntent);
//    }
}
