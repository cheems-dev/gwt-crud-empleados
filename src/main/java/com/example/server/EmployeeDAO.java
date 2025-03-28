package com.example.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.shared.Employee;

public class EmployeeDAO {
  private static final String DB_URL = "jdbc:postgresql://postgres:5432/empleados_db";
  private static final String DB_USER = "postgres";
  private static final String DB_PASSWORD = "postgres";

  // Get all employees
  public List<Employee> getAllEmployees() throws SQLException {
    List<Employee> employees = new ArrayList<>();
    String sql = "SELECT id, nombre, apellido, email, telefono, salario FROM empleados";

    try (Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setNombre(rs.getString("nombre"));
        employee.setApellido(rs.getString("apellido"));
        employee.setEmail(rs.getString("email"));
        employee.setTelefono(rs.getString("telefono"));
        employee.setSalario(rs.getDouble("salario"));

        employees.add(employee);
      }
    }

    return employees;
  }

  // Add a new employee
  public void addEmployee(Employee employee) throws SQLException {
    String sql = "INSERT INTO empleados (nombre, apellido, email, telefono, salario) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, employee.getNombre());
      pstmt.setString(2, employee.getApellido());
      pstmt.setString(3, employee.getEmail());
      pstmt.setString(4, employee.getTelefono());
      pstmt.setDouble(5, employee.getSalario());

      pstmt.executeUpdate();
    }
  }

  // Update an existing employee
  public void updateEmployee(Employee employee) throws SQLException {
    String sql = "UPDATE empleados SET nombre = ?, apellido = ?, email = ?, telefono = ?, salario = ? WHERE id = ?";

    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, employee.getNombre());
      pstmt.setString(2, employee.getApellido());
      pstmt.setString(3, employee.getEmail());
      pstmt.setString(4, employee.getTelefono());
      pstmt.setDouble(5, employee.getSalario());
      pstmt.setInt(6, employee.getId());

      pstmt.executeUpdate();
    }
  }

  // Delete an employee
  public void deleteEmployee(int id) throws SQLException {
    String sql = "DELETE FROM empleados WHERE id = ?";

    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, id);
      pstmt.executeUpdate();
    }
  }

  // Get connection to database
  private Connection getConnection() throws SQLException {
    try {
      Class.forName("org.postgresql.Driver");
      return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new SQLException("PostgreSQL JDBC Driver not found", e);
    }
  }
}