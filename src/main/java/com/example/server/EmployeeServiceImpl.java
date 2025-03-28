package com.example.server;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.client.EmployeeService;
import com.example.shared.Employee;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EmployeeServiceImpl extends RemoteServiceServlet implements EmployeeService {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class.getName());

  private final EmployeeDAO employeeDAO = new EmployeeDAO();

  @Override
  public List<Employee> getEmployees() {
    try {
      return employeeDAO.getAllEmployees();
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Error retrieving employees", e);
      throw new RuntimeException("Error retrieving employees: " + e.getMessage());
    }
  }

  @Override
  public void addEmployee(Employee employee) {
    try {
      employeeDAO.addEmployee(employee);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Error adding employee", e);
      throw new RuntimeException("Error adding employee: " + e.getMessage());
    }
  }

  @Override
  public void updateEmployee(Employee employee) {
    try {
      employeeDAO.updateEmployee(employee);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Error updating employee", e);
      throw new RuntimeException("Error updating employee: " + e.getMessage());
    }
  }

  @Override
  public void deleteEmployee(int id) {
    try {
      employeeDAO.deleteEmployee(id);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Error deleting employee", e);
      throw new RuntimeException("Error deleting employee: " + e.getMessage());
    }
  }
}