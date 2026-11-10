package com.queuedesk;
import java.util.Scanner;
public class QueueNumberSimulator {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in); //User input using scanner , we used scanner to accept the starting ticket number and customer count from the terminal.
        //accepted from the terminal:starting ticket number and number of customers to simulate.

        System.out.print("Starting ticket number: ");
        if(!in.hasNextInt()){ //Input validation hasNextInt() checks if the input is a valid integer. If not, it prompts the user to enter a whole number and exits the program.
            System.out.println("Please enter a whole number.");
            in.close();//closes the scanner after completing input processig.
            return;
        }
        int start = in.nextInt();

        System.out.print("Number of customers to simulate:");
        if(!in.hasNextInt()){
            System.out.println("Please enter a whole number.");
            in.close();
            return;
        }
        int count = in.nextInt();

        if(count < 0){
            System.out.println("Count cannot be negative.");
            in.close();
            return;
        }
        int served = 0, vip = 0,  recheck = 0; //used variables to maintain total customers served, total vip-lane tickets, total priority-recheck tickets.
        for(int i = 0 ; i < count ; i++){ //used a for lop to itreate through the customer count. Generated sequential ticket numbers starting from the user-provided number.
            int ticket = start + i;
            served++;

            System.out.println("Now serving ticket #" + ticket);//printed "Now serving ticket #N" for every customer served.

            //Used the modulus operator (%) and conditional statements to classify tickets
            if(served % 10 == 0){//Every 10th customer served receives the priority recheck flag.
                recheck++;
                System.out.println(" -> PRIORITY RECHECK");
            }
            else if(served % 5 == 0){ //Every 5th customer served receives the VIP-lane flag.
                vip++;
                System.out.println(" -> VIP lane");
            }
        }
        System.out.println("Total served:" + served);
        System.out.println("Total VIP: " + vip);
        System.out.println("Total Priority Rechecks:" + recheck);

        in.close();
    }
    
}
