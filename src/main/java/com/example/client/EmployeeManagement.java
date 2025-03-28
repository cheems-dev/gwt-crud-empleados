package com.example.client;

import java.util.List;

import com.example.shared.Employee;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class EmployeeManagement implements EntryPoint {
  private final EmployeeServiceAsync employeeService = GWT.create(EmployeeService.class);

  private FlexTable employeeTable = new FlexTable();
  private TextBox nombreTextBox = new TextBox();
  private TextBox apellidoTextBox = new TextBox();
  private TextBox emailTextBox = new TextBox();
  private TextBox telefonoTextBox = new TextBox();
  private TextBox salarioTextBox = new TextBox();
  private Button addButton = new Button("AGREGAR");
  private Button updateButton = new Button("ACTUALIZAR");
  private Button cancelButton = new Button("CANCELAR");
  private Button showFormButton = new Button("NUEVO EMPLEADO");
  private Label notificationLabel = new Label();

  // Modal dialog
  private DialogBox dialogBox = new DialogBox();
  private VerticalPanel dialogContents = new VerticalPanel();
  private Label dialogTitle = new Label();
  private FlowPanel formPanel = new FlowPanel();

  private Employee currentEmployee = null;

  @Override
  public void onModuleLoad() {
    // Add button container
    FlowPanel addButtonContainer = new FlowPanel();
    addButtonContainer.addStyleName("addButtonContainer");
    showFormButton.addStyleName("addButton");
    addButtonContainer.add(showFormButton);

    // Setup dialog
    setupDialog();

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
    RootPanel.get("addButtonContainer").add(addButtonContainer);
    RootPanel.get("employeeTableContainer").add(employeeTable);

    // Show form button click handler
    showFormButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        dialogTitle.setText("Agregar Empleado");
        showDialog(true);
      }
    });

    // Add button click handler
    addButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        if (validateForm()) {
          Employee employee = new Employee();
          employee.setNombre(nombreTextBox.getText());
          employee.setApellido(apellidoTextBox.getText());
          employee.setEmail(emailTextBox.getText());
          employee.setTelefono(telefonoTextBox.getText());
          employee.setSalario(Double.parseDouble(salarioTextBox.getText()));

          employeeService.addEmployee(employee, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
              showNotification("Error al agregar empleado: " + caught.getMessage(), false);
            }

            @Override
            public void onSuccess(Void result) {
              showNotification("Empleado agregado exitosamente", true);
              clearForm();
              loadEmployees();
              dialogBox.hide();
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

          employeeService.updateEmployee(currentEmployee, new AsyncCallback<Void>() {
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
              dialogBox.hide();
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
        dialogBox.hide();
      }
    });

    // Load employees
    loadEmployees();
  }

  private void setupDialog() {
    // Configure dialog box
    dialogBox.setText("Empleado");
    dialogBox.setAnimationEnabled(true);
    dialogBox.setGlassEnabled(true);
    dialogBox.setGlassStyleName("gwt-PopupPanelGlass");

    // Dialog title
    dialogTitle.addStyleName("dialogTitle");

    // Botón de cierre
    Button closeButton = new Button("×");
    closeButton.addStyleName("dialogCloseButton");
    closeButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        clearForm();
        addButton.setVisible(true);
        updateButton.setVisible(false);
        cancelButton.setVisible(false);
        currentEmployee = null;
        dialogBox.hide();
      }
    });

    // Create form panel
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

    // Style buttons
    addButton.addStyleName("primaryButton");
    updateButton.addStyleName("updateButton");
    cancelButton.addStyleName("cancelButton");

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

    // Add elements to dialog
    FlowPanel headerPanel = new FlowPanel();
    headerPanel.addStyleName("dialogHeader");
    headerPanel.add(dialogTitle);

    dialogContents.add(headerPanel);
    dialogContents.add(formPanel);
    dialogContents.add(closeButton);
    dialogContents.addStyleName("dialogContents");

    dialogBox.setWidget(dialogContents);
  }

  private void showDialog(boolean isAddMode) {
    if (isAddMode) {
      addButton.setVisible(true);
      updateButton.setVisible(false);
      cancelButton.setVisible(false);
    } else {
      addButton.setVisible(false);
      updateButton.setVisible(true);
      cancelButton.setVisible(true);
    }
    dialogBox.center();
    dialogBox.show();
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
    employeeService.getEmployees(new AsyncCallback<List<Employee>>() {
      @Override
      public void onFailure(Throwable caught) {
        Window.alert("Error al cargar empleados: " + caught.getMessage());
      }

      @Override
      public void onSuccess(List<Employee> result) {
        // Clear table (except header row)
        while (employeeTable.getRowCount() > 1) {
          employeeTable.removeRow(1);
        }

        // Add employees to table
        for (int i = 0; i < result.size(); i++) {
          final Employee employee = result.get(i);
          int row = i + 1;

          employeeTable.setText(row, 0, employee.getId().toString());
          employeeTable.setText(row, 1, employee.getNombre());
          employeeTable.setText(row, 2, employee.getApellido());
          employeeTable.setText(row, 3, employee.getEmail());
          employeeTable.setText(row, 4, employee.getTelefono());
          employeeTable.setText(row, 5, employee.getSalario().toString());

          // Action buttons
          Button editButton = new Button("Editar");
          Button deleteButton = new Button("Borrar");

          editButton.addStyleName("actionButton");
          editButton.addStyleName("editButton");
          deleteButton.addStyleName("actionButton");
          deleteButton.addStyleName("deleteButton");

          HorizontalPanel actionPanel = new HorizontalPanel();
          actionPanel.add(editButton);
          actionPanel.add(deleteButton);

          employeeTable.setWidget(row, 6, actionPanel);

          // Edit button handler
          editButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
              currentEmployee = employee;
              nombreTextBox.setText(employee.getNombre());
              apellidoTextBox.setText(employee.getApellido());
              emailTextBox.setText(employee.getEmail());
              telefonoTextBox.setText(employee.getTelefono());
              salarioTextBox.setText(employee.getSalario().toString());

              dialogTitle.setText("Editar Empleado");
              showDialog(false);
            }
          });

          // Delete button handler
          deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
              if (Window.confirm("¿Está seguro que desea eliminar este empleado?")) {
                employeeService.deleteEmployee(employee.getId(), new AsyncCallback<Void>() {
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