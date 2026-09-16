package com.siemens.pipeline.web;
// BUG: ThreadLocal estatico - se ninguem chamar remove(), o valor "cola-se"
// a thread. Como o Tomcat REUTILIZA threads de um pool, o pedido seguinte
// (de outro utilizador!) pode herdar o "currentUser" do pedido anterior.
public class RequestContext {
  private static final ThreadLocal<String> currentUser = new ThreadLocal<>();
  public static void set(String user) { currentUser.set(user); }
  public static String get() { return currentUser.get(); }
  // falta um remove()
}
