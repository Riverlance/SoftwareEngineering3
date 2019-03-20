package com.softwareengineering.softwareengineering3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Buscar o Carro.
 * 
 * @author rlecheta
 * 
 */
public class BuscarCarro extends Activity implements OnClickListener {
  private RepositorioCarro repositorioCarro;

  @Override
  protected void onCreate(Bundle icicle) {
    super.onCreate(icicle);

    repositorioCarro = RepositorioFactory.getRepositorioCarro();

    setContentView(R.layout.form_buscar_carro);

    Button btBuscar = (Button) findViewById(R.id.btBuscar);
    btBuscar.setOnClickListener(this);
  }

  /**
   * @see android.view.View.OnClickListener#onClick(android.view.View)
   */
  public void onClick(View view) {

    EditText nome = (EditText) findViewById(R.id.campoNome);
    EditText placa = (EditText) findViewById(R.id.campoPlaca);
    EditText ano = (EditText) findViewById(R.id.campoAno);
    ImageView imagem = (ImageView) findViewById(R.id.imagem);

    // Recupera o nome do carro
    String nomeCarro = nome.getText().toString();

    // Busca o carro pelo nome
    Carro carro = buscarCarro(nomeCarro);

    if (carro != null) {
      // Atualiza os campos com o resultado
      placa.setText(carro.getPlaca());
      ano.setText(String.valueOf(carro.getAno()));
      // Atualiza a imagem (bitmap)
      imagem.setImageBitmap(carro.getBitmap());
    }
    else {
      // Limpa os campos
      placa.setText("");
      ano.setText("");

      // Exibe alerta
      Toast.makeText(BuscarCarro.this, "Nenhum carro encontrado",
          Toast.LENGTH_SHORT).show();
    }
  }

  // Busca um carro pelo nome
  protected Carro buscarCarro(String nomeCarro) {
    Carro carro = repositorioCarro.buscarCarroPorNome(nomeCarro);
    return carro;
  }

  @Override
  protected void onPause() {
    super.onPause();
    // Cancela para nao ficar nada pendente na tela
    setResult(RESULT_CANCELED);

    // Fecha a tela
    finish();
  }
}