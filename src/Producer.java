import java.util.Random;

public class Producer extends Thread {
	
	private EventStorage storage;
	private int products;
	
	public Producer(EventStorage storage, String name, int products) {
		this.storage = storage;
		this.setName(name);
		this.products = products;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < this.products; i++) {
			storage.set(this.getName());
			sleepRandomly(100,500);
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
