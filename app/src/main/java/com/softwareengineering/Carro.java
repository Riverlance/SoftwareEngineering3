package com.softwareengineering;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Entidade para armazenar os valores de Carro
 * 
 * @author ricardo
 * 
 */
public class Carro {

  private Long id;
  private String nome;
  private String placa;
  private int ano;
  private byte[] imagem;

  public Carro() {}

  public Carro(String nome, String placa, int ano) {
    super();
    this.nome = nome;
    this.placa = placa;
    this.ano = ano;
  }

  public Bitmap getBitmap() {
    if(imagem == null){
      return null;
    }
    // Cria o Bitmap necessario para exibir no ImageView
    Bitmap bitmap = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
    return bitmap;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getPlaca() {
    return placa;
  }

  public void setPlaca(String placa) {
    this.placa = placa;
  }

  public int getAno() {
    return ano;
  }

  public void setAno(int ano) {
    this.ano = ano;
  }

  public byte[] getImagem() {
    return imagem;
  }

  public void setImagem(byte[] imagem) {
    this.imagem = imagem;
  }

  @Override
  public String toString() {
    return "Nome: " + nome + ", Placa: " + placa + ", Ano: " + ano;
  }
}
