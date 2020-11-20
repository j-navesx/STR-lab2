public class gateSensorThread extends Thread{
    private final Cylinder cylinder;
    
    public gateSensorThread(Cylinder cyl) {
        this.cylinder = cyl;
    }
}
