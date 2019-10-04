package serviceapp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.module.FindException;
import java.io.Serializable;


public class Car implements Serializable{
    private String make;
    private String registration;
    private String color;

    public Car(String make, String reg , String color){
        this.make = make;
        this.registration = reg.replaceAll(" ","");
        this. color = color;
    }

    public String getRegistration(){
        return this.registration;
    }


    public String getMake(){
        for(Make make: Make.values()){
            if (this.make.toUpperCase().equals(make.toString())){
                return this.make;
            }
        }
        return "Other";
    }

    public void storeCar(){
    try{
        String separator = File.separator;
        String outFilePath = String.format("."+ separator +"serviceapp"+ separator +"work"+ separator +"%s"+ separator +"%s.car",getMake(),getRegistration());
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(outFilePath));
        outputStream.writeObject(this);
        outputStream.close();


    }catch (FindException fex){
        System.out.print("File...not found");
    }catch (Exception ioex){
        ioex.printStackTrace();
    }


    }

    public boolean equals(Car testCar){
            return this.registration.equals(testCar.getRegistration());
    }

}
