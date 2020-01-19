//package ViewModel;
//
//import java.util.Observable;
//import java.util.Observer;
//
//import Simulator.Simulator;
//import javafx.beans.property.DoubleProperty;
//import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.ObjectProperty;
//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleObjectProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//
//
//public class ViewModel extends Observable implements Observer {
//    // models
//    private Simulator simulator;
//    /* ***data members related to view*** */
//    // ** simulator **
//    // connect button
//    public StringProperty simulatorIP;
//    public StringProperty simulatorPort;
//    // throttle & rudder
//    public DoubleProperty throttle;
//    public DoubleProperty rudder;
//    // joystick arguments
//    public StringProperty aileron;
//    public StringProperty elevator;
//    // plane location & destination -- data from simulator
//
//    public ObjectProperty<ImageView> plane;
//
//    // ** path **
//    public StringProperty solverIP;
//    public StringProperty solverPort;
//    // data from the view
//    public DoubleProperty destX;
//    public DoubleProperty destY;
//    public ObjectProperty<double[][]> ground;
//    public ObjectProperty<String[]> directions;
//    public DoubleProperty groundCellW, groundCellH;
//
//    // *** end of variables
//    public ViewModel(Simulator simulator) {
//        this.simulator = simulator;
//        simulatorIP = new SimpleStringProperty();
//        simulatorPort = new SimpleStringProperty();
//        throttle = new SimpleDoubleProperty();
//        rudder = new SimpleDoubleProperty();
//        aileron = new SimpleStringProperty();
//        elevator = new SimpleStringProperty();
//        plane = new SimpleObjectProperty<>(new ImageView());
//        plane.get().setImage(new Image("/resources/airplane.png"));
//        plane.get().setVisible(false);
//        String[] s = { "" };
//        openServer();
//        System.out.println("opened server");
//    }
//
//    private void openServer() {
//        simulator.openDataServer(5400, 10);
//    }
//
//    public void connectToSimulator() {
//        simulator.connectToSimulator(simulatorIP.get(), Integer.parseInt(simulatorPort.get()));
//        simulator.dumpPosition(simulatorIP.get(), Integer.parseInt(simulatorPort.get()));
//    }
//
//    public void setThrottle() {
//        simulator.setThrottle(throttle.get());
//    }
//
//    public void setRudder() {
//        simulator.setRudder(rudder.get());
//    }
//
//    public void setJoystickChanges() {
//        simulator.setAileron(Double.parseDouble(aileron.get()));
//        simulator.setElevator(Double.parseDouble(elevator.get()));
//    }
//
//    private void updatePlanePosition() {
//        /*
//         * array indexes: longitude_deg x | latitude_deg y | altitude_ft | ground_elev_m
//         * | ground_elev_ft
//         */
//        double[] position = simulator.getPlaneLocation();
//        double longitude_deg = position[0];
//        double latitude_deg = position[1];
////		double altitude_ft=position[2];
////		double ground_elev_m=position[3];
////		double ground_elev_ft=position[4];
//
//        int currentIndexX = (int) (((longitude_deg - csv_srcX.get())) / csv_scale.get() * groundCellW.get());
//        int currentIndexY = (int) (((csv_srcY.get() - latitude_deg) / csv_scale.get()) * groundCellH.get());
//        plane.get().setX(currentIndexX);
//        plane.get().setY(currentIndexY);
//        plane.get().setLayoutX(-16);
//        plane.get().setLayoutY(-16);
//        plane.get().setRotate(45);
//    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//        if (o == simulator) {
//            updatePlanePosition();
//        }
//    }
//}