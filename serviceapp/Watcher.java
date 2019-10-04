package serviceapp;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;


public  class Watcher {
    private WatchService carWatchService;
    private WatchKey serviceWatchKey;
    private WatchKey doneWatchKey;
    private String separator = File.separator;
    private Path serviceDirPath = Paths.get("."+ separator +"serviceapp"+ separator +"service"+ separator);
    private Path doneDirPath =  Paths.get("."+ separator +"serviceapp"+ separator +"service"+ separator +"done"+ separator);
    private ProcessCar processCar = new ProcessCar();



    public Watcher() {
        initialize();
    }
    private void initialize(){
        try{
            carWatchService = FileSystems.getDefault().newWatchService();
            serviceWatchKey = serviceDirPath.register(carWatchService, StandardWatchEventKinds.ENTRY_CREATE);
            doneWatchKey = doneDirPath.register(carWatchService, StandardWatchEventKinds.ENTRY_CREATE);

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void watch(){
    try{
        carWatchService = FileSystems.getDefault().newWatchService();
        serviceWatchKey = serviceDirPath.register(carWatchService, StandardWatchEventKinds.ENTRY_CREATE);
    }catch(IOException ex){
        ex.printStackTrace();
    }

            while (true) {

        for (WatchEvent < ? > event : serviceWatchKey.pollEvents()) {
            String csvPath = (serviceDirPath.toString()+ separator + event.context()).trim();
            processCar.storeCar(csvPath);
        }
        for (WatchEvent < ? > event : doneWatchKey.pollEvents()) {
            String csvPath = (doneDirPath.toString()+ separator + event.context()).trim();
            processCar.moveCar(csvPath);
        }

        }
    }

}
