package serviceapp;

import java.io.IOException;
import java.nio.file.*;

public  class Watcher {
    private WatchService carWatchService;
    private WatchKey serviceWatchKey;
    private WatchKey doneWatchKey;
    private Path serviceDirPath = Paths.get("./serviceapp/service/");
    private Path doneDirPath =  Paths.get("./serviceapp/service/done/");
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
            String csvPath = (serviceDirPath.toString()+"/"+ event.context()).trim();
            processCar.storeCar(csvPath);
        }
        for (WatchEvent < ? > event : doneWatchKey.pollEvents()) {
            String csvPath = (doneDirPath.toString()+"/"+ event.context()).trim();
            processCar.moveCar(csvPath);
        }

        }
    }

}
