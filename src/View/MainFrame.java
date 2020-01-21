package View;

import Model.Simulator;
import ViewModel.ViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainFrame extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage _primaryStage) {
        primaryStage = _primaryStage;
        Simulator simulator = new Simulator();

        // view model
        ViewModel viewModel = new ViewModel(simulator);
        try {
            FXMLLoader fxml = new FXMLLoader();
            BorderPane root = fxml.load(getClass().getResource("MainFrame.fxml").openStream());
            root.setStyle("-fx-background-image: url(\"/Pictures/cockpit.jpg\");");
            MainFrameController mainFrame = fxml.getController();// view
            mainFrame.setViewModel(viewModel);
            primaryStage.setTitle("Aviv Nizri & Raz Sardas FlightSoftware LTD");
            Scene scene = new Scene(root, 378, 458);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}