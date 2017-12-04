package csudh.leicao;

/**
 * Created by caolei on 10/30/17.
 */
public interface StackInterface<E> {
    E push(E obj);  // To insert at Top of Stack (TOS)
    E pop();    // To remove items from TOS
    E peek();   // To take a look at the TOS
    boolean empty();    // Check if the stack is empty

}
