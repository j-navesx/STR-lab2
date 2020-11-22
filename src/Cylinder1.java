public class Cylinder1 implements Cylinder {
    
    @Override
    public void moveForward() {
        SplitterLine.cylinder1MoveForward();
    }
    
    @Override
    public void moveBackward() {
        SplitterLine.cylinder1MoveBackward();
    }
    
    @Override
    public void stop() {
        SplitterLine.cylinder1Stop();
    }
    
    @Override
    public int getPosition() {
        return SplitterLine.getCylinder1Position();
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
        return SplitterLine.cylinder1_isMoving();
    }
    
    @Override
    public boolean isMovingForward(){
        return SplitterLine.cylinder1_isMovingForward();
    }
    
    @Override
    public boolean isMovingBackward(){
        return SplitterLine.cylinder1_isMovingBackward();
    }
    
    @Override
    public boolean isStopped(){
        return SplitterLine.cylinder1_isStopped();
    }
    
    @Override
    public boolean packageGetDetected() {
        throw new UnsupportedOperationException("Opertion not supported for cylinder1.");
    }
}
