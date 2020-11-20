public class SplitterLine {
    
    static {
        System.load("c:\\str\\SplitterLine\\x64\\Debug\\SplitterLine.dll");
    }
    
    // these methods have got the race condition effect
    static synchronized native public void initializeHardwarePorts();
    static synchronized native public void cylinder1MoveForward();
    static synchronized native public void cylinder1MoveBackward();
    static synchronized native public void cylinder1Stop();
    static synchronized native public void cylinder2MoveForward();
    static synchronized native public void cylinder2MoveBackward();
    static synchronized native public void cylinder2Stop();
    static synchronized native public void cylinder3MoveForward();
    static synchronized native public void cylinder3MoveBackward();
    static synchronized native public void cylinder3Stop();
    
    static synchronized native public void conveyorMove();
    static synchronized native public void conveyorStop();
    
    static synchronized native public void ledON();
    static synchronized native public void ledOFF();
    
    // these methods read sensors
    static native public int getCylinder1Position();
    static native public boolean cylinder1_isMoving();
    static native public boolean cylinder1_isMovingForward();
    static native public boolean cylinder1_isMovingBackward();
    static native public boolean cylinder1_isStopped();
    static native public int getCylinder2Position();
    static native public boolean cylinder2_isMoving();
    static native public boolean cylinder2_isMovingForward();
    static native public boolean cylinder2_isMovingBackward();
    static native public boolean cylinder2_isStopped();
    static native public int getCylinder3Position();
    static native public boolean cylinder3_isMoving();
    static native public boolean cylinder3_isMovingForward();
    static native public boolean cylinder3_isMovingBackward();
    static native public boolean cylinder3_isStopped();
    
    static native public int getIdentificationSensors();
    
    static native public boolean isPartAtGate1();
    static native public boolean isPartAtGate2();
    
    static native public boolean getSwitchDock1();
    static native public boolean getSwitchDock2();
    static native public boolean getSwitchDock3();
}
