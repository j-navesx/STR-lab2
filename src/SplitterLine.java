
public class SplitterLine {
    
    static {
        System.load("C:\\str\\SplitterLine\\x64\\Debug\\SplitterLine.dll");
    }
    
    static synchronized native public void initializeHardwarePorts();
    
    static synchronized native public void cylinder1_moveForward();
    static synchronized native public void cylinder1_moveBackward();
    static synchronized native public void cylinder1_stop();
    
    static synchronized native public void cylinder2_moveForward();
    static synchronized native public void cylinder2_moveBackward();
    static synchronized native public void cylinder2_stop();
    
    static synchronized native public void cylinder3_moveForward();
    static synchronized native public void cylinder3_moveBackward();
    static synchronized native public void cylinder3_stop();
    
    static synchronized native public void conveyorMove();
    static synchronized native public void conveyorStop();
    
    static synchronized native public void ledON();
    static synchronized native public void ledOFF();
    
    static native public int getIdentificationSensors();

    static native public boolean isPartAtGate1();
    static native public boolean isPartAtGate2();

    static native public boolean getSwitchDock1();
    static native public boolean getSwitchDock2();
    static native public boolean getSwitchDock3();

    static native public int cylinder1_getPosition();
    static native public int cylinder2_getPosition();
    static native public int cylinder3_getPosition();
}
