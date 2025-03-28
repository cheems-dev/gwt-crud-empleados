package com.example.shared;

import java.io.Serializable;

public class Empleado implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer id;
  private String nombre;
  private String apellido;
  private String email;
  private String telefono;
  private Double salario;

  // Default constructor required for GWT serialization
  public Empleado() {
  }

  public Empleado(Integer id, String nombre, String apellido, String email, String telefono, Double salario) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
    this.telefono = telefono;
    this.salario = salario;
  }

  // Getters and setters
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public Double getSalario() {
    return salario;
  }

  public void setSalario(Double salario) {
    this.salario = salario;
  }
}