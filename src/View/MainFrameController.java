package View;

import java.io.IOException;
import ViewModel.ViewModel;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainFrameController {
    private ViewModel viewModel;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    // MVVM Var
    private DoubleProperty aileronV, elevatorV;

    // entire GUI features
    @FXML
    private Button openConnectWindow;
    @FXML
    private Button back;
    @FXML
    private Button ServerConnect;
    @FXML
    private TextField connectionIp;
    @FXML
    private TextField connectionPort;
    @FXML
    private TextField simulatorIP;
    @FXML
    private TextField simulatorPort;
    @FXML
    private Slider rudderSlider;
    @FXML
    private Slider throttleSlider;
    @FXML
    private Circle joystick;
    @FXML
    private Circle frameCircle;
    @FXML
    private Label throttleValue;
    @FXML
    private Label rudderValue;

    //Ctor
    public MainFrameController() {
        openConnectWindow = new Button();
        ServerConnect = new Button();
        simulatorIP = new TextField();
        simulatorPort = new TextField();
        rudderSlider = new Slider();
        throttleSlider = new Slider();
        joystick = new Circle();
        frameCircle = new Circle();
        aileronV = new SimpleDoubleProperty();
        elevatorV = new SimpleDoubleProperty();
        back = new Button();
        throttleValue = new Label();
        rudderValue = new Label();
    }
    // Pay Attention the view model is being created in the mainFrame section
    public void setViewModel(ViewModel viewModel0) {
        this.viewModel = viewModel0;
        this.viewModel.rudder.bind(rudderSlider.valueProperty());
        this.viewModel.throttle.bind(throttleSlider.valueProperty());
        this.viewModel.aileron.bind(this.aileronV);
        this.viewModel.elevator.bind(this.elevatorV);
    }

    // here is the small popUp window which we set the params to the remote simulator
    @FXML
    private void openConnectWindow(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PopUp.fxml"));
        BorderPane root = (BorderPane) fxmlLoader.load();
        root.setStyle("-fx-background-image: url(\"/Pictures/connect.jpg\");");
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainFrame.primaryStage);
        stage.setScene(new Scene(root));
        stage.show();
        MainFrameController connectWindow = fxmlLoader.getController();
        connectWindow.viewModel= this.viewModel;
    }

    // right after the connectWindow invoke, this function actually run and connect the client
    @FXML
    private void handleConnect(ActionEvent event) throws IOException {
        String ip = connectionIp.getText();
        String port = connectionPort.getText();
        System.out.println("Param : " + ip  +"  "+ port);
        this.viewModel.simulator.setMyClient(new Client(ip, Integer.parseInt(port)));
        String mode = ((Stage) ServerConnect.getScene().getWindow()).getTitle();

        if (ip.matches("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$") && port.matches("^(\\d{1,4})")) {
                simulatorIP.setText(ip);
                simulatorPort.setText(port);
                viewModel.simulator.myClient.runClient();
                closeConnectWindow(event);
        }
    }

    @FXML
    private void closeConnectWindow(ActionEvent event) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
        rudderSlider.setValue(0);
        throttleSlider.setValue(0);
        aileronV.set(0);
        elevatorV.set(0);
    }

    @FXML
    private void joystickIsPressed(MouseEvent me) {
            orgSceneX = me.getSceneX();
            orgSceneY = me.getSceneY();
            orgTranslateX = ((Circle) (me.getSource())).getTranslateX();
            orgTranslateY = ((Circle) (me.getSource())).getTranslateY();
    }

    @FXML
    private void joystickIsDragged(MouseEvent me) {
            double offsetX = me.getSceneX() - orgSceneX;
            double offsetY = me.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
            double joystickCenterX = frameCircle.getTranslateX() + frameCircle.getRadius() - joystick.getRadius();
            double joystickCenterY = frameCircle.getTranslateY() - frameCircle.getRadius() - joystick.getRadius();
            double frameRadius = frameCircle.getRadius();
            double maxX = joystickCenterX + frameRadius;
            double contractionsCenterX = joystickCenterX - frameRadius;
            double maxY = joystickCenterY - frameRadius;
            double contractionsCenterY = joystickCenterY + frameRadius;

            double slant = Math
                    .sqrt(Math.pow(newTranslateX - joystickCenterX, 2) + Math.pow(newTranslateY - joystickCenterY, 2));

            if (slant > frameRadius) {
                double alpha = Math.atan((newTranslateY - joystickCenterY) / (newTranslateX - joystickCenterX));
                if ((newTranslateX - joystickCenterX) < 0) {
                    alpha = alpha + Math.PI;
                }
                newTranslateX = Math.cos(alpha) * frameRadius + orgTranslateX;
                newTranslateY = Math.sin(alpha) * frameRadius + orgTranslateY;
            }
            ((Circle) (me.getSource())).setTranslateX(newTranslateX);
            ((Circle) (me.getSource())).setTranslateY(newTranslateY);
            // normalize to range of [-1,1]
            double normalX = Math
                    .round(((((newTranslateX - contractionsCenterX) / (maxX - contractionsCenterX)) * 2) - 1) * 100.00)
                    / 100.00;
            // normalize to range of [-1,1]
            double normalY = Math
                    .round(((((newTranslateY - contractionsCenterY) / (maxY - contractionsCenterY)) * 2) - 1) * 100.00)
                    / 100.00;

            aileronV.set(normalX);
            elevatorV.set(normalY);
    }

    @FXML
    private void joystickIsReleased(MouseEvent me) {
            ((Circle) (me.getSource())).setTranslateX(frameCircle.getTranslateX() + frameCircle.getRadius() - joystick.getRadius());
            ((Circle) (me.getSource())).setTranslateY(frameCircle.getTranslateY() - frameCircle.getRadius() - joystick.getRadius());
            aileronV.set(0);
            elevatorV.set(0);
    }
}