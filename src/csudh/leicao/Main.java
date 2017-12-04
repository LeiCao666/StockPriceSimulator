package csudh.leicao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

import static java.util.Objects.hash;

public class Main {

    public static void main(String[] args) {
	// 1. Read serialized StockExCenter Object, if no such object is found, instantiate a new one.
    // 2. call menu() from Menu class

    // read previous serialized data
        // if not empty, read loggedStockPrice and set the first and only Stock Price to current date and 10am.
        StockExCenter stockExCenter = null;
        try {
            FileInputStream fileIn = new FileInputStream("StockExCenter.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            stockExCenter = (StockExCenter) in.readObject();
            System.out.println("StockExCenter object is read successfully");
            //stockExCenter.getCurClient().getLoggedStkPrcStack().peek().setToTodayAndTimeAt10am();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println("StockExCenter.ser not found, instantiating new StockExCenter object");
            stockExCenter = new StockExCenter();
        } catch (ClassNotFoundException c) {
            System.out.println("StockExCenter object not found, instantiating new one");
            stockExCenter = new StockExCenter();
        }

        Menu menu = new Menu(stockExCenter);
        while (true) {
            menu.startMenu();
//            System.out.print("Press ENTER to continue: ");
//            ReadData.readString();
        }

    }


}
