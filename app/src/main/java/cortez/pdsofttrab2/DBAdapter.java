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
        db.execSQL("CREATE TABLE IF NOT EXISTS tabela (usuario VARCHAR(40), senha BINARY(32), id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "nome VARCHAR(30), rua VARCHAR(40), numero VARCHAR(10), complemento VARCHAR(30), bairro VARCHAR(10), cep VARCHAR(10), cidade VARCHAR(30), estado VARCHAR(20), " +
                "ano_nascimento INT, celular VARCHAR(15), email VARCHAR(20), foto BLOB)");

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older users table if existed
        db.execSQL("DROP TABLE IF EXISTS tabela");
        // create fresh users table
        this.onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        try{
            values.put("usuario", user.getUser());
            values.put("senha", user.getSenha());
            values.put("foto", user.getFoto());
            values.put("nome", user.getNome());
            //endereço
            values.put("rua", user.getRua());
            values.put("bairro", user.getBairro());
            values.put("numero", user.getNumero());
            values.put("complemento", user.getComplemento());
            values.put("cidade", user.getCidade());
            values.put("estado", user.getEstado());
            values.put("cep", user.getCep());
            //contato
            values.put("celular", user.getCelular());
            values.put("email", user.getMail());

            db.insert("tabela",null,values);
            db.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

//    public User getBook(String user){
//        // 1. get reference to readable DB
//        SQLiteDatabase db = this.getReadableDatabase();
//        // 2. build query
//        Cursor cursor =
//                db.query(TABLE_BOOKS, // a. table
//                        COLUMNS, // b. column names
//                        " id = ?", // c. selections
//                        new String[] { String.valueOf(id) }, // d. selections args
//                        null, // e. group by
//                        null, // f. having
//                        null, // g. order by
//                        null); // h. limit
//
//        // 3. if we got results get the first one
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        // 4. build book object
//        Book book = new Book();
//        book.setId(Integer.parseInt(cursor.getString(0)));
//        book.setTitle(cursor.getString(1));
//        book.setAuthor(cursor.getString(2));
//
//        //log
//        Log.d("getBook("+id+")", book.toString());
//
//        // 5. return book
//        return book;
//    }

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
                    user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    //user.setNome(cursor.getString(cursor.getColumnIndex("nome")));

                    // Add book to books
                    users.add(user);
                } while (cursor.moveToNext());
            }

            Log.d("getAllUsers()", users.toString());
            db.close();
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }

        // return users list
        return users;

    }

    public List<User> searchFor(String row, String word, String table) {
        List<User> users = new LinkedList<User>();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * " + " FROM " + table + " WHERE " + row + " =?", new String[]{word});
            User user = null;
            if (cursor.moveToFirst()) {
                do {
                    user = new User();
                    user.setUser(cursor.getString(cursor.getColumnIndex("usuario")));
                    user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    user.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
                    user.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                    //endereço
                    user.setRua(cursor.getString(cursor.getColumnIndex("rua")));
                    user.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
                    user.setComplemento(cursor.getString(cursor.getColumnIndex("complemento")));
                    user.setNumero(cursor.getString(cursor.getColumnIndex("numero")));
                    user.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
                    user.setEstado(cursor.getString(cursor.getColumnIndex("estado")));
                    user.setCep(cursor.getString(cursor.getColumnIndex("cep")));
                    //contato
                    user.setCelular(cursor.getString(cursor.getColumnIndex("celular")));
                    user.setMail(cursor.getString(cursor.getColumnIndex("email")));

                    user.setFoto(cursor.getBlob(cursor.getColumnIndex("foto")));
                    users.add(user);
                } while (cursor.moveToNext());
            }

            Log.d("searchForUsers()", users.toString());
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }

    public void eraseDb(){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS tabela");
            db.execSQL("CREATE TABLE IF NOT EXISTS tabela (usuario VARCHAR(40), senha BINARY(32), id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "nome VARCHAR(30), rua VARCHAR(40), numero VARCHAR(10), complemento VARCHAR(30), bairro VARCHAR(10), cep VARCHAR(10), cidade VARCHAR(30), estado VARCHAR(20), " +
                    "ano_nascimento INT, celular VARCHAR(15), email VARCHAR(20), foto BLOB)");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }







}
