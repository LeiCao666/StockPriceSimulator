package csudh.leicao;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created by caolei on 10/30/17.
 */

// This stack reallocate itself if full.
public class LCStack<E> implements StackInterface<E>, java.io.Serializable{

    private E[] theData;    // Storage for the stack
    private int topOfStack = -1;    // Top of Stack, indicating nothing is there
    private static final int INITIAL_CAPACITY = 10;     // Default capacity
    private int capacity = INITIAL_CAPACITY;

    // Default constructor, creates a stack of default capacity
    public LCStack(){
        this.theData = (E[]) new Object[capacity];
    }

    // Overloaded constructor, creates a stack for user defined size
    public LCStack(int size) {
        this.theData = (E[]) new Object[size];
    }


    @Override
    public E push(E obj) {
        // This method add elements to the stack if there is space
        // Check if the stack is full
        if (topOfStack == this.theData.length - 1) {
            reallocate();
        }
        // else if there is space on the stack, insert the data

        return this.theData[++topOfStack] = obj; // test
    }

    @Override
    public E pop() {
        // This method removes the element from the top of stack
        // Make sure the stack is not empty
        if (this.empty()) {
            System.out.println("Stack Underflow!");
            return null;
        }
        return this.theData[topOfStack--];
    }

    @Override
    public E peek() {
        if (this.empty()) {
            System.out.println("Stack Empty!");
            return null;
        }
        return this.theData[topOfStack]; // Return the top element
    }

    @Override
    public boolean empty() {
        return (topOfStack == -1);
    }

    //Display the elements of the Stack
    public void display() {
        System.out.print("Stack: ");
        for(int i = 0; i <= this.topOfStack; i++) {
            System.out.print(this.theData[i] + " | ");
        }
        System.out.println();
    }

    public void reallocate() {
        E[] newData = (E[]) new Object[capacity*2];
        System.arraycopy(theData,0,newData,0,capacity);
//        for (int i = 0 ; i < theData.length; i++) {
//            newData[i] = theData[i];
//        }
        capacity *= 2;
        theData = newData;
    }

    public boolean searchFor(E obj){
        for (E element : theData) {
            if (element.equals(obj))
                return true;
        }
        return false;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<topOfStack; i++) {
            sb.append(theData[i].toString() + "\n");
        }
        return sb.toString();
    }

    public StockPrice getHighest() {
        if (empty()){
            System.out.println("No stock price is recorded yet");
            return null;
        }

        StockPrice highest = (StockPrice) theData[0];
        for (int i = 1; i<topOfStack; i++) {
            highest = ((highest.compareTo(theData[i])==-1 ? (StockPrice) theData[i] : highest ));
        }
        return highest;
    }

    public StockPrice getLowest() {
        if (empty()){
            System.out.println("No stock price is recorded yet");
            return null;
        }
        StockPrice lowest = (StockPrice) theData[0];
        for (int i = 1; i<topOfStack; i++) {
            lowest = ((lowest.compareTo(theData[i])==1 ? (StockPrice) theData[i] : lowest ));
        }
        return lowest;
    }

    // Overloaded method search with price
    public StockPrice searchFor(double curPrice)
    {
        if (theData.length==0){
            System.out.println("No stock price is recorded yet");
            return null;
        }
        for (E sp : theData) {
            if (((StockPrice)sp).getCurrPrice()==curPrice){
                return (StockPrice) sp;
            }
        }
        return null;
    }

    // Overloaded method search with LocalDateTime
    public StockPrice searchFor(LocalDateTime dateTime) {
        if (theData.length==0){
            System.out.println("No stock price is recorded yet");
            return null;
        }
        for (E sp : theData) {
            if (((StockPrice)sp).getDateTime().isEqual(dateTime)){
                return (StockPrice) sp;
            }
        }
        return null;
    }

    // This method sort this stack
    public void getSortedStack() {
        if (theData.length==0){
            System.out.println("No stock price is recorded yet");
            return;
        }
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.sort((StockPrice[])theData);
    }

    // A helper static method to reverse a stack by popping one element at a time and push onto a new stack
    public LCStack<E> getReversedStack() {
        LCStack<E> reversedStack = new LCStack<>();
        LCStack<E> temp = new LCStack<E>();
        E spTemp = null;
        while(!this.empty())
            spTemp = this.pop();
            reversedStack.push(spTemp);
            temp.push(spTemp);
        while (!temp.empty()) {
            this.push(temp.pop());
        }
        return reversedStack;
    }

    public E[] getData(){
        return theData;
    }

    public int getTopOfStack() {
        return topOfStack;
    }

//    public LCStack<E> clone() {
//        LCStack<E> clone = new LCStack<E>();
//        LCStack<E> temp = new LCStack<E>();
//        while (cop)
//        return clone;
//    }
}
