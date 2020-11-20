public class CylinderThread extends Thread {
    private final Cylinder cylinder;
    
    public CylinderThread(Cylinder cyl) {
        this.cylinder = cyl;
    }
    
    @Override
    public void run() {
        cylinder.gotoPosition(1);
        cylinder.gotoPosition(0);
    }
}
