/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanilla3.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.unaplanilla3.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cbcar
 */
public class PrincipalViewController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private JFXButton BtnEmpleados;
    @FXML
    private JFXButton BtnSalir;
    @FXML
    private JFXButton BtnTiposPlanilla;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void initialize() {
        
    }

    @FXML
    private void onActionBtnEmpleados(ActionEvent event) {
        FlowController.getInstance().goView("EmpleadosView");
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
       ((Stage) BtnSalir.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnTiposPlanilla(ActionEvent event) {
        FlowController.getInstance().goView("TipoPlanillasView");
    }
    
}
