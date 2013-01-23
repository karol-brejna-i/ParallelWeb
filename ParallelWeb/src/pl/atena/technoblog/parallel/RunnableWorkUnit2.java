package pl.atena.technoblog.parallel;

import java.util.concurrent.CountDownLatch;

public class RunnableWorkUnit2 implements Runnable {
	private String name;
	private CountDownLatch countDownLatch;
	public RunnableWorkUnit2(String name, CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
		this.name = name;
	}
	@Override
	public void run() {
		System.out.println(String.format("%d:%s started (%s)", System.currentTimeMillis(), this.name,Thread.currentThread().getName()));
		try {
			// complicated business logic:
			Long t1 = System.nanoTime();
			Thread.sleep(4000);
			Long t2 = System.nanoTime();
			
			System.out.println(String.format("%d:%s finished in %d ms", System.currentTimeMillis(),this.name, (t2 - t1) / 1000000));
			System.out.println("Counting down: " +  this.name);
			countDownLatch.countDown();
		} catch (InterruptedException e) {
			System.out.println(this.name + ": error");
			e.printStackTrace();
		}
	}
}
