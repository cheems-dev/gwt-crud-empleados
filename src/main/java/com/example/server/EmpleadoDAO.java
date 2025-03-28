package com.example.server;

import com.example.shared.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {
  private static final String DB_URL = "jdbc:postgresql://postgres:5432/empleados_db";
  private static final String DB_USER = "postgres";
  private static final String DB_PASSWORD = "postgres";

  // Get connection to database
  private Connection getConnection() throws SQLException {
    try {
      Class.forName("org.postgresql.Driver");
      return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    } catch (ClassNotFoundException e) {
      throw new SQLException("PostgreSQL JDBC Driver not found", e);
    }
  }

  // Get all employees
  public List<Empleado> getAllEmpleados() throws SQLException {
    List<Empleado> empleados = new ArrayList<>();
    String sql = "SELECT id, nombre, apellido, email, telefono, salario FROM empleados";

    try (Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        Empleado empleado = new Empleado();
        empleado.setId(rs.getInt("id"));
        empleado.setNombre(rs.getString("nombre"));
        empleado.setApellido(rs.getString("apellido"));
        empleado.setEmail(rs.getString("email"));
        empleado.setTelefono(rs.getString("telefono"));
        empleado.setSalario(rs.getDouble("salario"));

        empleados.add(empleado);
      }
    }

    return empleados;
  }

  // Add a new employee
  public void addEmpleado(Empleado empleado) throws SQLException {
    String sql = "INSERT INTO empleados (nombre, apellido, email, telefono, salario) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, empleado.getNombre());
      pstmt.setString(2, empleado.getApellido());
      pstmt.setString(3, empleado.getEmail());
      pstmt.setString(4, empleado.getTelefono());
      pstmt.setDouble(5, empleado.getSalario());

      pstmt.executeUpdate();
    }
  }

  // Update an existing employee
  public void updateEmpleado(Empleado empleado) throws SQLException {
    String sql = "UPDATE empleados SET nombre = ?, apellido = ?, email = ?, telefono = ?, salario = ? WHERE id = ?";

    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, empleado.getNombre());
      pstmt.setString(2, empleado.getApellido());
      pstmt.setString(3, empleado.getEmail());
      pstmt.setString(4, empleado.getTelefono());
      pstmt.setDouble(5, empleado.getSalario());
      pstmt.setInt(6, empleado.getId());

      pstmt.executeUpdate();
    }
  }

  // Delete an employee
  public void deleteEmpleado(int id) throws SQLException {
    String sql = "DELETE FROM empleados WHERE id = ?";

    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, id);
      pstmt.executeUpdate();
    }
  }
}