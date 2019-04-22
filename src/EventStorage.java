import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class EventStorage {
    private int maxSize;
    private int allReceivedProducts;
    private int notDescartedReceivedProducts;
    private Queue<Date> storage;

    public EventStorage() {
        allReceivedProducts = 0;
        notDescartedReceivedProducts = 0;
        maxSize=10;
        storage=new LinkedList<>();
    }

    public synchronized void set(String name) {
        allReceivedProducts++;

        System.out.printf("Storage size: %d/%d\n" ,storage.size(), maxSize);
        if (storage.size() == maxSize) {
            notify();
            System.out.printf("Producto desc. de:\t%s.\tTotal: %d\tRecibidos sin descartar: %d\n",
                    name, allReceivedProducts, notDescartedReceivedProducts);
            return;
        }

        notDescartedReceivedProducts++;
        storage.add(new Date());
        System.out.printf("Product Set: [%d]\t- %s.\tTotal: %d\tRecibidos sin descartar: %d\n",
                storage.size(), name, allReceivedProducts, notDescartedReceivedProducts);
        notify();
    }
    public synchronized String get(String name) {
        while(storage.size() == 0) {
            try {
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String element = storage.poll().toString();
        System.out.printf("Get: %d: %s - %s\n", storage.size(), element, name);
        return element;
//        notify();
    }

    public int getAllReceivedProducts() {
        return allReceivedProducts;
    }

    public int getNotDescartedReceivedProducts() {
        return notDescartedReceivedProducts;
    }

    public int getSize(){ return storage.size(); }
}
