public class PackageIdentifyThread extends Thread{
    private int pkgType;
    
    int getpkgType() {
        return pkgType;
    }
    
    @Override
    public void run() {
        Cylinder2 cyl_2= new Cylinder2();
        pkgType = 1;
        boolean sensor1 = false;
        boolean sensor2 = false;
        
        while(!cyl_2.packageGetDetected()) {
            if(!sensor1 && SplitterLine.getIdentificationSensors() == 1) {
                sensor1 = true;
                pkgType++;
            }
            if(!sensor2 && SplitterLine.getIdentificationSensors() == 2) {
                sensor2 = true;
                pkgType++;
            }
        }
        System.out.println(pkgType);
    }
}
