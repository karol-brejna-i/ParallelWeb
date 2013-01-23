package pl.atena.technoblog.parallel.utils;

import java.io.*;
import java.net.URL;

public class UrlWorker implements Runnable {
	private String url;

	public UrlWorker(String url) {
		this.url = url;
	}

	@Override
	public void run() {
		URL urlObj;
		try {
			urlObj = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlObj.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null)
				System.out.println(inputLine);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
