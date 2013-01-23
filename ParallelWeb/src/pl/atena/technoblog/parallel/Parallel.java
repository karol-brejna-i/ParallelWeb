package pl.atena.technoblog.parallel;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/parallel")
public class Parallel extends HttpServlet {
	private static final long serialVersionUID = -152801880710512087L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// get the number of workers to run
		int count = this.getRequestedCount(request, 4);
		// get the executor service
		final ServletContext sc = this.getServletContext();
		final ExecutorService executorService = (ExecutorService) sc
				.getAttribute("myExecutorService");
		
		Long t1 = System.nanoTime();
		// create the workers
		List<RunnableWorkUnit> workers = new ArrayList<RunnableWorkUnit>();
		for (int i = 0; i < count; i++) {
			RunnableWorkUnit wu = new RunnableWorkUnit("RunnableTask"
					+ String.valueOf(i + 1));
			workers.add(wu);
		}
		// run the workers through the executor
		for (RunnableWorkUnit wu : workers) {
			executorService.execute(wu);
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
