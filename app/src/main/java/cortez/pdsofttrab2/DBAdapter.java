package cortez.pdsofttrab2;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;
        import java.util.LinkedList;
        import java.util.List;

/**
 * Created by Usuario on 10/10/2016.
 */

//classe usada para fazer manipulaçoes CRUD create read update delete em databases
public class DBAdapter extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;


    public DBAdapter(Context context) {
        super(context,"MyDatabase", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS tabela (usuario VARCHAR(40), senha VARCHAR(30), id INT PRIMARY KEY UNIQUE)");

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older users table if existed
        db.execSQL("DROP TABLE IF EXISTS tabela");

        // create fresh books table
        this.onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        db.execSQL("CREATE TABLE IF NOT EXISTS tabela (usuario VARCHAR(40), senha VARCHAR(30), id INT PRIMARY KEY UNIQUE)");
        try{
            values.put("usuario", user.getUser());
            values.put("senha", user.getSenha());
            db.insert("tabela",null,values);
        }catch(SQLException e){
            e.printStackTrace();
        }



    }

    public List<User> getAllUsers(){

        List<User> users = new LinkedList<User>();

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT  * FROM tabela", null);

            // 3. go over each row, build user and add it to list
            User user = null;
            if (cursor.moveToFirst()) {
                do {
                    user = new User();
                    user.setUser(cursor.getString(cursor.getColumnIndex("usuario")));
                    user.setSenha(cursor.getString(cursor.getColumnIndex("senha")));

                    // Add book to books
                    users.add(user);
                } while (cursor.moveToNext());
            }

            Log.d("getAllUsers()", users.toString());
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }

        // return users list
        return users;

    }





}