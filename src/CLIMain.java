import geschäftslogik.MediaVerwalter;
import data.memorymanagement.MemoryManager;
import data.producerManager.ProducersManager;
import controller.MVcontroller;
import userInterface.cliView.View;

import java.math.BigDecimal;
import java.util.Scanner;


public class CLIMain {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        long size = 0;
        boolean flag = true;
        while (flag) {
            System.out.println("Bitte die größe von Speicher in Giga bytes eingeben");
            try {
                size = Long.parseLong(scn.nextLine());
                flag = false;
            } catch (Exception e){
                System.out.println("falsche Angaben !!");
            }
        }
        System.out.println("Speicher größe = " + size*1024 + " MBs");
        ProducersManager producersManager = new ProducersManager();
        MemoryManager memoryManager = new MemoryManager(new BigDecimal(size*1024));
        MediaVerwalter model = new MediaVerwalter(producersManager, memoryManager);
        View view = new View();
        MVcontroller controller = new MVcontroller(view, model);
        controller.setViewAnzeigen(true);
        controller.start();
    }
}
