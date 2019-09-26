package serviceapp;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.module.FindException;
import java.io.Serializable;


public class Car implements Serializable{
    private String model;
    private String registration;
    private String color;

    public Car(String model,String reg ,String color){
        this.model = model;
        this.registration = reg.replaceAll(" ","");
        this. color = color;
    }

    public String getRegistration(){
        return this.registration;
    }


    public String getMake(){
        for(Make model: Make.values()){
            if (this.model.toUpperCase().equals(model.toString())){
                return this.model;
            }
        }
        return "Other";
    }

    public void storeCar(){
    System.out.println("Storing car....");
    try{

        String outFilePath = String.format("./serviceapp/work/%s/%s.car",getMake(),getRegistration());
        System.out.println(outFilePath);
//        File tmpCar file = new FileOutputStream("")
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