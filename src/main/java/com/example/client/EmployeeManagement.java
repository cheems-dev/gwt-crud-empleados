package com.example.client;

import com.example.shared.Empleado;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.List;

public class EmployeeManagement implements EntryPoint {
  private final EmployeeServiceAsync employeeService = GWT.create(EmployeeService.class);

  private FlexTable employeeTable = new FlexTable();
  private TextBox nombreTextBox = new TextBox();
  private TextBox apellidoTextBox = new TextBox();
  private TextBox emailTextBox = new TextBox();
  private TextBox telefonoTextBox = new TextBox();
  private TextBox salarioTextBox = new TextBox();
  private Button addButton = new Button("Agregar");
  private Button updateButton = new Button("Actualizar");
  private Button cancelButton = new Button("Cancelar");
  private Label notificationLabel = new Label();

  private Empleado currentEmployee = null;

  @Override
  public void onModuleLoad() {
    // Create form panel
    FlowPanel formPanel = new FlowPanel();
    formPanel.addStyleName("employeeForm");

    // Form fields
    FlowPanel nombreField = createFormField("Nombre:", nombreTextBox);
    FlowPanel apellidoField = createFormField("Apellido:", apellidoTextBox);
    FlowPanel emailField = createFormField("Email:", emailTextBox);
    FlowPanel telefonoField = createFormField("Teléfono:", telefonoTextBox);
    FlowPanel salarioField = createFormField("Salario:", salarioTextBox);

    // Button panel
    HorizontalPanel buttonPanel = new HorizontalPanel();
    buttonPanel.addStyleName("buttonPanel");
    buttonPanel.add(addButton);
    buttonPanel.add(updateButton);
    buttonPanel.add(cancelButton);

    // Initially hide update button
    updateButton.setVisible(false);
    cancelButton.setVisible(false);

    // Add form elements to panel
    formPanel.add(nombreField);
    formPanel.add(apellidoField);
    formPanel.add(emailField);
    formPanel.add(telefonoField);
    formPanel.add(salarioField);
    formPanel.add(buttonPanel);
    formPanel.add(notificationLabel);

    // Setup table
    employeeTable.addStyleName("employeeTable");
    employeeTable.setText(0, 0, "ID");
    employeeTable.setText(0, 1, "Nombre");
    employeeTable.setText(0, 2, "Apellido");
    employeeTable.setText(0, 3, "Email");
    employeeTable.setText(0, 4, "Teléfono");
    employeeTable.setText(0, 5, "Salario");
    employeeTable.setText(0, 6, "Acciones");

    // Add widgets to the root panel
    RootPanel.get("employeeFormContainer").add(formPanel);
    RootPanel.get("employeeTableContainer").add(employeeTable);

    // Add button click handler
    addButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        if (validateForm()) {
          Empleado empleado = new Empleado();
          empleado.setNombre(nombreTextBox.getText());
          empleado.setApellido(apellidoTextBox.getText());
          empleado.setEmail(emailTextBox.getText());
          empleado.setTelefono(telefonoTextBox.getText());
          empleado.setSalario(Double.parseDouble(salarioTextBox.getText()));

          employeeService.addEmpleado(empleado, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
              showNotification("Error al agregar empleado: " + caught.getMessage(), false);
            }

            @Override
            public void onSuccess(Void result) {
              showNotification("Empleado agregado exitosamente", true);
              clearForm();
              loadEmployees();
            }
          });
        }
      }
    });

    // Update button click handler
    updateButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        if (validateForm() && currentEmployee != null) {
          currentEmployee.setNombre(nombreTextBox.getText());
          currentEmployee.setApellido(apellidoTextBox.getText());
          currentEmployee.setEmail(emailTextBox.getText());
          currentEmployee.setTelefono(telefonoTextBox.getText());
          currentEmployee.setSalario(Double.parseDouble(salarioTextBox.getText()));

          employeeService.updateEmpleado(currentEmployee, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
              showNotification("Error al actualizar empleado: " + caught.getMessage(), false);
            }

            @Override
            public void onSuccess(Void result) {
              showNotification("Empleado actualizado exitosamente", true);
              clearForm();
              loadEmployees();

              // Reset form to add mode
              addButton.setVisible(true);
              updateButton.setVisible(false);
              cancelButton.setVisible(false);
              currentEmployee = null;
            }
          });
        }
      }
    });

    // Cancel button click handler
    cancelButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        clearForm();
        addButton.setVisible(true);
        updateButton.setVisible(false);
        cancelButton.setVisible(false);
        currentEmployee = null;
      }
    });

    // Load employees
    loadEmployees();
  }

  private FlowPanel createFormField(String labelText, Widget widget) {
    FlowPanel panel = new FlowPanel();
    panel.addStyleName("formField");
    Label label = new Label(labelText);
    panel.add(label);
    panel.add(widget);
    return panel;
  }

  private void loadEmployees() {
    employeeService.getEmpleados(new AsyncCallback<List<Empleado>>() {
      @Override
      public void onFailure(Throwable caught) {
        Window.alert("Error al cargar empleados: " + caught.getMessage());
      }

      @Override
      public void onSuccess(List<Empleado> result) {
        // Clear table (except header row)
        while (employeeTable.getRowCount() > 1) {
          employeeTable.removeRow(1);
        }

        // Add employees to table
        for (int i = 0; i < result.size(); i++) {
          final Empleado empleado = result.get(i);
          int row = i + 1;

          employeeTable.setText(row, 0, empleado.getId().toString());
          employeeTable.setText(row, 1, empleado.getNombre());
          employeeTable.setText(row, 2, empleado.getApellido());
          employeeTable.setText(row, 3, empleado.getEmail());
          employeeTable.setText(row, 4, empleado.getTelefono());
          employeeTable.setText(row, 5, empleado.getSalario().toString());

          // Action buttons
          Button editButton = new Button("Editar");
          Button deleteButton = new Button("Eliminar");

          editButton.addStyleName("actionButton");
          deleteButton.addStyleName("actionButton");

          HorizontalPanel actionPanel = new HorizontalPanel();
          actionPanel.add(editButton);
          actionPanel.add(deleteButton);

          employeeTable.setWidget(row, 6, actionPanel);

          // Edit button handler
          editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
              currentEmployee = empleado;
              nombreTextBox.setText(empleado.getNombre());
              apellidoTextBox.setText(empleado.getApellido());
              emailTextBox.setText(empleado.getEmail());
              telefonoTextBox.setText(empleado.getTelefono());
              salarioTextBox.setText(empleado.getSalario().toString());

              addButton.setVisible(false);
              updateButton.setVisible(true);
              cancelButton.setVisible(true);
            }
          });

          // Delete button handler
          deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
              if (Window.confirm("¿Está seguro que desea eliminar este empleado?")) {
                employeeService.deleteEmpleado(empleado.getId(), new AsyncCallback<Void>() {
                  @Override
                  public void onFailure(Throwable caught) {
                    showNotification("Error al eliminar empleado: " + caught.getMessage(), false);
                  }

                  @Override
                  public void onSuccess(Void result) {
                    showNotification("Empleado eliminado exitosamente", true);
                    loadEmployees();
                  }
                });
              }
            }
          });
        }
      }
    });
  }

  private boolean validateForm() {
    if (nombreTextBox.getText().trim().isEmpty()) {
      showNotification("El nombre es obligatorio", false);
      return false;
    }

    if (apellidoTextBox.getText().trim().isEmpty()) {
      showNotification("El apellido es obligatorio", false);
      return false;
    }

    if (emailTextBox.getText().trim().isEmpty()) {
      showNotification("El email es obligatorio", false);
      return false;
    }

    try {
      Double.parseDouble(salarioTextBox.getText().trim());
    } catch (NumberFormatException e) {
      showNotification("El salario debe ser un número válido", false);
      return false;
    }

    return true;
  }

  private void clearForm() {
    nombreTextBox.setText("");
    apellidoTextBox.setText("");
    emailTextBox.setText("");
    telefonoTextBox.setText("");
    salarioTextBox.setText("");
    notificationLabel.setText("");
    notificationLabel.removeStyleName("success");
    notificationLabel.removeStyleName("error");
  }

  private void showNotification(String message, boolean isSuccess) {
    notificationLabel.setText(message);
    notificationLabel.removeStyleName("success");
    notificationLabel.removeStyleName("error");
    notificationLabel.addStyleName("notification");
    notificationLabel.addStyleName(isSuccess ? "success" : "error");
  }
}