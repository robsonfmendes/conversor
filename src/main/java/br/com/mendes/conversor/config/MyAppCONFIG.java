package br.com.mendes.conversor.config;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

import br.com.mendes.uploaded.controller.ConversorVideoController;

public class MyAppCONFIG extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> s = new HashSet<Class<?>>();
    s.add(ConversorVideoController.class);
    return s;
  }
}
