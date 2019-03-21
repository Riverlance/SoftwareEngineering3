package com.softwareengineering;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Activity que utiliza o TableLayout para editar o carro
 *
 * @author rlecheta
 *
 */
public class FormularioCarro extends Activity {
  private static final String CATEGORIA = "javamagazine";
  static final int RESULT_ERRO = 2;

  // Campos texto
  private EditText campoNome;
  private EditText campoPlaca;
  private EditText campoAno;
  private Long id;
  private RepositorioCarro repositorioCarro;
  private ImageView campoImagem;
  private byte[] byteArray;

  @Override
  protected void onCreate(Bundle icicle) {
    super.onCreate(icicle);

    repositorioCarro = RepositorioFactory.getRepositorioCarro(this);

    setContentView(R.layout.formulario_carro);

    // Acessa os campos da tela
    campoNome = (EditText) findViewById(R.id.campoNome);
    campoPlaca = (EditText) findViewById(R.id.campoPlaca);
    campoAno = (EditText) findViewById(R.id.campoAno);
    campoImagem = (ImageView) findViewById(R.id.imagem);

    id = null;

    Bundle extras = getIntent().getExtras();
    // Se for para Editar, recuperar os valores ...
    if (extras != null) {
      id = extras.getLong("id");

      if (id != null) {
        // eh uma edicao, busca o carro...
        Carro c = repositorioCarro.getCarro(id);

        if (c == null) {
          Toast.makeText(this, "Carro nao  o encontrado: " + id,
              Toast.LENGTH_SHORT);
        }
        else {
          campoNome.setText(c.getNome());
          campoPlaca.setText(c.getPlaca());
          campoAno.setText(String.valueOf(c.getAno()));
          campoImagem.setImageBitmap(c.getBitmap());
        }
      }
      else {
        Toast.makeText(this, "Informe o id do carro.", Toast.LENGTH_SHORT);
      }
    }

    // Listener para salvar o carro
    ImageButton btSalvar = (ImageButton) findViewById(R.id.btSalvar);
    btSalvar.setOnClickListener(new OnClickListener() {
      public void onClick(View view) {
        Carro carro = popularCarro();
        salvar(carro);
      }
    });

    ImageButton btExcluir = (ImageButton) findViewById(R.id.btExcluir);
    if (id == null) {
      // Se id estah nulo, nao pode excluir
      btExcluir.setVisibility(View.INVISIBLE);
    }
    else {
      // Listener para excluir o carro
      btExcluir.setOnClickListener(new OnClickListener() {
        public void onClick(View view) {
          Carro carro = repositorioCarro.getCarro(id);
          excluir(carro);
        }
      });
    }

    // Exibe a camera
    ImageButton btCamera = (ImageButton) findViewById(R.id.btFoto);
    btCamera.setOnClickListener(new OnClickListener() {
      public void onClick(View view) {
        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(i, 0);
      }
    });
  }

  // Quando a camera retornar, recupera a foto (no emulador apenas uma imagem de exemplo)
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    super.onActivityResult(requestCode, resultCode, data);

    if (data != null) {
      Bundle bundle = data.getExtras();
      // Recupera o Bitmap retornado pela camera
      Bitmap bitmap = (Bitmap) bundle.get("data");
      // Atualiza a imagem na tela
      campoImagem.setImageBitmap(bitmap);
      try {
        // Salva o array de bytes
        ByteArrayOutputStream bArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bArray);
        bArray.flush();
        bArray.close();
        this.byteArray = bArray.toByteArray();
      }
      catch (IOException ex) {
        Log.e(CATEGORIA, ex.getMessage());
      }
    }
  }

  private Carro popularCarro() {
    Carro carro = id != null ? repositorioCarro.getCarro(id) : new Carro();

    carro.setNome(campoNome.getText().toString());
    carro.setPlaca(campoPlaca.getText().toString());
    carro.setAno(Integer.parseInt(campoAno.getText().toString()));

    // Popula a imagem da foto
    if (byteArray != null) {
      carro.setImagem(byteArray);
    }

    return carro;
  }

  public void salvar(final Carro carro) {
    final ProgressDialog dialog = ProgressDialog.show(this, "Aguarde",
        "Salvando carro, por aguarde...", false, true);
    new Thread() {
      @Override
      public void run() {
        // Salvar
        boolean ok = repositorioCarro.salvar(carro);

        // OK
        setResult(ok ? Activity.RESULT_OK : RESULT_ERRO, new Intent());

        // Fecha a tela
        finish();

        dialog.dismiss();
      }
    }.start();
  }

  public void excluir(final Carro carro) {
    final ProgressDialog dialog = ProgressDialog.show(this, "Aguarde",
        "Excluindo carro, por aguarde...", false, true);
    new Thread() {
      @Override
      public void run() {
        // Excluir
        boolean ok = repositorioCarro.deletar(carro);

        // OK
        setResult(ok ? Activity.RESULT_OK : RESULT_ERRO, new Intent());

        // Fecha a tela
        finish();

        dialog.dismiss();
      }
    }.start();
  }
}
