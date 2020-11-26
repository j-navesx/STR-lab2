public class Cylinder3 implements Cylinder {
    
    @Override
    public void moveForward() {
        SplitterLine.cylinder3MoveForward();
    }
    
    @Override
    public void moveBackward() {
        SplitterLine.cylinder3MoveBackward();
    }
    
    @Override
    public void stop() {
        SplitterLine.cylinder3Stop();
    }
    
    @Override
    public int getPosition() {
        return SplitterLine.getCylinder3Position();
    }
    
    @Override
    public int getPkgNumber() {
        return 2;
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
        return SplitterLine.cylinder3_isMoving();
    }
    
    @Override
    public boolean isMovingForward(){
        return SplitterLine.cylinder3_isMovingForward();
    }
    
    @Override
    public boolean isMovingBackward(){
        return SplitterLine.cylinder3_isMovingBackward();
    }
    
    @Override
    public boolean isStopped(){
        return SplitterLine.cylinder3_isStopped();
    }
    
    @Override
    public boolean packageGetDetected() {
        return SplitterLine.isPartAtGate2();
    }
}
