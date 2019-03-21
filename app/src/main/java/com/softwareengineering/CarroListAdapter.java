package com.softwareengineering;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter customizado que exibe o layout definido em smile_row.xml
 * 
 * As imagens sao exibidas no widget ImageView
 * 
 * @author ricardo
 * 
 */
public class CarroListAdapter extends BaseAdapter {
  private Context context;
  private List<Carro> lista;

  public CarroListAdapter(Context context, List<Carro> lista) {
    this.context = context;
    this.lista = lista;
  }

  @Override
  public int getCount() {
    return lista.size();
  }

  @Override
  public Object getItem(int posicao) {
    return lista.get(posicao);
  }

  @Override
  public long getItemId(int posicao) {
    return posicao;
  }

  @Override
  public View getView(int posicao, View convertView, ViewGroup parent) {
    // Recupera o Carro da posicao atual
    Carro carro = lista.get(posicao);

    // LayoutInflater permite instanciar uma View a partir de um arquivo de layout XML
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    // Cria a view a partir do arquivo carro_linha_tabela.xml
    View view = inflater.inflate(R.layout.detalhes_carro, null);

    // Atualiza o valor dos campos da tela
    TextView nome = (TextView) view.findViewById(R.id.nome);
    nome.setText(carro.getNome());

    TextView placa = (TextView) view.findViewById(R.id.placa);
    placa.setText(carro.getPlaca());

    TextView ano = (TextView) view.findViewById(R.id.ano);
    ano.setText(String.valueOf(carro.getAno()));

    // Atualiza a imagem (bitmap)
    ImageView imagem = (ImageView) view.findViewById(R.id.imagem);
    imagem.setImageBitmap(carro.getBitmap());

    return view;
  }
}