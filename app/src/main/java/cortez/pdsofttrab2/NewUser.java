package cortez.pdsofttrab2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.*;
import java.util.List;

public class NewUser extends AppCompatActivity {

    //fields
    TextView userEditText;
    TextView senhaEdittext;
    TextView listaTextView;
    User user;
    DBAdapter db = new DBAdapter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        userEditText = (TextView) findViewById(R.id.userEditText);
        senhaEdittext = (TextView) findViewById(R.id.senhaEditText);
        listaTextView = (TextView) findViewById(R.id.listaTextView);
    }

    public void onClick(View v){
        switch (v.getId()){
            case (R.id.createNewUserButton):
                user = new User();
                user.setUser(userEditText.getText().toString());
                user.setSenha(senhaEdittext.getText().toString());
                db.addUser(user);
                Toast t = Toast.makeText(this, "user adicionado!\n", Toast.LENGTH_LONG);
                t.show();
                break;
            case(R.id.showTableButton):
                List<User> users = db.getAllUsers();
                for (int i=0;i<users.size();i++) {
                    System.out.println(users.get(i).getUser());
                }
                t = Toast.makeText(this, "mostrando tabela", Toast.LENGTH_LONG);
                t.show();
                listaTextView.setText("");
                for (int i=0;i<users.size();i++) {
                    listaTextView.setText(listaTextView.getText().toString() + "\n"
                            + "usuario: " + users.get(i).getUser() + "\n" + "senha: " + users.get(i).getSenha() + "\n");
                }



                break;
        }
    }

}
