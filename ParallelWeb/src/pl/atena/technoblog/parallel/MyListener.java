package pl.atena.technoblog.parallel;  
  
import java.util.concurrent.*;  
  
import javax.servlet.*;  
import javax.servlet.annotation.WebListener;  
  
@WebListener  
public class MyListener implements ServletContextListener {  
    @Override  
    public void contextInitialized(ServletContextEvent sce) {  
        System.out.println("Servlet Context initialized.");  
        final int numberOfThreads = 256;  
        final ExecutorService threadPool = Executors  
                .newFixedThreadPool(numberOfThreads);  
  
        final ServletContext servletContext = sce.getServletContext();  
        servletContext.setAttribute("myExecutorService", threadPool);  
    }  
  
    @Override  
    public void contextDestroyed(ServletContextEvent sce) {  
        System.out.println("Servlet Context destroyed.");  
        final ExecutorService threadPool = (ExecutorService) sce  
                .getServletContext().getAttribute("myExecutorService");  
        // stop accepting new tasks  
        threadPool.shutdown();  
        try {  
            // wait for task to terminate  
            threadPool.awaitTermination(1, TimeUnit.MINUTES);  
        } catch (InterruptedException e) {  
            // enough waiting!  
            threadPool.shutdownNow();  
        }  
    }  
}