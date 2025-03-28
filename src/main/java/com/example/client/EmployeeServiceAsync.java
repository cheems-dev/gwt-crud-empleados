package com.example.client;

import com.example.shared.Empleado;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface EmployeeServiceAsync {
  void getEmpleados(AsyncCallback<List<Empleado>> callback);

  void addEmpleado(Empleado empleado, AsyncCallback<Void> callback);

  void updateEmpleado(Empleado empleado, AsyncCallback<Void> callback);

  void deleteEmpleado(int id, AsyncCallback<Void> callback);
}