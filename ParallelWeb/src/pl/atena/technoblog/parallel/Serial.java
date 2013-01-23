package pl.atena.technoblog.parallel;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/serial")
public class Serial extends HttpServlet {
	private static final long serialVersionUID = 7357242480855825340L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get the number of workers to run
		int count = this.getRequestedCount(request, 4);
		// create the workers
		ArrayList<WorkUnit> workers = new ArrayList<WorkUnit>();
		for (int i = 0; i < count; i++) {
			WorkUnit wu = new WorkUnit("worker" + (i + 1));
			workers.add(wu);
		}
		
		// run the workers one after another
		Long t1 = System.nanoTime();
		for (WorkUnit wu : workers) {
			wu.run();
		}
		Long t2 = System.nanoTime();
		Writer w = response.getWriter();
		w.write(String.format("\n Request processed in %dms!", (t2 - t1) / 1000000));
		w.flush();
		w.close();
	}
	
	/** 
	 * Method for getting the requested workers' count. 
	 * If no such param in the request, assign defaultValue. 
	 */
	private int getRequestedCount(HttpServletRequest request, int defaultValue) {
		int result = defaultValue;
		String param = request.getParameter("count");
		if (param != null) {
			result = Integer.parseInt(param);
		}
		return result;
	}
}
