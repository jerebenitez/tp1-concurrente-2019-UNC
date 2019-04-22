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
        List<Thread> consumers = new ArrayList<>();
        for (int i=0; i<2; i++) {
            consumers.add(new Thread(new Consumer(storage, "Consumidor "+i)));
            consumers.get(i).start();
        }

        boolean finished = false;
        while(!finished){
            try {
                finished = true;
                for(Thread t : consumers){
                    //TODO Log in a txt the Thread's states
                    System.out.printf("El estado del hilo %s es: %s\n", t.getName(), t.getState());
                    finished = finished && t.getState().equals(Thread.State.TERMINATED);
                }
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fin de la ejecucion de Main");
    }
}
