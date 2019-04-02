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
        System.out.printf("Productos recibidos totales: %d\n", allReceivedProducts);

        System.out.println("Storage size: " + storage.size());
        if (storage.size() == maxSize) {
            notify();
            System.out.println("Producto descartado de: " + name);
            return;
        }

        notDescartedReceivedProducts++;
        System.out.printf("Productos recibidos no descartados: %d\n", notDescartedReceivedProducts);
        storage.add(new Date());
        System.out.printf("Set: %d - %s\n", storage.size(), name);
        notify();
    }
    public synchronized void get(String name) {
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
//        notify();
    }

    public int getAllReceivedProducts() {
        return allReceivedProducts;
    }

    public int getNotDescartedReceivedProducts() {
        return notDescartedReceivedProducts;
    }
}
