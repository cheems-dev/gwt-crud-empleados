package com.example.client;

import java.util.List;

import com.example.shared.Employee;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EmployeeServiceAsync {
  void getEmployees(AsyncCallback<List<Employee>> callback);

  void addEmployee(Employee employee, AsyncCallback<Void> callback);

  void updateEmployee(Employee employee, AsyncCallback<Void> callback);

  void deleteEmployee(int id, AsyncCallback<Void> callback);
}