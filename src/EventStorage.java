import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class EventStorage {
    private int maxSize;
    private int allReceivedProducts;
    private int notDiscardedReceivedProducts;
    private Queue<Date> storage;

    public EventStorage(int maxSize) {
        allReceivedProducts = 0;
        notDiscardedReceivedProducts = 0;
        this.maxSize = maxSize;
        storage = new LinkedList<>();
    }

    public synchronized void set(String name) {
        allReceivedProducts++;

        System.out.printf("Storage size: %d/%d\n" ,storage.size(), maxSize);
        if (storage.size() == maxSize) {
            notify();
            System.out.printf("Producto desc. de:\t%s.\tTotal: %d\tRecibidos sin descartar: %d\n",
                    name, allReceivedProducts, notDiscardedReceivedProducts);
            return;
        }

        notDiscardedReceivedProducts++;
        storage.add(new Date());
        System.out.printf("Product Set: [%d]\t- %s.\tTotal: %d\tRecibidos sin descartar: %d\n",
                storage.size(), name, allReceivedProducts, notDiscardedReceivedProducts);
        notify();
    }
    public synchronized String get(String name) {
        while(storage.size() == 0) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "";
            }
        }
        String element = storage.poll().toString();
        System.out.printf("Get: %d: %s - %s\n", storage.size(), element, name);
        return element;
    }

    public int getAllReceivedProducts() {
        return allReceivedProducts;
    }

    public int getNotDiscardedReceivedProducts() {
        return notDiscardedReceivedProducts;
    }

    public synchronized int getLoad(){ return storage.size(); }
}
