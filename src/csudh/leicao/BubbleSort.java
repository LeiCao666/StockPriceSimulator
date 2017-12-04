package csudh.leicao;

/**
 * Created by caolei on 11/2/17.
 */

// Optimized Bubble Sort, utilizing a break if no swapping happens in the last traverse
    // in ascending order
public class BubbleSort {
    public void sort(StockPrice[] arr){
        int n = arr.length;
        boolean swapped = false;
        for (int i = 0; i < n-1; i++) {
            swapped = false;
            for (int j =0; j < n-i-1; j++) {
                if (arr[j].compareTo(arr[j+1]) > 0 ) {
                    swap(arr[j], arr[j + 1]);
                    swapped = true;
                }
            }
            // if no two elements are swapped by the inner loop, then break
            // this mechanism optimize BubbleSort
            if (swapped==false)
                break;
        }
    }

    private void swap(StockPrice a, StockPrice b) {
        StockPrice temp = a;
        a = b;
        b = temp;
    }
}
