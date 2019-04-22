import java.util.Random;

/**
 * This class implements a consumer of events.
 *
 */
public class Consumer extends Thread {
    private EventStorage storage;
    /**
     * Constructor of the class. Initialize the storage
     * @param storage The store to work with
     */

    public Consumer(EventStorage storage, String name){
        this.storage = storage;
        this.setName(name);
    }

    /**
     * Core method for the consumer. Consume until killed by main
     */
    @Override
    public void run(){
        while(!Thread.currentThread().interrupted()) {
            String element = storage.get(this.getName());
            sleepRandomly(50,200);
        }

        System.out.printf("Thread %s has been interrupted.\n", this.getName());
    }

    private void sleepRandomly(int low, int high) {
        Random r = new Random();
        int result = r.nextInt(high-low) + low;
        try {
            Thread.sleep(result);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
