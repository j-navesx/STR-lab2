public class Mechanism {
    public void moveConveyor() {
        SplitterLine.conveyorMove();
    }
    public void stopConveyor() {
        SplitterLine.conveyorStop();
    }
    public void swithLed(boolean state) {
        if(!state)
            SplitterLine.ledON();
        if(state)
            SplitterLine.ledOFF();
    }
    public boolean switchDock1Pressed() {
        return SplitterLine.getSwitchDock1();
    }
    public boolean switchDock2Pressed() {
        return SplitterLine.getSwitchDock2();
    }
    public boolean switchDock3Pressed() {
        return SplitterLine.getSwitchDock3();
    }
}

