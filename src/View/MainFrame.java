package View;

import Simulator.Simulator;
//import ViewModel.ViewModel;
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

        // ***create models and view-model connections***
        // models
        Simulator simulator = new Simulator();

        // view model
        //ViewModel viewModel = new ViewModel(simulator);
//        simulator.addObserver(viewModel);

        try {
            FXMLLoader fxml = new FXMLLoader();
            BorderPane root = fxml.load(getClass().getResource("MainFrame.fxml").openStream());
            root.setStyle("-fx-background-image: url(\"/Pictures/cockpit.jpg\");");
            MainFrameController mainFrame = fxml.getController();// view
//            mainFrame.setViewModel(viewModel);
            //viewModel.addObserver(mainFrame);
            primaryStage.setTitle("Aviv Nizri & Raz Sardas FlightSoftwate LTD");
            Scene scene = new Scene(root, 378, 458);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            mainFrame.setSliderOnDragEvent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}