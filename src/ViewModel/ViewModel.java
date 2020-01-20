package ViewModel;

import Model.Simulator;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;


public class ViewModel {
    public DoubleProperty throttle,rudder,aileron,elevator;
    public Simulator simulator;
    public ViewModel(Simulator simulator) {
        this.simulator  =   simulator;
        throttle        =   new SimpleDoubleProperty();
        rudder          =   new SimpleDoubleProperty();
        aileron         =   new SimpleDoubleProperty();
        elevator        =   new SimpleDoubleProperty();

        // when these values change, change the model values as well.
        throttle.addListener((o,old,nw) ->simulator.setThrottle(nw.doubleValue()));
        rudder.addListener((o,old,nw)   ->simulator.setRudder(nw.doubleValue()));
        aileron.addListener((o,old,nw)  ->simulator.setAileron(nw.doubleValue()));
        elevator.addListener((o,old,nw) ->simulator.setElevator(nw.doubleValue()));
        // when the model changes values it sends FlightGear the associated commands
    }
}