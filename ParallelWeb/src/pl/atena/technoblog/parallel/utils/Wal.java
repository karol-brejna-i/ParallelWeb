package pl.atena.technoblog.parallel.utils;

public class Wal {
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 4; i++) {
			UrlWorker uw = new UrlWorker("http://localhost:8080/TestExecutorService/parallel3?count=4&name=req"+(i+1));
			Thread t = new Thread(uw);
			t.start();
		}
	}
}
