package com.softwareengineering;

import android.content.Context;

/**
 * Factory para o repositorio do carro
 * 
 * @author ricardo
 *
 */
public class RepositorioFactory {

  private static RepositorioCarro repositorio;

  public static RepositorioCarro getRepositorioCarro(Context context) {
    if (repositorio == null) {
      //repositorio = new RepositorioCarroFake();
      repositorio = new RepositorioCarroSQLite(context);
    }
    return repositorio;
  }
}
