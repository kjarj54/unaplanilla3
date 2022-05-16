/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanilla3.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import  com.jfoenix.controls.JFXTextField;
import cr.ac.una.unaplanilla3.model.EmpleadoDto;
import cr.ac.una.unaplanilla3.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla3.service.EmpleadoService;
import cr.ac.una.unaplanilla3.service.TipoPlanillaService;
import cr.ac.una.unaplanilla3.util.Formato;
import cr.ac.una.unaplanilla3.util.Mensaje;
import cr.ac.una.unaplanilla3.util.Respuesta;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author cbcar
 */
public class BusquedaViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private VBox vbxParametros;
    @FXML
    private JFXButton btnFiltrar;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableView tbvResultados;
    @FXML
    private JFXButton btnAceptar;
    
    private EventHandler<KeyEvent> keyEnter;
    
    private Object resultado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       keyEnter = (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnFiltrar.fire();
            }
        };
    }    

    @FXML
    private void OnMousePressedTbvResultados(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            onActionBtnAceptar(null);
        }
    }

    @FXML
    private void onActionBtnAceptar(ActionEvent event) {
        resultado = tbvResultados.getSelectionModel().getSelectedItem();
        //getStage().getScene().setRoot(new Pane());
        getStage().close();
    }
    
    public Object getResultado() {
        return resultado;
    }
    
    public void busquedaEmpleados() {
        try {
            lblTitulo.setText("Búsqueda de Empleados");
            
            JFXTextField txtCedula = new JFXTextField();
            txtCedula.setLabelFloat(true);
            txtCedula.setPromptText("Cédula");
            txtCedula.setOnKeyPressed(keyEnter);
            txtCedula.setTextFormatter(Formato.getInstance().cedulaFormat(40));
            
            JFXTextField txtNombre = new JFXTextField();
            txtNombre.setLabelFloat(true);
            txtNombre.setPromptText("Nombre");
            txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(30));
            txtNombre.setOnKeyPressed(keyEnter);
            
            JFXTextField txtPApellido = new JFXTextField();
            txtPApellido.setLabelFloat(true);
            txtPApellido.setPromptText("Primer Apellido");
            txtPApellido.setTextFormatter(Formato.getInstance().letrasFormat(15));
            txtPApellido.setOnKeyPressed(keyEnter);
            
            JFXTextField txtSApellido = new JFXTextField();
            txtSApellido.setLabelFloat(true);
            txtSApellido.setPromptText("Segundo Apellido");
            txtSApellido.setTextFormatter(Formato.getInstance().letrasFormat(15));
            txtSApellido.setOnKeyPressed(keyEnter);
            
            
            vbxParametros.getChildren().clear();
            vbxParametros.getChildren().add(txtCedula);
            vbxParametros.getChildren().add(txtNombre);
            vbxParametros.getChildren().add(txtPApellido);
            vbxParametros.getChildren().add(txtSApellido);

            tbvResultados.getColumns().clear();
            tbvResultados.getItems().clear();
            
            TableColumn<EmpleadoDto, String> tbcId = new TableColumn<>("Id");
            tbcId.setPrefWidth(0);
            tbcId.setCellValueFactory(cd -> cd.getValue().id);
            
            TableColumn<EmpleadoDto, String> tbcCedula = new TableColumn<>("Cédula");
            tbcCedula.setPrefWidth(100);
            tbcCedula.setCellValueFactory(cd -> cd.getValue().cedula);
            
            TableColumn<EmpleadoDto, String> tbcNombre = new TableColumn<>("Nombre");
            tbcNombre.setPrefWidth(150);
            tbcNombre.setCellValueFactory(cd -> cd.getValue().nombre);
            
            TableColumn<EmpleadoDto, String> tbcPApellido = new TableColumn<>("Primer Apellido");
            tbcPApellido.setPrefWidth(150);
            tbcPApellido.setCellValueFactory(cd -> cd.getValue().pApellido);
            
            TableColumn<EmpleadoDto, String> tbcSApellido = new TableColumn<>("Segundo Apellido");
            tbcSApellido.setPrefWidth(150);
            tbcSApellido.setCellValueFactory(cd -> cd.getValue().sApellido);
            
            
            tbvResultados.getColumns().add(tbcId);
            tbvResultados.getColumns().add(tbcCedula);
            tbvResultados.getColumns().add(tbcNombre);
            tbvResultados.getColumns().add(tbcPApellido);
            tbvResultados.getColumns().add(tbcSApellido);
            tbvResultados.refresh();

            btnFiltrar.setOnAction((ActionEvent event) -> {
                tbvResultados.getItems().clear();
                EmpleadoService service = new EmpleadoService();
                String cedula = "%" + txtCedula.getText() + "%";

                String nombre = "%" + txtNombre.getText() + "%";
                
                String pApellido = "%" + txtPApellido.getText() + "%";
                
                String sApellido = "%" + txtSApellido.getText() + "%";

                Respuesta respuesta = service.getEmpleados(cedula.toUpperCase(), nombre.toUpperCase(), pApellido.toUpperCase(), sApellido.toUpperCase());

                if (respuesta.getEstado()) {
                    ObservableList<EmpleadoDto> empleados = FXCollections.observableList((List<EmpleadoDto>) respuesta.getResultado("Empleados"));
                    tbvResultados.setItems(empleados);
                    tbvResultados.refresh();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Consultar empleados", getStage(), respuesta.getMensaje());
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(BusquedaViewController.class.getName()).log(Level.SEVERE, "Error consultando los empleados.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Consultar empleados", getStage(), "Ocurrio un error consultado los empleados.");
        }
    }
public void busquedaPlanillas() {
        try {
            lblTitulo.setText("Búsqueda de Planillas");
            
            
            
            JFXTextField txtID = new JFXTextField();
            txtID.setLabelFloat(true);
            txtID.setPromptText("ID");
            txtID.setTextFormatter(Formato.getInstance().integerFormat());
            txtID.setOnKeyPressed(keyEnter);
            
            JFXTextField txtCodigo = new JFXTextField();
            txtCodigo.setLabelFloat(true);
            txtCodigo.setPromptText("Codigo");
            txtCodigo.setTextFormatter(Formato.getInstance().maxLengthFormat(4));
            txtCodigo.setOnKeyPressed(keyEnter);
            
            JFXTextField txtDescripcion = new JFXTextField();
            txtDescripcion.setLabelFloat(true);
            txtDescripcion.setPromptText("Descripcion");
            txtDescripcion.setTextFormatter(Formato.getInstance().letrasFormat(40));
            txtDescripcion.setOnKeyPressed(keyEnter);
            
            JFXTextField txtPlaxMes = new JFXTextField();
            txtPlaxMes.setLabelFloat(true);
            txtPlaxMes.setPromptText("Planillas por mes");
            txtPlaxMes.setOnKeyPressed(keyEnter);
            txtPlaxMes.setTextFormatter(Formato.getInstance().integerFormat());
            
            JFXTextField txtIDEmpleado = new JFXTextField();
            txtIDEmpleado.setLabelFloat(true);
            txtIDEmpleado.setPromptText("ID Empleado");
            txtIDEmpleado.setTextFormatter(Formato.getInstance().integerFormat());
            txtIDEmpleado.setOnKeyPressed(keyEnter);
            
            JFXTextField txtCedula = new JFXTextField();
            txtCedula.setLabelFloat(true);
            txtCedula.setPromptText("Cedula");
            txtCedula.setTextFormatter(Formato.getInstance().cedulaFormat(40));
            txtCedula.setOnKeyPressed(keyEnter);
            
            vbxParametros.getChildren().clear();
            vbxParametros.getChildren().add(txtPlaxMes);
            vbxParametros.getChildren().add(txtID);
            vbxParametros.getChildren().add(txtCodigo);
            vbxParametros.getChildren().add(txtDescripcion);
            vbxParametros.getChildren().add(txtIDEmpleado);
            vbxParametros.getChildren().add(txtCedula);

            tbvResultados.getColumns().clear();
            tbvResultados.getItems().clear();
            
            TableColumn<TipoPlanillaDto, String> tbcId = new TableColumn<>("Id");
            tbcId.setPrefWidth(50);
            tbcId.setCellValueFactory(cd -> cd.getValue().id);
            
            TableColumn<TipoPlanillaDto, String> tbcCodigo = new TableColumn<>("codigo");
            tbcCodigo.setPrefWidth(100);
            tbcCodigo.setCellValueFactory(cd -> cd.getValue().codigo);
            
            TableColumn<TipoPlanillaDto, String> tbcDescripcion = new TableColumn<>("Descripcion");
            tbcDescripcion.setPrefWidth(150);
            tbcDescripcion.setCellValueFactory(cd -> cd.getValue().descripcion);
            
            TableColumn<TipoPlanillaDto, String> tbcPlanxMes = new TableColumn<>("Planilla por mes");
            tbcPlanxMes.setPrefWidth(150);
            tbcPlanxMes.setCellValueFactory(cd -> cd.getValue().planillasPorMes);
            
            
            
            tbvResultados.getColumns().add(tbcId);
            tbvResultados.getColumns().add(tbcCodigo);
            tbvResultados.getColumns().add(tbcDescripcion);
            tbvResultados.getColumns().add(tbcPlanxMes);
            tbvResultados.refresh();

            btnFiltrar.setOnAction((ActionEvent event) -> {
                tbvResultados.getItems().clear();
                TipoPlanillaService service = new TipoPlanillaService();
                String codigo = "%" + txtCodigo.getText() + "%";

                String descripcion = "%" + txtDescripcion.getText() + "%";
                
                String planillasPorMes = "%" + txtPlaxMes.getText() + "%";
                
                String idEmp = "%" + txtIDEmpleado.getText() + "%";
                
                String cedula = "%" + txtCedula.getText() + "%";
                
                Respuesta respuesta = service.getTipoPlanillas(codigo.toUpperCase(), descripcion.toUpperCase(), planillasPorMes.toUpperCase());
                
                if(txtIDEmpleado.getText()!= null || txtCedula.getText()!= null){
                   respuesta = service.getTipoPlanillasIDyCed(idEmp.toUpperCase(), cedula.toUpperCase());  
                }
                if (respuesta.getEstado()) {
                    ObservableList<TipoPlanillaDto> tipoPlanilla = FXCollections.observableList((List<TipoPlanillaDto>) respuesta.getResultado("Planillas"));
                    tbvResultados.setItems(tipoPlanilla);
                    tbvResultados.refresh();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Consultar Planillas", getStage(), respuesta.getMensaje());
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(BusquedaViewController.class.getName()).log(Level.SEVERE, "Error consultando los planillas.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Consultar plainllas", getStage(), "Ocurrio un error consultado los planillas.");
        }
    }

    @Override
    public void initialize() {
        Platform.runLater(() -> {
            for (Node object : vbxParametros.getChildren()) {
                if(object.isFocusTraversable()) {
                    object.requestFocus();
                    break;
                }
            }
        });
        
        resultado = null;
    }
}
