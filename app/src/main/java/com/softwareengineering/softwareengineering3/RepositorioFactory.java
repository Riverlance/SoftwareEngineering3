package com.softwareengineering.softwareengineering3;

/**
 * Factory para o repositorio do carro
 * 
 * @author ricardo
 *
 */
public class RepositorioFactory {

  private static RepositorioCarro repositorio;

  public static RepositorioCarro getRepositorioCarro() {
    if (repositorio == null) {
      repositorio = new RepositorioCarroFake();
    }
    return repositorio;
  }
}
