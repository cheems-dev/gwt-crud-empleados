package com.example.client;

import com.example.shared.Empleado;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("employee")
public interface EmployeeService extends RemoteService {
  List<Empleado> getEmpleados();

  void addEmpleado(Empleado empleado);

  void updateEmpleado(Empleado empleado);

  void deleteEmpleado(int id);
}