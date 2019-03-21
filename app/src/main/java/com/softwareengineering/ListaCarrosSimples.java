package com.softwareengineering.softwareengineering3;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;

/**
 * Activity que demonstra o cadastro de carros:
 * 
 * - ListActivity: para listar os carros - RepositorioCarro para salvar os dados
 * no banco - Carro
 * 
 * @author rlecheta
 * 
 */
public class ListaCarrosSimples extends ListActivity {

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    RepositorioCarro repositorio = RepositorioFactory.getRepositorioCarro();
    List<Carro> carros = repositorio.listarCarros();
    ListAdapter adaptador = new CarroListAdapter(this, carros);
    setListAdapter(adaptador);
  }
}
