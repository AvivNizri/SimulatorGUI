package sample;

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
//        PathModel pathModel = new PathModel();
//        SimModel simModel = new SimModel();
//        // view model
//
//        ViewModel viewModel = new ViewModel(pathModel, simModel);
//        pathModel.addObserver(viewModel);
//        simModel.addObserver(viewModel);

        try {
            FXMLLoader fxml = new FXMLLoader();
            BorderPane root = fxml.load(getClass().getResource("MainFrame.fxml").openStream());
            root.setStyle("-fx-background-image: url(\"/Pictures/cockpit.jpg\");");
            MainFrameController mainWindowC = fxml.getController();// view
//            mainWindowC.setViewModel(viewModel);
//            viewModel.addObserver(mainWindowC);
            primaryStage.setTitle("Aviv Nizri & Raz Sardas FlightSoftwate LTD");
            Scene scene = new Scene(root, 600, 440);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            mainWindowC.setSliderOnDragEvent();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);

    }
}