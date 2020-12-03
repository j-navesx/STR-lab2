public interface Cylinder {
    
    public void moveForward();
    
    public void moveBackward();
    
    public void stop();
    
    public void gotoPosition(int position);
    
    public void setEmergency();
    
    public void endEmergency();
    
    public int getPosition();
    
    public int getPkgNumber();
    
    public boolean isMoving();
    
    public boolean isMovingForward();
    
    public boolean isMovingBackward();
    
    public boolean isStopped();
    
    public boolean packageGetDetected();
}
