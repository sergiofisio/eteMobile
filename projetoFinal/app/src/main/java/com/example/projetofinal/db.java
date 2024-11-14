package com.example.projetofinal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class db implements AutoCloseable {

    private SQLiteDatabase database;
    private final Context context;
    private static final String DATABASE_NAME = "agenda";

    public db(Context context) {
        this.context = context;
        openDatabase();
    }

    public void openDatabase() {
        if (database == null || !database.isOpen()) {
            database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            createTableIfNotExists("contatos");
        }
    }

    public void createTableIfNotExists(String tableName) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT, " +
                "telefone TEXT, " +
                "email TEXT);";
        database.execSQL(createTableQuery);
    }

    public List<Map<String, Object>> getInfo(String table) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try (Cursor cursor = database.rawQuery("SELECT * FROM " + table, null)) {
            while (cursor.moveToNext()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                row.put("nome", cursor.getString(cursor.getColumnIndexOrThrow("nome")));
                row.put("telefone", cursor.getString(cursor.getColumnIndexOrThrow("telefone")));
                row.put("email", cursor.getString(cursor.getColumnIndexOrThrow("email")));
                resultList.add(row);
            }
        }
        return resultList;
    }

    public List<Map<String, Object>> getContact(String table, String whereClause, String[] whereArgs) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        String sql = "SELECT * FROM " + table + " WHERE " + whereClause;

        try (Cursor cursor = database.rawQuery(sql, whereArgs)) {
            while (cursor.moveToNext()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                row.put("nome", cursor.getString(cursor.getColumnIndexOrThrow("nome")));
                row.put("telefone", cursor.getString(cursor.getColumnIndexOrThrow("telefone")));
                row.put("email", cursor.getString(cursor.getColumnIndexOrThrow("email")));
                resultList.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }


    public String addOrUpdate(String nome, String telefone, String email, String type, String tableName, int id) {
        String sql;
        try {
            if ("add".equalsIgnoreCase(type)) {
                sql = "INSERT INTO " + tableName + " (nome, telefone, email) VALUES (?, ?, ?)";
                database.execSQL(sql, new String[]{nome, telefone, email});
                return "Contato adicionado com sucesso!";
            } else if ("update".equalsIgnoreCase(type)) {
                sql = "UPDATE " + tableName + " SET telefone = ?, email = ? WHERE id = ?";
                database.execSQL(sql, new String[]{telefone, email, String.valueOf(id)});
                return "Contato atualizado com sucesso!";
            } else {
                return "Erro: Tipo de operação inválido.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao adicionar ou atualizar: " + e.getMessage();
        }
    }

    public String delete(int id, String table) {
        String sql = "DELETE FROM " + table + " WHERE id = ?";
        try {
            openDatabase();
            database.execSQL(sql, new String[]{String.valueOf(id)});
            return "Contato deletado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao deletar contato: " + e.getMessage();
        }
    }

    @Override
    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}
