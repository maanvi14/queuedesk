package com.groww.intern.queuedesk;
import java.util.Scanner;
public class QueueNumberSimulator {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        System.out.print("Starting ticket number: ");
        if(!in.hasNextInt()){
            System.out.println("Please enter a whole number.");
            in.close();
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
        int served = 0, vip = 0,  recheck = 0;
        for(int i = 0 ; i < count ; i++){
            int ticket = start + i;
            served++;

            System.out.println("Now serving ticket #" + ticket);

            if(served % 10 == 0){
                recheck++;
                System.out.println(" -> PRIORITY RECHECK");
            }
            else if(served % 5 == 0){
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
