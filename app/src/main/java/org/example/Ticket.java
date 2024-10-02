package org.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Tickets
 */
public class Ticket implements Train, Serializable{

    int available_lower_birth = Train.lower;
    int available_middle_birth = Train.middle;
    int available_upper_birth = Train.upper;
    int available_waiting_tickets = Train.waiting;
    int available_rac_tickets = Train.rac;

    Map<Integer, Integer> allotment = new HashMap<Integer, Integer>();
    
    Queue<Passenger> waiting_list = new LinkedList<Passenger>();
    Queue<Passenger> rac_list = new LinkedList<Passenger>();
    List<Passenger> lower = new ArrayList<Passenger>();
    List<Passenger> middle = new ArrayList<Passenger>();
    List<Passenger> upper = new ArrayList<Passenger>();
    

	public void book(Passenger passenger) {
        
        int availability = checkAnyAvailability();
        
        if (availability == -3) {
            passenger.setAlloted("not_allotted");
            System.out.println("No Tickets Available Right Now!!!");
            return;
        }

        if (passenger.getBirthPreference() == 0 && isLowerAvailable() ) {
            bookToLower(passenger);
            return;
        }

        if (passenger.getBirthPreference() == 1 && isMiddleAvailable() ) {
            bookToMiddle(passenger);
            return;
        }

        if (passenger.getBirthPreference() == 2 && isUpperAvailable() ) {
            bookToUpper(passenger);
            return;
        }

        switch (availability) {
            case -1:
                moveToWaiting(passenger);
                return;
            case -2:
                moveToRAC(passenger);
                return;
            case 0:
                bookToLower(passenger);
                return;
            case 1:
                bookToMiddle(passenger);
                return;
            case 2:
                bookToUpper(passenger);
                return;
        }

	}

    private void removePassenger(Passenger p, List<Passenger> l){
        for (Passenger passenger : l) {
            if ( p.hashCode()==passenger.hashCode() ) {
                System.out.println( p.hashCode() + " " + passenger.hashCode() + ( p.hashCode()==passenger.hashCode() ) );
                System.out.println("removed passenger => " + passenger.toString());
                allotment.remove(p.getId());
                l.remove(passenger);
                return;
            }
        }
    }

	public void Cancel(Passenger p) {
        if ( allotment.containsKey(p.getId()) ) {
            int berth = allotment.get(p.getId());
            switch (berth){
                case 0:
                {
                    removePassenger(p, lower);
                    available_lower_birth++;
                    break;
                }
                case 1:
                {
                    removePassenger(p, middle);    
                    available_middle_birth++;
                    break;
                }
                case 2:
                {
                    removePassenger(p, upper);
                    available_upper_birth++;
                    break;
                } 
            }

            if(!waiting_list.isEmpty()) {
                book(waiting_list.poll());
                available_waiting_tickets++;
                if(!rac_list.isEmpty()) {
                    moveToWaiting(rac_list.poll());
                    available_rac_tickets++;
                }
            }
        }
	}

	public void Available() {
        System.out.println("Lower -> " + available_lower_birth);
        System.out.println("Middle -> " + available_middle_birth);
        System.out.println("Upper -> " + available_upper_birth);
        System.out.println("Waiting -> " + available_waiting_tickets);
        System.out.println("RAC -> " + available_rac_tickets);
	}

    private void printPassengers(List<Passenger> l){
        for (Passenger p : l) {
            System.out.println(p.toString());
        }
        System.out.println();
    }

    private void printQueues(Queue<Passenger> q){
        for (Passenger p : q) {
            System.out.println(p.toString());
        }
        System.out.println();
    }

	public void Booked() { 
        printPassengers(lower);
        printPassengers(middle);
        printPassengers(upper);
        printQueues(waiting_list);
        printQueues(rac_list);
	}

    private int checkAnyAvailability(){
        if ( isLowerAvailable() )
           return 0;
        if ( isMiddleAvailable() )
           return 1;
        if ( isUpperAvailable() )
           return 2;
        if (this.available_waiting_tickets > 0)
           return -1;
        if (this.available_rac_tickets > 0) 
           return -2;
        else
           return -3;
    }

    private boolean isLowerAvailable() {
        return this.available_lower_birth > 0;
    }
    
    private boolean isMiddleAvailable() {
        return this.available_middle_birth > 0;
    }

    private boolean isUpperAvailable() {
        return this.available_upper_birth > 0;
    }

    private void bookToLower(Passenger p){
        lower.add(p);
        p.setAlloted("lower " + (Train.lower-available_lower_birth+1) );
        available_lower_birth--;
        allotment.put(p.getId(), 0);
    }
    private void bookToMiddle(Passenger p){
        middle.add(p);
        p.setAlloted("middle " + (Train.middle - available_middle_birth+1) );
        available_middle_birth--;
        allotment.put(p.getId(), 1);
    }
    private void bookToUpper(Passenger p){
        upper.add(p);
        p.setAlloted("upper " + (Train.upper - available_upper_birth +1 ) );
        available_upper_birth--;
        allotment.put(p.getId(), 2);
    }
    private void moveToWaiting(Passenger p) {
        waiting_list.offer(p);
        p.setAlloted("waiting " + (Train.waiting - available_waiting_tickets + 1) );
        available_waiting_tickets--;
    }
    private void moveToRAC(Passenger p) {
        rac_list.offer(p);
        p.setAlloted("rac " + ( Train.rac - available_rac_tickets + 1 ) );
        available_rac_tickets--;
    }
}

