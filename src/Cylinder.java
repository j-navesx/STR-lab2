public interface Cylinder {
    
    public void moveForward();
    
    public void moveBackward();
    
    public void stop();
    
    public void gotoPosition(int position);
    
    public int getPosition();
    
    public boolean isMoving();
    
    public boolean isMovingForward();
    
    public boolean isMovingBackward();
    
    public boolean isStopped();
    
    public boolean packageGetDetected();
    
}
