package csudh.leicao;

import java.util.Scanner;

/**
 * Created by caolei on 3/19/17.
 */

//This class is from CSC123 Textbook - Java Programming From the Ground Up (Ralph Bravaco Shai Simonson) P.648 Example 14.3
    // I think it's a sound way to get input using scanner, with sound error checking for all common types of data input.
    // I always use this ReadData class and its static methods to get console inputs.
public class ReadData {
    Scanner input = new Scanner(System.in);

    public static String readString(){
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    public static int readInt() {
        // returns a valid integer that is supplied interactively
        Scanner input = new Scanner(System.in);
        boolean correct; // is the input correct?
        boolean negative = false; String number;
        do
        {
            correct = true;
            number = input.next();
            if (number.charAt(0)=='-') {
            negative = true;

        // input string
        // read a string
        // negative number?
            number = number.substring(1, number.length()); }
            for ( int i = 0; i < number.length(); i++  )
            if (!Character.isDigit(number.charAt(i)))  {// input error
                correct = false;
                System.out.print("Input error, reenter: ");
                break;
            }
        } while(!correct);
        if (negative)
            return   Integer.parseInt(number);
        return Integer.parseInt(number);
    }

    public static double readDouble() {
        // out of the if-block
        // returns a valid double that is supplied interactively
        Scanner input = new Scanner(System.in);
        boolean correct;
        boolean negative = false;
        String number;
        // negative number?
        // index of the decimal point
        int decimalPlace;
        do {
            correct = true;
            number = input.next();
            if (number.charAt(0) == '-') {
                negative = true;
                number = number.substring(1, number.length());
            }
                decimalPlace = number.indexOf("."); //  -1 if no decimal point
            // validate that the characters up to the decimal are digits
            // this loop is skipped if there is
            // no decimal point or the decimal occurs as the first character
            for (int i = 0; i < decimalPlace; i++  ) // skipped if decimalPlace == -1
                if (!Character.isDigit(number.charAt(i))) // input error
                {
                    correct = false;
                    System.out.print("Input error, reenter: ");
                    break; // out of the if-block
                 }
            // validate that the characters after the decimal are digits
            for (int i = decimalPlace + 1; i < number.length(); i++)
                if (!Character.isDigit(number.charAt(i))) // input error
                {
                    correct = false;
                    System.out.print("Input error, reenter: ");
                    break;
                }
        } while (!correct); if (negative)
            return   Double.parseDouble(number);
        return Double.parseDouble(number);
    }
}
