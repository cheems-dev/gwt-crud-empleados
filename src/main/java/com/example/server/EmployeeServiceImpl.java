package com.example.server;

import com.example.client.EmployeeService;
import com.example.shared.Empleado;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeServiceImpl extends RemoteServiceServlet implements EmployeeService {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class.getName());

  private final EmpleadoDAO empleadoDAO = new EmpleadoDAO();

  @Override
  public List<Empleado> getEmpleados() {
    try {
      return empleadoDAO.getAllEmpleados();
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Error retrieving employees", e);
      throw new RuntimeException("Error retrieving employees: " + e.getMessage());
    }
  }

  @Override
  public void addEmpleado(Empleado empleado) {
    try {
      empleadoDAO.addEmpleado(empleado);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Error adding employee", e);
      throw new RuntimeException("Error adding employee: " + e.getMessage());
    }
  }

  @Override
  public void updateEmpleado(Empleado empleado) {
    try {
      empleadoDAO.updateEmpleado(empleado);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Error updating employee", e);
      throw new RuntimeException("Error updating employee: " + e.getMessage());
    }
  }

  @Override
  public void deleteEmpleado(int id) {
    try {
      empleadoDAO.deleteEmpleado(id);
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Error deleting employee", e);
      throw new RuntimeException("Error deleting employee: " + e.getMessage());
    }
  }
}