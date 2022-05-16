package cr.ac.una.unaplanilla3;

import cr.ac.una.unaplanilla3.util.FlowController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FlowController.getInstance().InitializeFlow(stage,null);
        stage.getIcons().add(new Image("cr/ac/una/unaplanilla3/resources/Usuario-48.png"));
        stage.setTitle("UNA PLANILLA");
        FlowController.getInstance().goViewInWindow("LoginView");
    }

    public static void main(String[] args) {
        launch();
    }

}