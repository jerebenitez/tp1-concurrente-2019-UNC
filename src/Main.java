import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventStorage storage = new EventStorage();
        List<Thread> threads = new ArrayList<>();
        for (int i =0; i<10; i++) {
            threads.add(new Thread(new Producer(storage, "Productor " + i)));
            threads.get(i).start();
        }

        Thread threadConsumer1 = new Thread(new Consumer(storage, "Consumidor 1"));
        Thread threadConsumer2 = new Thread(new Consumer(storage, "Consumidor 2"));
        threadConsumer1.start();
        threadConsumer2.start();


    }
}
