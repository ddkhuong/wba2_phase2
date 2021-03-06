package restfulwebservice;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyServerFactory;

public class RestServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		String url = "http://localhost.com:4869";

		SelectorThread srv = GrizzlyServerFactory.create(url);

		System.out.println("Server wurde gestartet" + "\n" + "URL: " + url);
		Thread.sleep(1000 * 60 * 10);
		srv.stopEndpoint();
		System.out.println("Server wurde beendet");
	}

}
