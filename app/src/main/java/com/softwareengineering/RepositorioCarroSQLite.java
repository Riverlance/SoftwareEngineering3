package com.softwareengineering;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SQLiteHandler Implementation
 * 
 * @author River
 *
 */
public class RepositorioCarroSQLite implements RepositorioCarro {
  private static final Map<Long, Carro> map = new HashMap<>();
  private static long nextId = 0;

  private static SQLiteHandler dbHandler;
  private static String DB_TABLE = "cars";

  public RepositorioCarroSQLite(Context context) {
    dbHandler = SQLiteHandler.getInstance(context);

    // Create table
    SQLiteDatabase db = dbHandler.getWritableDatabase();
    db.execSQL(String.format("CREATE TABLE IF NOT EXISTS `%s` (`id` int(11) NOT NULL, `name` varchar(255) NOT NULL, `plate` varchar(255) NOT NULL, `year` int(11) NOT NULL, `image` longblob) ;", DB_TABLE));
    db.close();

    // Load data from db
    carregar();

    // Add default values
    /*
    salvar(new Carro("Carro 1", "ABC-1011", 2009));
    salvar(new Carro("Carro 2", "ABC-1012", 2009));
    salvar(new Carro("Carro 3", "ABC-1013", 2009));
    salvar(new Carro("Carro 4", "ABC-1014", 2009));
    salvar(new Carro("Carro 5", "ABC-1015", 2009));
    salvar(new Carro("Carro 6", "ABC-1011", 2009));
    salvar(new Carro("Carro 7", "ABC-1012", 2009));
    salvar(new Carro("Carro 8", "ABC-1013", 2009));
    salvar(new Carro("Carro 9", "ABC-1014", 2009));
    salvar(new Carro("Carro 10", "ABC-1015", 2009));
    */
  }

  @Override
  public Carro getCarro(Long id) {
    return map.get(id);
  }

  @Override
  public Carro buscarCarroPorNome(String nome) {
    List<Carro> carros = listarCarros();
    for (Carro carro : carros) {
      if (carro.getNome().equals(nome)) { return carro; }
    }
    return null;
  }

  @Override
  public boolean deletar(Carro carro) {
    // Remove from map
    map.remove(carro.getId());

    boolean ret = false;

    // Remove from db
    SQLiteDatabase db = dbHandler.getWritableDatabase();
    ret = db.delete(DB_TABLE, String.format("`id` = %d", carro.getId()), null) > 0;

    // Close
    db.close();

    return ret;
  }

  @Override
  public List<Carro> listarCarros() {
    Collection<Carro> carros = map.values();
    List<Carro> lista = new ArrayList<>(carros);
    return lista;
  }

  @Override
  public boolean salvar(Carro carro) {
    System.out.println("salvar");

    /* Put on map */

    if (carro.getId() == null) {
      // Se eh um novo carro, incrementa um novo id
      carro.setId(++nextId);
    }
    // Atualiza o carro, ou insere um novo com o proximo id
    map.put(carro.getId(), carro);

    /* Put on db */

    SQLiteDatabase dbWriter = dbHandler.getWritableDatabase();
    SQLiteDatabase dbReader = dbHandler.getReadableDatabase();
    ContentValues contentValues = new ContentValues();

    Long id = carro.getId();
    contentValues.put("id", id);
    contentValues.put("name", carro.getNome());
    contentValues.put("plate", carro.getPlaca());
    contentValues.put("year", carro.getAno());
    contentValues.put("image", carro.getImagem());

    boolean ret = false;
    // If not exists on db
    if (dbReader.rawQuery(String.format("SELECT `id` FROM `%s` WHERE `id` = %d ;", DB_TABLE, id), null).getCount() == -1) {
      // Update
      ret = dbWriter.update(DB_TABLE, contentValues, String.format("`id` = %d", id), null) > 0;

    // If exists on db
    } else {
      // Insert
      ret = dbWriter.insert(DB_TABLE, null, contentValues) != -1;
    }

    // Close
    dbWriter.close();
    dbReader.close();

    return ret;
  }

  // Load data from db to map
  public void carregar() {
    SQLiteDatabase db = dbHandler.getReadableDatabase();
    Cursor cursor = db.rawQuery(String.format("SELECT * FROM `%s` ;", DB_TABLE), null);

    // Clear actual map
    map.clear();

    // Fill map according to table
    if (cursor.moveToFirst()) {
      do {
        Carro carro = new Carro();
        carro.setId(cursor.getLong(0));
        carro.setNome(cursor.getString(1));
        carro.setPlaca(cursor.getString(2));
        carro.setAno(cursor.getInt(3));
        carro.setImagem(cursor.getBlob(4));
        map.put(carro.getId(), carro);
      } while (cursor.moveToNext());
    }

    // Close
    cursor.close();
    db.close();
  }
}
