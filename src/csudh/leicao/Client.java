package csudh.leicao;

/**
 * Created by caolei on 10/30/17.
 */
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Client implements java.io.Serializable {
    private String bizName, userID, hashPassword;
    private StockPrice curStockPrice;
    private StockPrice theLastDayLastPrice;
    private ArrayList<StockPrice> simulatedStkPrcList;
    private LCStack<StockPrice> loggedStkPrcStack;
    private static int numOfStkPrcDaily = 8;

    public Client (String bizName, String userID, String password, double curStockPrice){
        this.bizName=bizName;
        this.userID=userID;
        this.hashPassword = simpleHash(password);
        this.curStockPrice = new StockPrice(0.0,curStockPrice,LocalDateTime.now());
        this.simulatedStkPrcList = getSimulatedStkPrcStack();
        this.loggedStkPrcStack = new LCStack<>();
        this.theLastDayLastPrice=null;
    }

    protected boolean verifyCredentials(String userID, String attempedPassword) {
        String hashedAttmptPasswd = simpleHash(attempedPassword);
        if (userID.equals(this.userID) && hashedAttmptPasswd.equals(this.hashPassword)){
            return true;
        }
        return false;
    }

    private String simpleHash(String password){ //try use hashCode()
        // This method make a simple password hash processing to encrypt the password
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<password.length(); i++){
            sb.append((char)((int)password.charAt(i)*31^(i+1))%128); // a naive encryption method manipulating ASCII, might use SHA in production
        }
        return sb.toString();
    }

    // This method get the input of current Time and log all price stock until current Time today.
    public void logAllChangesTillTheCurr(LocalDateTime currDateTime) {
        this.loggedStkPrcStack= new LCStack<>();
        //LCStack<StockPrice> copyOfSimStack = simulatedStkPrcList.clone();
        for (StockPrice sp: simulatedStkPrcList){
            // log all the price changes until the current time
            if (sp.getDateTime().isBefore(currDateTime) )
                this.loggedStkPrcStack.push(sp);
            else if (sp.getDateTime().isEqual(currDateTime))
                this.loggedStkPrcStack.push(sp);
//            else
//                break;
        }

    }

    // This method simulate seven stock price everyday from 10:00 to 17:00, one price for every hour passed
    private ArrayList<StockPrice> getSimulatedStkPrcStack(){ // ***#*@#*$@#(#%)#$ has problem return null

        LCStack<StockPrice> tempStkPrcStack = new LCStack<>();
        ArrayList<StockPrice> tempStkPrcArrList = new ArrayList<>();
        Random random = new Random();
        if (theLastDayLastPrice!=null){
            StockPrice theFirstPriceToday = theLastDayLastPrice;
            theFirstPriceToday.setToTodayAndTimeAt10am();
            tempStkPrcStack.push(theFirstPriceToday);
        }
        // generating the other 7 stock prices and push them on to the stack.
        if(!tempStkPrcStack.empty()) { //if the client has previous day last price, we only need to simulate 7 stock prices
            // refer to the description at the top of the method signature
            genTheRestOfStkPrc(tempStkPrcStack);
        } else { // else if the client doesn't have previous day last price. We need to simulate 8 stock prices

            // generating the the starting stock price given that it has no previous day last price
            double prevPrice = 0.0;
            double currPrice = random.nextInt(100) + 1; // assume the regulation for IPO has a limit of $100
            StockPrice newStkPrc = new StockPrice(prevPrice, currPrice, null); //set Date & Time in the next line
            newStkPrc.setToTodayAndTimeAt10am();
            tempStkPrcStack.push(newStkPrc);
            // refer to the description at the top of the method signature
            genTheRestOfStkPrc(tempStkPrcStack);
        }
        while (!tempStkPrcStack.empty()) {
            tempStkPrcArrList.add(tempStkPrcStack.pop());
        }
        Collections.reverse(tempStkPrcArrList);
        this.simulatedStkPrcList=tempStkPrcArrList;
        return tempStkPrcArrList;
    }



    // This method to generate the other 7 stock prices (refer to the comment of getSimulatedStkPrcStack() above)
    private void genTheRestOfStkPrc(LCStack<StockPrice> simStkPrcStack){
        Random random = new Random();
        for (int i = 0; i < numOfStkPrcDaily; i++) {

            double prevPrice = simStkPrcStack.peek().getCurrPrice();

            // new price has stable change based on previous price
            // This part "((-1)^(random.nextInt(1)+1))" is to randomly decide if the price increases or decreases
            // This part "random.nextDouble()*10" is to randomly decide the price change within a change limit of $10
            double change = Math.pow((-1),(random.nextInt(2)+1)) * random.nextDouble() * 10;

            // This while loop makes sure that currPrice is not negative
            while ((prevPrice + change) < 0) {
                change = Math.pow((-1),(random.nextInt(2)+1)) * random.nextDouble() * 10;
            }

            double currPrice = prevPrice + change;

            // The next line generate the DateTime for the next stockprice to be generated
            LocalDateTime currTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10+i+1,0,0));

            // The next line push a new stock price onto the stack
            simStkPrcStack.push(new StockPrice(prevPrice, currPrice, currTime));
        }
    }



    public String getBizName() {
        return bizName;
    }

    public String getUserID() {
        return userID;
    }

    public StockPrice getCurStockPrice() {
        return curStockPrice;
    }

    public StockPrice getTheLastDayLastPrice() {
        return theLastDayLastPrice;
    }

    public LCStack<StockPrice> getLoggedStkPrcStack() {

        return loggedStkPrcStack;
    }

    public void setLoggedStkPrcStack(LCStack<StockPrice> loggedStkPrcStack) {
        theLastDayLastPrice = loggedStkPrcStack.peek();
        this.loggedStkPrcStack = loggedStkPrcStack;
    }
}


//    public void printEncPass() {
//        System.out.println(hashPassword);
//    }
