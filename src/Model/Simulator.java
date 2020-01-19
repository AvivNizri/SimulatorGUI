package Model;

import View.Client;

public class Simulator implements ISimulator {

    public Client myClient;

    public Simulator(Client myClient){
        this.myClient = myClient;
    };
    @Override
    public void setThrottle(double v) {
        this.myClient.writeClient("set /controls/flight/throttle " + v);
        System.out.println("throttle "+v);
    }

    @Override
    public void setRudder(double v) {
        this.myClient.writeClient("set /controls/flight/rudder " + v);
        System.out.println("rudder "+v);
    }

    @Override
    public void setAileron(double v) {
        this.myClient.writeClient("set /controls/flight/aileron " + v);
        System.out.println("aileron "+v);
    }

    @Override
    public void setElevator(double v) {
        this.myClient.writeClient("set /controls/flight/elevator " + v);
        System.out.println("elevator "+v);
    }

}