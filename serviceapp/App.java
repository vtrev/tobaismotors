package serviceapp;

import serviceapp.watcher.Watcher;

public class App{

public static void main(String[] args) {

        Watcher watcher =  new Watcher();
        watcher.watch();
    }
}