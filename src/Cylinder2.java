public class Cylinder2 implements Cylinder {
    
    @Override
    public void moveForward() {
        SplitterLine.cylinder2MoveForward();
    }
    
    @Override
    public void moveBackward() {
        SplitterLine.cylinder2MoveBackward();
    }
    
    @Override
    public void stop() {
        SplitterLine.cylinder2Stop();
    }
    
    @Override
    public int getPosition() {
        return SplitterLine.getCylinder2Position();
    }
    
    @Override
    public int getPkgNumber() {
        return 1;
    }
    
    @Override
    public void gotoPosition(int position) {
        //TODO:  movefrom 0 to 1 and vice-versa
        //this part is developed in Java
        int crtPos= getPosition();
        
        if(crtPos != position) {
            if(crtPos < position)
                moveForward();
            if(crtPos > position)
                moveBackward();
        }
        while(getPosition() != position) {
            Thread.yield();
        }
        stop();
    }
    @Override
    public boolean isMoving() {
        return SplitterLine.cylinder2_isMoving();
    }
    
    @Override
    public boolean isMovingForward(){
        return SplitterLine.cylinder2_isMovingForward();
    }
    
    @Override
    public boolean isMovingBackward(){
        return SplitterLine.cylinder2_isMovingBackward();
    }
    
    @Override
    public boolean isStopped(){
        return SplitterLine.cylinder2_isStopped();
    }
    
    @Override
    public boolean packageGetDetected() {
        return SplitterLine.isPartAtGate1();
    }
}
