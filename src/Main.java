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
        //TODO Better implementation
        List<Thread> threadList = new ArrayList<>();
        threadList.add(new Thread(new Consumer(storage, "Consumidor 1")));
        threadList.add(new Thread(new Consumer(storage, "Consumidor 2")));
        threadList.get(0).start();
        threadList.get(1).start();


        boolean finished = false;
        while(!finished){
            try {
                finished = true;
                for(Thread t : threadList){
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
