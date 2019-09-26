package serviceapp;


import java.io.*;
import java.lang.module.FindException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProcessCar{
    private String csvPath;
    private File carFile;
    private FileReader fileReader;
    private BufferedReader bufferedReader;

    public void moveCar(String csvPath){
        System.out.println("Servicing car....");

        try{
            carFile = new File(csvPath);
            fileReader = new FileReader(carFile);
            bufferedReader = new BufferedReader(fileReader);

            String[] carDetails = bufferedReader.readLine().split(",");
            String carFileName = carDetails[1].replaceAll(" ","")+".car";
            String carObjectPath = String.format("./serviceapp/work/%s/%s",carDetails[0].trim(),carFileName);
            String carDestination = String.format("./serviceapp/service-completed/%s",carFileName);

            Path tmp = Files.move(Paths.get(carObjectPath),Paths.get(carDestination));
            System.out.println(tmp);
            updateStatus();

            //get car object from disk
            //run service method
            //move file to service completed

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void storeCar(String csvPath){
        System.out.println("Storing car....@"+csvPath);
        try{
            carFile = new File(csvPath);
            fileReader = new FileReader(carFile);
            bufferedReader = new BufferedReader(fileReader);

            String[] carDetails = bufferedReader.readLine().split(",");
            Car car = new Car(carDetails[0],carDetails[1],carDetails[2]);

            String outFilePath = String.format("./serviceapp/work/%s/%s.car",car.getMake(),car.getRegistration());
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outFilePath));
            outputStream.writeObject(car);
            outputStream.close();


        }catch (FindException fex){
            System.out.print("File...not found");
        }catch (Exception ioex){
            ioex.printStackTrace();
        }


    }

    private void updateStatus(){

        try{

            File records = new File("./serviceapp/status.txt");


        }catch (IOException ex){
            ex.printStackTrace();
        }
    }


}