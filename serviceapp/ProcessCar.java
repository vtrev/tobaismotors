package serviceapp;

import java.io.*;
import java.lang.module.FindException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.util.TimeZone;


public class ProcessCar{
    private File carFile;
    private FileReader fileReader;
    private BufferedReader bufferedReader;

    public void moveCar(String csvPath){
        try{
            carFile = new File(csvPath);
            fileReader = new FileReader(carFile);
            bufferedReader = new BufferedReader(fileReader);

            String[] carDetails = bufferedReader.readLine().split(",");
            String carFileName = carDetails[1].replaceAll(" ","")+".car";
            String carObjectPath = String.format("./serviceapp/work/%s/%s",carDetails[0].toLowerCase(),carFileName);
            String carDestination = String.format("./serviceapp/service-completed/%s",carFileName);
            Path tmp = Files.move(Paths.get(carObjectPath),Paths.get(carDestination));
            //if temp is null no file was moved
        }catch (IOException ex){
            ex.printStackTrace();
        }finally {
            updateRecords();
        }
    }

    public void storeCar(String csvPath){
        try{
            carFile = new File(csvPath);
            fileReader = new FileReader(carFile);
            bufferedReader = new BufferedReader(fileReader);

            String[] carDetails = bufferedReader.readLine().split(",");
            Car car = new Car(carDetails[0].toLowerCase(),carDetails[1],carDetails[2]);

            String outFilePath = String.format("./serviceapp/work/%s/%s.car",car.getMake(),car.getRegistration());
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outFilePath));
            outputStream.writeObject(car);
            outputStream.close();

        }catch (FindException fex){
            System.out.print("File...not found");
        }catch (IOException ex){
            ex.printStackTrace();
        }finally {
            updateStatus();
        }
    }

    private void updateStatus(){
        Path statusFile = Paths.get("./serviceapp/status.txt");
        String data ="";

        if(Files.exists(statusFile)){
            try {
                Files.delete(statusFile);

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }

        for (Make makeEnum: Make.values()){
            String make = makeEnum.toString().toLowerCase();
            String directory = String.format("./serviceapp/work/%s/",make);
            File[] carList = new File(directory).listFiles();
            data += String.format("%s \t\t: %s \n",make,carList.length);
            }

        try {
            FileWriter  writer = new FileWriter(statusFile.toString());
            writer.write(data);
            writer.flush();
            writer.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }


        }
    private void updateRecords(){
        Path statusFile = Paths.get("./serviceapp/records.txt");
        LocalDate today = LocalDate.now();
        String data = "";
        int lastDay = 0;
        int lastWeek = 0;
        int lastMonth = 0;

        if(Files.exists(statusFile)){
            try {
                Files.delete(statusFile);

            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
            String directory = "./serviceapp/service-completed";
            File[] carList = new File(directory).listFiles();
        for (File carFile : carList){
            LocalDate fileDate = LocalDate.ofInstant(Instant.ofEpochMilli(carFile.lastModified()), TimeZone.getDefault().toZoneId());
            if(fileDate.minusDays(1).equals(today.minusDays(1))){
                lastDay++;
                continue;
            }
            if(fileDate.isAfter(today.minusDays(7))){
                lastWeek++;
                continue;
            }

            if(today.getMonthValue() -1 == fileDate.getMonthValue()){
                lastMonth++;
            }
        }

        data += String.format("Last day \t: %s \n",lastDay);
        data += String.format("Last week \t: %s \n",lastWeek);
        data += String.format("Last month \t: %s \n",lastMonth);

        try {
            FileWriter  writer = new FileWriter(statusFile.toString());
            writer.write(data);
            writer.flush();
            writer.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }


    }

}

