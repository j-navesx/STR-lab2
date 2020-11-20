public interface Cylinder {
    public void moveFoward();
    public void moveBackward();
    public void stopMoving();
    public void gotoPostion();
    public int getPosition();
    public boolean isMoving();
    public boolean isMovingFoward();
    public boolean isMovingBackward();
    public boolean isStopped();
    public boolean packageGetDetected();
}
