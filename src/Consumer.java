import java.util.Random;

/**
 * This class implements a consumer of events.
 *
 */
public class Consumer implements Runnable {

    private String name;
    private EventStorage storage;
    /**
     * Constructor of the class. Initialize the storage
     * @param storage The store to work with
     */

    public Consumer(EventStorage storage, String name){
        this.storage=storage;
        this.name = name;
    }

    /**
     * Core method for the consumer. Consume 100 events
     */
    @Override
    public void run(){
        for (int i=0; i<200; i++){
            System.out.println("Llamada get " + i + " - " + name);

            storage.get(name);
            sleepRandomly(50,200);
        }
    }

    private void sleepRandomly(int low, int high) {
        Random r = new Random();
        int result = r.nextInt(high-low) + low;
        try {
            Thread.sleep(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
