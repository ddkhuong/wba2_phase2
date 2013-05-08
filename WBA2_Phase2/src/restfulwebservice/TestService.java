package restfulwebservice;

import generated.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.ws.rs.*;


@Path( "/testomat" )
public class TestService {
	
	   @GET 
	   @Produces( "application/xml" )
	   public Nachrichten getNews() throws JAXBException, FileNotFoundException
	   {
		   ObjectFactory ob = new ObjectFactory();
		   Nachrichten news = ob.createNachrichten();
		   JAXBContext context = JAXBContext.newInstance(Nachrichten.class);
		   Unmarshaller um = context.createUnmarshaller();
		   news = (Nachrichten) um.unmarshal(new FileReader("C:/Users/Duy/git/wba2_phase2/WBA2_Phase2/src/Phase2XMLfeed.xml"));
		   //news = (Nachrichten) um.unmarshal(new FileReader("C:/Users/Duy/git/WBA2_SS13/Phase1/src/Aufgabe2.xml"));

		   return news;
	   }
	   
	   @GET @Produces( "text/plain" )
	   public String halloText( @QueryParam("name") String name )
	   {
	      return "Hallo " + name;
	   }

	   @GET @Produces( "text/html" )
	   public String halloHtml( @QueryParam("name") String name )
	   {
	      return "<html><title>HelloWorld</title><body><h2>Html: Hallo " + name + "</h2></body></html>";
	   }
	 
	}