import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventStorage storage = new EventStorage();
        List<Thread> threads = new ArrayList<>();
        for (int i =0; i<10; i++) {
            threads.add(new Thread(new Producer(storage, "Productor " + i)));
            threads.get(i).start();
        }
        List<Thread> consumers = new ArrayList<>();
        for (int i=0; i<2; i++) {
            consumers.add(new Thread(new Consumer(storage, "Consumidor "+i)));
            consumers.get(i).start();
        }

        // Log every 2 seconds
        Formatter file = null;
        try{
            file = new Formatter("./out/log.txt");

            boolean finished = false;
            int counter = 0;
            while(!finished && counter < 60){
                try {
                    finished = true;

                    file.format( "%ds Cantidad de productos en el buffer: %d\n", counter, storage.getSize());
                    System.out.printf( "%ds Cantidad de productos en el buffer: %d", counter, storage.getSize());
                    for(Thread t : consumers){
                        String logFormat = "%ds El estado del hilo %s es: %s\n";
                        file.format( logFormat, counter, t.getName(), t.getState());
                        System.out.printf( logFormat, counter, t.getName(), t.getState());

                        finished = finished && t.getState().equals(Thread.State.TERMINATED);
                    }

                    Thread.sleep(2000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    counter += 2;
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        finally {
            file.format("Fin de la ejecucion de Main");
            file.close();
        }

        System.out.println("Fin de la ejecucion de Main");
    }
}
