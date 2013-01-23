package pl.atena.technoblog.parallel;

public class WorkUnit {
	private String name;
	public WorkUnit(String name) {
		this.name = name;
	}

	public void run() {
		System.out.println(this.name + ": start");
		try {
			// complicated business logic:
			Long t1 = System.nanoTime();
			Thread.sleep(4000);
			Long t2 = System.nanoTime();
			
			System.out.println(String.format("%s finished in %d ms", this.name, (t2 - t1) / 1000000));
		} catch (InterruptedException e) {
			System.out.println(this.name + ": error");		
			e.printStackTrace();
		}
	}
}
