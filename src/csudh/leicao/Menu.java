package csudh.leicao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

/**
 * Created by caolei on 10/31/17.
 */
public class Menu implements java.io.Serializable {
    StockExCenter stockExCenter;
    public Menu(StockExCenter stockExCenter){
        this.stockExCenter = stockExCenter;
    }
    public void startMenu() {
        System.out.println("*************************************************************");
        System.out.println("1. Create an account"); // will ask for bizName, userID, currStockPrice
        System.out.println("2. Login with your UserID and Password"); // will ask for the current time, and display everything
        System.out.println("3. Log all changes to the stock price until the current price");
        System.out.println("4. Display all prices in reverse chronological order");
        System.out.println("5. Display today's highest stock price and time");
        System.out.println("6. Display today's lowest stock price and time");
        System.out.println("7. Save and Quit for today");
        System.out.println("*************************************************************");
        System.out.print("Your choice: ");
        int choice = ReadData.readInt();
        if (choice==1){
            System.out.print("Please enter your business name: ");
            String bizName = ReadData.readString();
            System.out.print("Please create your User ID: ");
            String userID = getUserID(bizName);
            System.out.print("Please create a password: ");
            String password;
            final int lenOfPasswd = 8;
            // This while loop makes sure the password is 8-character long
            while(true){
                password = ReadData.readString();
                if (password.length()==lenOfPasswd)
                    break;
                else
                    System.out.println("Your password must have 8 characters, please retry: ");
            }
            System.out.print("Please enter the current stock price of your company: ");
            double currStcPrc = ReadData.readDouble();
            // register the new client
            this.stockExCenter.registerClient(bizName, userID, password, currStcPrc);
            System.out.println("\nYour account is created successfully. Thank you!\nNow please login\n");

        } else if (choice == 2) {
            String attempUserID;
            String attempPasswd;
            int verification;
            do {
                System.out.print("Please enter your User ID: ");
                attempUserID = ReadData.readString();
                System.out.print("Please enter your password: ");
                attempPasswd = ReadData.readString();
                verification =stockExCenter.clientLogin(attempUserID, attempPasswd);
                if (verification==2){
                    System.out.println("Your password is invalid, please retry: ");
                } else if (verification == 3) {
                    System.out.println("The user doesnot exist, please retry: ");
                }
            } while (verification!=StockExCenter.LOGIN_SUCCESS);
            System.out.println("\nLogin Successful. Welcome back, " + attempUserID + ".\n");
        } else if (choice == 3) {
            if (repetitiveCode())
                System.out.println(stockExCenter.getCurClient().getLoggedStkPrcStack().toString());
        } else if (choice == 4) {
            if (repetitiveCode()) {
                LCStack<StockPrice> reversedStack = new LCStack<>();
                Object[] temp = stockExCenter.getCurClient().getLoggedStkPrcStack().getData();
                int topOfStack = stockExCenter.getCurClient().getLoggedStkPrcStack().getTopOfStack();
                for (int i=topOfStack-1; i>=0; i--) {
                    reversedStack.push((StockPrice)temp[i]);
                }
                System.out.println(reversedStack.toString());
            }
        } else if (choice == 5) {
            if (repetitiveCode()) {
                System.out.print("The highest price is: ");
                System.out.println(stockExCenter.getCurClient().getLoggedStkPrcStack().getHighest().toString());
                System.out.println();
            }

        } else if (choice == 6) {
            if (repetitiveCode()){
                System.out.print("The lowest price is: ");
                System.out.println(stockExCenter.getCurClient().getLoggedStkPrcStack().getLowest().toString());
                System.out.println();
            }

        } else if (choice == 7) {
            stockExCenter.saveAndLogout();
        }
    }

    private boolean repetitiveCode(){
        if (stockExCenter.getCurClient() == null) {
            System.out.println("\nPlease Login first! \n");
            return false;
        }
        System.out.println("Please enter the current time (format xx:xx 24-hour, from 10:00 to 17:00)");
        String sTime = ReadData.readString();
        // * add : check format
        while (!sTime.matches("[0-2][0-9]\\:[0-6][0-9]")) {
            System.out.print("Invalid format, please retry: ");
            sTime = ReadData.readString();
        }
        LocalTime time = LocalTime.of(Integer.parseInt(sTime.substring(0,2)),Integer.parseInt(sTime.substring(3,5)));
        stockExCenter.getCurClient().logAllChangesTillTheCurr(time.atDate(LocalDate.now()));
        return true;
    }
    private String getUserID(String bizName){
        String userID = "";
        int count = 0;

        // 2 attempts, after two generate an alphanumber userID
        do {
            userID = ReadData.readString();
            boolean success = true;
            for (Client client : stockExCenter.getListOfClients()) {
                if (client.getUserID().equals(userID)) {
                    System.out.println("The User ID already exist. Please try another one: ");
                    count++;
                    success=false;
                    break;
                }
            }
            if (success)
                break;

        } while (count<2);
        if (count!=2)
            return userID;
        else{
            Random random = new Random();
            //int digits = random.nextInt(9000) + 1000; // range(0,8999) + 1000 = range(1000,9999)
            // generating the alphabet part of the userID. The alphabet part has to be based on the bizName
            String bizNameWithoutSpace = bizName.replace("\\s", "");
            int alphabetLength;
            final int requiredLenOfUserID = 8;
            if (bizNameWithoutSpace.length()>requiredLenOfUserID-1)
                alphabetLength = requiredLenOfUserID-1; // leave one space for number
            else
                alphabetLength = bizNameWithoutSpace.length();

            String alphaPart = bizNameWithoutSpace.substring(0,alphabetLength);

            // generating the number part of the userID.
            StringBuilder numBuilder = new StringBuilder();
            String numbers = "0123456789";

            for (int i=0; i<(requiredLenOfUserID-alphabetLength); i++) {
                numBuilder.append(numbers.charAt(random.nextInt(numbers.length())));
            }
            String numPart = numBuilder.toString();

            userID = alphaPart + numPart;
            System.out.println("Your assigned User Id is " + userID);
            return userID;
        }
    }
}
