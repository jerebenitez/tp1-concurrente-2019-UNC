import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventStorage storage = new EventStorage(10);

        List<Thread> threads = new ArrayList<>();
        int producers = 10;
        for (int i = 0; i < producers; i++) {
            threads.add(new Producer(storage, "Productor " + i, 1000/producers));
            threads.get(i).start();
        }

        List<Thread> consumers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            consumers.add(new Consumer(storage, "Consumer " + i));
            consumers.get(i).start();
        }

        // Log every 2 seconds
        try {
            FileWriter file = new FileWriter("log.txt");
            PrintWriter pw = new PrintWriter(file);

            boolean finish = false;
            while (!finish  && storage.getLoad() != 0) {
                finish = true;
                for (Thread t: threads) {
                    finish = finish && (t.getState() == Thread.State.TERMINATED);
                }

                pw.printf("Storage load: %d\n", storage.getLoad());
                for (Thread c: consumers) {
                    pw.printf(" Name: %s\tState: %s\n", c.getName(), c.getState());
                }
                pw.printf("\n");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            file.close();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // We interrupt both consumers
            for (Thread c: consumers) {
                c.interrupt();
            }
            System.out.println("Fin de la ejecucion de Main");
        }
    }
}

