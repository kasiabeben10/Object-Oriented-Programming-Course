package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;


public class World {
    public static void main(String[] args){
        System.out.println("system wystartował");
        MoveDirection[] directions = OptionsParser.parse(args);
        //run(args);
        run(directions);
        System.out.println("system zakończył działanie");
    }

// PKT 9 metoda run informująca o tym że zwierzak idzie do przodu:
//    public static void run(){
//        System.out.println("Zwierzak idzie do przodu");
//    }


// PKT 11, 12 metoda run informująca że zwierzak idzie do przodu i wypisująca przekazane argumenty
//    public static void run(String[] args){
//        System.out.println("Zwierzak idzie do przodu");
//        for(int index=0;index<args.length-1;index++){
//            System.out.print(args[index]+", ");
//        }
//        if (args.length>0) {
//            System.out.println(args[args.length - 1]);
//        }
//    }


// PKT 14 metoda run interpretująca wprowadzone argumenty, ignorująca niepoprawne polecenia
//    public static void run(String[] args){
//        for (String arg: args) {
//            switch (arg){
//                case "f" -> System.out.println("Zwierzak idzie do przodu");
//                case "b" -> System.out.println("Zwierzak idzie do tyłu");
//                case "r" -> System.out.println("Zwierzak skręca w prawo");
//                case "l" -> System.out.println("Zwierzak skręca w lewo");
//            };
//        }
//    }

    public static void run(MoveDirection[] directions){
        for (MoveDirection direction: directions){
            switch (direction){
                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("Zwierzak skręca w prawo");
                case LEFT -> System.out.println("Zwierzak skręca w lewo");
            };
        }
    }
}
