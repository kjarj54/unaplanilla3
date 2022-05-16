/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanilla3.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.unaplanilla3.model.EmpleadoDto;
import cr.ac.una.unaplanilla3.service.EmpleadoService;
import cr.ac.una.unaplanilla3.util.BindingUtils;
import cr.ac.una.unaplanilla3.util.FlowController;
import cr.ac.una.unaplanilla3.util.Formato;
import cr.ac.una.unaplanilla3.util.Mensaje;
import cr.ac.una.unaplanilla3.util.Respuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author cbcar
 */
public class EmpleadosViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtPApellido;
    @FXML
    private JFXTextField txtSApellido;
    @FXML
    private JFXTextField txtCedula;
    @FXML
    private JFXRadioButton rdbMasculino;
    @FXML
    private ToggleGroup tggGenero;
    @FXML
    private JFXRadioButton rdbFemenino;
    @FXML
    private JFXCheckBox chkAdministrador;
    @FXML
    private JFXCheckBox chkActivo;
    @FXML
    private JFXDatePicker dtpFIngreso;
    @FXML
    private JFXDatePicker dtpFSalida;
    @FXML
    private JFXTextField txtCorreo;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtClave;
    @FXML
    private JFXButton btnNuevo;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnGuardar;
    
    private EmpleadoDto empleadoDto;
    List<Node> requeridos = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rdbFemenino.setUserData("F");
        rdbMasculino.setUserData("M");
        txtId.setTextFormatter(Formato.getInstance().integerFormat());
        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(30));
        txtPApellido.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtSApellido.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtCedula.setTextFormatter(Formato.getInstance().cedulaFormat(40));
        txtCorreo.setTextFormatter(Formato.getInstance().maxLengthFormat(80));
        txtUsuario.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtClave.setTextFormatter(Formato.getInstance().maxLengthFormat(8));
        empleadoDto = new EmpleadoDto();
        nuevoEmpleado();
        indicarRequeridos();
    }    

    @Override
    public void initialize() {
        
    }
    
    public void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombre, txtCedula, txtPApellido, dtpFIngreso));
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof JFXTextField && !((JFXTextField) node).validate()) {
                if (validos) {
                    invalidos += ((JFXTextField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXTextField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXPasswordField && !((JFXPasswordField) node).validate()) {
                if (validos) {
                    invalidos += ((JFXPasswordField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXPasswordField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXDatePicker && ((JFXDatePicker) node).getValue() == null) {
                if (validos) {
                    invalidos += ((JFXDatePicker) node).getAccessibleText();
                } else {
                    invalidos += "," + ((JFXDatePicker) node).getAccessibleText();
                }
                validos = false;
            } else if (node instanceof JFXComboBox && ((JFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                if (validos) {
                    invalidos += ((JFXComboBox) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXComboBox) node).getPromptText();
                }
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }
    
    private void bindEmpleado(Boolean nuevo) {
        if(!nuevo){
            txtId.textProperty().bind(empleadoDto.id);
        }
        txtCedula.textProperty().bindBidirectional(empleadoDto.cedula);
        txtNombre.textProperty().bindBidirectional(empleadoDto.nombre);
        txtPApellido.textProperty().bindBidirectional(empleadoDto.pApellido);
        txtSApellido.textProperty().bindBidirectional(empleadoDto.sApellido);
        txtUsuario.textProperty().bindBidirectional(empleadoDto.usuario);
        txtClave.textProperty().bindBidirectional(empleadoDto.clave);
        txtCorreo.textProperty().bindBidirectional(empleadoDto.correo);
        dtpFIngreso.valueProperty().bindBidirectional(empleadoDto.fIngreso);
        dtpFSalida.valueProperty().bindBidirectional(empleadoDto.fSalida);
        chkActivo.selectedProperty().bindBidirectional(empleadoDto.estado);
        chkAdministrador.selectedProperty().bindBidirectional(empleadoDto.administrador);
        BindingUtils.bindToggleGroupToProperty(tggGenero, empleadoDto.genero);
    }

    private void unbindEmpleado() {
        txtId.textProperty().unbind();
        txtCedula.textProperty().unbindBidirectional(empleadoDto.cedula);
        txtNombre.textProperty().unbindBidirectional(empleadoDto.nombre);
        txtPApellido.textProperty().unbindBidirectional(empleadoDto.pApellido);
        txtSApellido.textProperty().unbindBidirectional(empleadoDto.sApellido);
        txtUsuario.textProperty().unbindBidirectional(empleadoDto.usuario);
        txtClave.textProperty().unbindBidirectional(empleadoDto.clave);
        txtCorreo.textProperty().unbindBidirectional(empleadoDto.correo);
        dtpFIngreso.valueProperty().unbindBidirectional(empleadoDto.fIngreso);
        dtpFSalida.valueProperty().unbindBidirectional(empleadoDto.fSalida);
        chkActivo.selectedProperty().unbindBidirectional(empleadoDto.estado);
        chkAdministrador.selectedProperty().unbindBidirectional(empleadoDto.administrador);
        BindingUtils.unbindToggleGroupToProperty(tggGenero, empleadoDto.genero);
    }
    
    private void nuevoEmpleado() {
        unbindEmpleado();
        empleadoDto = new EmpleadoDto();
        bindEmpleado(true);
        validarAdministrador();
        txtId.clear();
        txtId.requestFocus();
    }
    
    private void validarAdministrador() {
        if (chkAdministrador.isSelected()) {
            requeridos.addAll(Arrays.asList(txtUsuario, txtClave));
            txtUsuario.setDisable(false);
            txtClave.setDisable(false);
        } else {
            requeridos.removeAll(Arrays.asList(txtUsuario, txtClave));
            txtUsuario.validate();
            txtClave.validate();
            txtUsuario.clear();
            txtUsuario.setDisable(true);
            txtClave.clear();
            txtClave.setDisable(true);
        }
    }
    
    private void cargarEmpleado(Long id) {
        EmpleadoService service = new EmpleadoService();
        Respuesta respuesta = service.getEmpleado(id);

        if (respuesta.getEstado()) {
            unbindEmpleado();
            empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
            bindEmpleado(false);
            validarAdministrador();
            validarRequeridos();
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar empleado", getStage(), respuesta.getMensaje());
        }
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar empleado", getStage(), "¿Esta seguro que desea limpiar el registro?")) {
            nuevoEmpleado();
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        BusquedaViewController busquedaController = (BusquedaViewController) FlowController.getInstance().getController("BusquedaView");
        busquedaController.busquedaEmpleados();
        FlowController.getInstance().goViewInWindowModal("BusquedaView", getStage(),true);
        EmpleadoDto empleadoDto = (EmpleadoDto) busquedaController.getResultado();
        if (empleadoDto != null) {
            cargarEmpleado(empleadoDto.getId());
        }
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try {
            if (empleadoDto.getId()== null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar empleado", getStage(), "Debe cargar el empleado que desea eliminar.");
            } else {

                EmpleadoService service = new EmpleadoService();
                Respuesta respuesta = service.eliminarEmpleado(empleadoDto.getId());
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar empleado", getStage(), respuesta.getMensaje());
                } else {
                    nuevoEmpleado();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar empleado", getStage(), "Empleado eliminado correctamente.");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosViewController.class.getName()).log(Level.SEVERE, "Error eliminando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar empleado", getStage(), "Ocurrio un error eliminando el empleado.");
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), invalidos);
            } else {

                EmpleadoService service = new EmpleadoService();
                Respuesta respuesta = service.guardarEmpleado(empleadoDto);
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), respuesta.getMensaje());
                } else {
                    unbindEmpleado();
                    empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
                    bindEmpleado(false);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar empleado", getStage(), "Empleado actualizado correctamente.");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosViewController.class.getName()).log(Level.SEVERE, "Error guardando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), "Ocurrio un error guardando el empleado.");
        }
    }

    @FXML
    private void onKeyPressedTxtId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtId.getText().isEmpty()) {
            cargarEmpleado(Long.valueOf(txtId.getText()));
        }
    }

    @FXML
    private void onActionChkAdministrador(ActionEvent event) {
        validarAdministrador();
    }
    
}
