package csudh.leicao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by caolei on 10/30/17.
 */
//Serialization: https://www.tutorialspoint.com/java/java_serialization.htm

public class StockExCenter implements java.io.Serializable {

    private ArrayList<Client> listOfClients; // will try hashmap for listOfClient later / or use set() to prevent duplication
    private Client curClient;
    protected static final int LOGIN_SUCCESS = 1;
    protected static final int PASSWD_INVALID = 2;
    protected static final int CLIENT_NOT_EXIST = 3;

    public StockExCenter() {
        listOfClients = new ArrayList<Client>();
    }

    public boolean registerClient(String bizName, String userID, String password, double currStcPrc){
        Client newClient = new Client(bizName, userID, password,currStcPrc);
        if (checkIfClientExist(userID))
            return false;
        listOfClients.add(newClient);
        return true;
    }

    // This method is to login a client,
        // return LOGIN_SUCCESS if credentials are verified;
        // return PASSWD_INVALID if password is not matched;
        // return CLIENT_NOT_EXIST if userID is not matched.
    // This method also clear previous-day stock prices,
        // of which we copy the last one, change the dataTime and push into today's stack
    public int clientLogin(String userID, String attemptedPassword){
        for (Client c : listOfClients) {
            if (userID.equals(c.getUserID())) {
                if (c.verifyCredentials(userID, attemptedPassword)) {
                    curClient = c;

                    return LOGIN_SUCCESS;
                }
                return PASSWD_INVALID;
            }
        }
        return CLIENT_NOT_EXIST;
    }

    private boolean checkIfClientExist(String userID) {
        for (Client c : listOfClients) {
            if (userID.equals(c.getUserID()))
                return true;
        }
        return false;
    }

    public Client getCurClient() {
        return curClient;
    }

    public void displayHiPrice() {
        System.out.println(curClient.getLoggedStkPrcStack().getHighest().toString());
    }

    public void displayLoPrice() {
        System.out.println(curClient.getLoggedStkPrcStack().getLowest().toString());
    }

    // This method
    public void saveAndLogout() {
        /*This part save save the current day last price as the First price of the next day,
        * and clear loggedStockPrice stack */
        StockPrice theNextDayFirstPrice = curClient.getLoggedStkPrcStack().pop();
        LCStack<StockPrice> newStackForNextDay = new LCStack<>();
        newStackForNextDay.push(theNextDayFirstPrice);
        curClient.setLoggedStkPrcStack(newStackForNextDay);

        /**till here******/

        // Logout current client;
        curClient = null;

        //
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("StockExCenter.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in StockExCenter.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
        System.exit(0);
    }

    // This method display all Stock Prices in Chronological Reverse
    public void displayInChonReverse() {
        System.out.println(curClient.getLoggedStkPrcStack().getReversedStack().toString());
    }

    public ArrayList<Client> getListOfClients() {
        return listOfClients;
    }
}
