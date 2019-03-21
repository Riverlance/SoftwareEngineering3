package com.softwareengineering;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Activity que demonstra o cadastro de carros:
 * 
 * - ListActivity: para listar os carros - RepositorioCarro para salvar os dados
 * no banco - Carro
 * 
 * @author rlecheta
 * 
 */
public class ListaCarrosSimples2 extends ListActivity {
  protected static final int ATUALIZAR = 1;
  protected static final int INSERIR_NOVO = 2;
  protected static final int BUSCAR = 3;

  private RepositorioCarro repositorio;

  private List<Carro> carros;

  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    repositorio = RepositorioFactory.getRepositorioCarro(this);
    // Atualiza a lista com os carros.
    atualizarLista();
  }

  // Atualiza a lista, fazendo uma requisicao HTTP no servidor web
  // Enquanto faz a requisicao, exibe uma janela de aguarde.
  private void atualizarLista() {
    // Pega a lista de carros e exibe na tela
    carros = repositorio.listarCarros();
    // Informa para a ListActivity qual o adaptador da lista
    setListAdapter(new CarroListAdapter(ListaCarrosSimples2.this, carros));
  }

  @Override
  // Cria os menus
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    menu.add(0, ATUALIZAR, 0, "Atualizar").setIcon(R.drawable.atualizar);
    menu.add(0, INSERIR_NOVO, 0, "Inserir Novo").setIcon(R.drawable.novo);
    menu.add(0, BUSCAR, 0, "Buscar").setIcon(R.drawable.buscar);
    return true;
  }

  @Override
  // Se selecionar um item do menu
  public boolean onMenuItemSelected(int featureId, MenuItem item) {
    // Clicou no menu
    switch (item.getItemId()) {
      case ATUALIZAR:
        // Atualiza a lista
        atualizarLista();
        break;
      case INSERIR_NOVO:
        // Abre a tela com o formulario para inserir um novo
        startActivityForResult(new Intent(this, FormularioCarro.class),
            INSERIR_NOVO);
        break;
      case BUSCAR:
        // Abre a tela para buscar o carro pelo nome
        startActivity(new Intent(this, BuscarCarro.class));
        break;
    }
    return true;
  }

  @Override
  // Se a tela do formulario de cadastro retornou, verifica o status e atualiza a lista
  protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
    super.onActivityResult(codigo, codigoRetorno, it);

    // Se a tela FormularioCarro retornar OK, atualiza o a lista
    if (codigoRetorno == Activity.RESULT_OK) {
      atualizarLista();
    }
    else if (codigoRetorno == FormularioCarro.RESULT_ERRO) {
      // Uma aplicacao real deveria recuperar o erro aqui.
      Toast.makeText(this, "Ocorrreu um erro na operacao.", Toast.LENGTH_LONG)
          .show();
    }
  }

  @Override
  // Se selecionar um carro da lista
  protected void onListItemClick(ListView l, View v, int indice, long id) {
    super.onListItemClick(l, v, indice, id);
    // Recupera o carro selecionado utilizando a posicao da lista
    Carro carro = carros.get(indice);
    // Cria a intent para abrir a tela do formulario
    Intent it = new Intent(this, FormularioCarro.class);
    // Passa o id do carro como parametro
    it.putExtra("id", carro.getId());
    // Abre a tela de edicao
    startActivityForResult(it, INSERIR_NOVO);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Liberar recursos aqui se necessario, como fechar alguma conexao
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_R) {
      // Se teclar 'R' a lista eh atualizada
      atualizarLista();
    }
    return super.onKeyDown(keyCode, event);
  }
}