import java.util.Random;

public class Producer implements Runnable {
	
	private EventStorage storage;
	private String name;
	
	public Producer(EventStorage storage, String name) {
		this.storage = storage;
		this.name = name;
	}
	
	@Override
	public void run() {
		for (int i=0; i<100; i++) {
			System.out.println("Llamada set " + i + " - " + name);
			storage.set(name);
			sleepRandomly(0,2);
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
