import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static int hisLen=0;
    public static void showMenu(){
        System.out.println("\n");
        line();
        System.out.println("[[==>> Application Menu <<==]]");
        System.out.println("\t<A> Booking");
        System.out.println("\t<B> Hall");
        System.out.println("\t<C> Showtime");
        System.out.println("\t<D> Reboot Showtime");
        System.out.println("\t<E> History");
        System.out.println("\t<F> Exit");
    }
    private static void line(){
        System.out.println("♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨♨");
    }
    private static String validateOption(Scanner input,String regex,String message){
        while (true) {
            System.out.print(message);//output user message
            String userInput = input.nextLine().trim();//user input for any condition (time,option,reboot...)
            Pattern pattern = Pattern.compile(regex);//use pattern to validate string
            if (pattern.matcher(userInput).matches()) {//if string meet format
                return userInput;//return user input
            } else {
                System.out.println("Invalid format");
            }
        }
    }
    private static String chooseShowTime(Scanner input,String[][] morning,String[][]afternoon,String[][]night){
        String showTime=validateOption(input,"[a-dA-D]",">> Please select show time( A | B | C |D ): ");//validate option a,b,c
        String[][] hall= showTime.equalsIgnoreCase("a") ?morning: showTime.equalsIgnoreCase("b") ?afternoon:night;//check hall(hall a,hall b, hall c)
        if(showTime.equalsIgnoreCase("d")) return showTime;
        line();
        System.out.println(showTime.equalsIgnoreCase("a")?"# Hall A":showTime.equalsIgnoreCase("b")?"# Hall B":"# Hall C");//show hall showtime
        for (int i=0;i< hall.length;i++){
            for(int j=0;j<hall[i].length;j++){
                System.out.print("|"+(char)('A'+i)+"-"+(j+1)+"::"+(hall[i][j]==null?"AV":"BO")+"|\t");
            }
            System.out.println();
        }
        return showTime;
    }
    private static boolean checkDuplicate(String[] userBooking){
        for (int i=0;i< userBooking.length;i++){
            for(int j=i+1;j<userBooking.length;j++){
                if(userBooking[i].trim().equalsIgnoreCase(userBooking[j].trim())) {//check all seat duplicate or not
                    System.out.println("Duplicate seat");
                    return true;
                }
            }
        }
        return false;
    }
    public static void booking(Scanner input,String[][] morning,String[][]afternoon,String[][]night,String[]history){
        line();//show line(-+-+-+-+)
        System.out.println("# Start booking proccess --->>>");
        showTime();//show time
        System.out.println(">> D) Back to menu");
        line();
        String showTime=chooseShowTime(input, morning, afternoon, night);
        if(showTime.equalsIgnoreCase("d")) {
            System.out.println("Back to menu ===>>>");
            return;
        }
        String[][] hall= showTime.equalsIgnoreCase("a") ?morning: showTime.equalsIgnoreCase("b") ?afternoon:night;//check hall(hall a,hall b, hall c)
        line();
        System.out.println("# INSTRUCTION");
        System.out.println("# Single: A-1");
        System.out.println("# Multiple (separate by comma): A-1,A-2");
        System.out.println("# Back to menu: NO");
        line();
        outLoop:
        while(true) {//check available seat
            System.out.print(">> Please select available seat: ");
            String booking=input.nextLine().replaceAll("\\s","");//user input seat to book (can be multiple seat separate by ,)
            if(booking.equalsIgnoreCase("no")) return;//return to menu
            String[] userBooking=booking.split(",");//split to individual seat
            if(checkDuplicate(userBooking)) continue;//check duplicate seat
            Pattern pattern = Pattern.compile("^\\s*[A-Za-z]\\s*-\\s*(?!0)\\d+\\s*$");//use regex
            for (String eachBook : userBooking) {//iterate all seat(for multiple seat that user want to book)
                if (pattern.matcher(eachBook.trim()).matches()) {//check seat format
                    boolean pareCheck=true;
                    timeLoop://loop to check seat available in hall and seat that free
                    for(int i=0;i<hall.length;i++){
                        for (int j=0;j<hall[i].length;j++){
                            if(eachBook.trim().equalsIgnoreCase((char) ('A' + i) + "-" + (j + 1))){//check seat available in hall
                                if(hall[i][j]!=null){//check seat already book or not yet
                                    System.out.println((char) ('A' + i) + "-" + (j + 1)+" is already booked");
                                    String seatOption=validateOption(input,"[yYnY]","Choose other seat?[y|n]: ");
                                    if(seatOption.equalsIgnoreCase("y")) continue outLoop;//input other seat
                                    else break outLoop;//stop input seat
                                }else{
                                    pareCheck=false;
                                    break timeLoop;//if seat available in hall and not booking yet// break input seat loop
                                }
                            }
                        }
                    }
                    if(pareCheck){
                        System.out.println("Invalid seat in hall");
                        continue outLoop;
                    }
                }else {
                    System.out.println("Invalid seat format");//if user input wrong seat format(should be a-1 or A-1,b-1...)
                    continue outLoop;
                }
            }
            String confirmBooking=validateOption(input,"[yYnN]","Are you sure to book the seat?[y|n] :");
            if(confirmBooking.equalsIgnoreCase("y")) {
                String id=validateOption(input,"^[A-Za-z0-9]{1,10}$","Enter Student Id(number or alphabet):");//input student id
                String hallShift= showTime.equalsIgnoreCase("a") ?"Hall A": showTime.equalsIgnoreCase("b") ?"Hall B":"Hall C";//find hall
                LocalDateTime dateTime=LocalDateTime.now();//current datetime
                DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");//format datetime
                String bookDateTime=dateTime.format(formatter);//datetime
                StringBuilder allbook= new StringBuilder("[");//declare seat will book
                for (String eachBook : userBooking) {//iterate all user book
                    for (int i = 0; i < hall.length; i++) {
                        for (int j = 0; j < hall[i].length; j++) {
                            if (eachBook.trim().toUpperCase().equals((char) ('A' + i) + "-" + (j + 1))) {//check seat
                                hall[i][j] = "BO";//booking
                                allbook.append((char) ('A' + i)).append("-").append(j + 1).append(", ");//note all book
                            }
                        }
                    }
                }
                allbook.append("\b\b]");
                history[hisLen++]=String.format("%-10s%-20s\n%-20s%-20s%-20s\n%-20s%-20s%-20s","#SEATS: ", allbook,"#HALL","#STU.ID","#CREDITED AT",hallShift,id,bookDateTime);
                line();
                System.out.println(allbook+" book successfully!");//show all seat that book successfully
                System.out.println("Thank you for booking!");
                line();
            }
            else {
                line();
                System.out.println("You don't book the seat");
                line();
            }
            break ;
        }
    }
    public static void showHall(String[][] morning,String[][]afternoon,String[][]night){
        for(int k=0;k<3;k++){
            line();
            String message=k==0?"# Hall morning":k==1?"# Hall afternoon":"# Hall Night";//message for each hall
            String[][] currentHall = k == 0 ? morning : k == 1 ? afternoon : night;//reference current hall
            System.out.println("\n"+message);
            for(int i=0;i<currentHall.length;i++){
                for(int j=0;j<currentHall[i].length;j++){
                    System.out.print("|" + (char) ('A' + i) + "-" + (j + 1) + "::" + (currentHall[i][j] == null ? "AV" : "BO") + "|\t");//seat format |A-1::AV| |A-2::BO|...
                }
                System.out.println();
            }
        }
    }
    public static void showTime(){
        line();
        System.out.println("Daily Showtime of CSTAD Hall: ");
        System.out.println(">> A) Morning (10:00AM - 12:30PM)");
        System.out.println(">> B) Afternoon (03:00AM - 05:30PM)");
        System.out.println(">> C) Night (07:00PM - 09:30PM)");
    }
    public static void reBoot(Scanner input,String[][] morning,String[][]afternoon,String[][]night,String[] history){
        String option=validateOption(input,"[yYnN]","Do you want to reboot all booking[y/n]").toLowerCase();//validate option
        if(option.equals("y")){
            System.out.println("\n\tReboot successfully..!");
            int k=0;
            hisLen=0;//set number of history to 0
            for(int i=0;i<night.length;i++) {
                for (int j = 0; j < night[i].length; j++) {
                    morning[i][j]=null;
                    afternoon[i][j]=null;
                    night[i][j]=null;
                    history[k++]=null;
                }
            }
        }
    }
    public static void showHistory(String[]history){
        line();
        System.out.println("# Booking History ");
        System.out.println("----------------------------------------------------------------");
        if(hisLen>0){//check have history or not
            for(int i=0;i<hisLen;i++){
                System.out.println("#NO : "+(i+1));//show record no
                System.out.println(history[i]);//show each record of history
                System.out.println("----------------------------------------------------------------");
            }
        }else{
            System.out.println("There is no history");
            System.out.println("----------------------------------------------------------------");
        }
        line();
    }
    public static void main(String[] args) {
        Scanner input =new Scanner(System.in);
        line();
        System.out.println("\t\t CSTAD HALL BOOKING SYSTEM");
        line();
        int row=Integer.parseInt(validateOption(input,"^\\s*(0*[1-9]|1\\d|2[0-5])\\s*$","Config total row in hall (1-25)  :").trim());//validate number of row(>0)
        int column=Integer.parseInt(validateOption(input,"^\\s*(0*[1-9]|1\\d|2\\d|30)\\s*$","Config total column in hall(1-30):").trim());//validate number of column(>0)
        String[][] morning=new String[row][column];//morning hall (hall A)
        String[][] afternoon=new String[row][column];//afternoon hall (hall B)
        String[][] night=new String[row][column];// night hall (hall C)
        String[]history=new String[row*column];//store history
        do{
            showMenu();
            String option=(validateOption(input,"[[a-fA-F]]",">> Please select menu no: ")).toUpperCase();//validate option (aA-fF)
            switch (option){
                case "A"->booking(input,morning,afternoon,night,history);
                case "B"-> showHall(morning,afternoon,night);
                case "C"-> showTime();
                case "D"-> reBoot(input,morning,afternoon,night,history);
                case "E"-> showHistory(history);
                case "F" -> {
                    if(validateOption(input, "[yYnY]", "Are you sure to exit?[y|n]: ").equalsIgnoreCase("y")) System.exit(0);
                }
            }
        }while (true);

    }
}
