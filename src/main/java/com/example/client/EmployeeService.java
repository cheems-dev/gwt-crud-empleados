package com.example.client;

import java.util.List;

import com.example.shared.Employee;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("employee")
public interface EmployeeService extends RemoteService {
  List<Employee> getEmployees();

  void addEmployee(Employee employee);

  void updateEmployee(Employee employee);

  void deleteEmployee(int id);
}