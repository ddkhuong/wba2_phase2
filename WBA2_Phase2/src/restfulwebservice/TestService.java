package restfulwebservice;

import generated.*;

import generated.Nachrichten;
import generated.Nachrichten.Feed.News;
import generated.Nachrichten.Feed.News.Kommentare.Kommentar;
import generated.ObjectFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.ws.rs.*;

import profil.*;
import profil.Profile.Profil;
import java.lang.String;
import java.util.Date;
import java.util.GregorianCalendar;

@Path( "/home" )
public class TestService {
	
	   @GET 
	   @Produces( "text/xml" )
	   public Nachrichten getNachricht() throws JAXBException, FileNotFoundException
	   {
		   ObjectFactory ob = new ObjectFactory();
		   Nachrichten news = ob.createNachrichten();
		   JAXBContext context = JAXBContext.newInstance(Nachrichten.class);
		   Unmarshaller um = context.createUnmarshaller();
		   news = (Nachrichten) um.unmarshal(new FileReader("C:/Users/Duy/git/wba2_phase2/WBA2_Phase2/src/Phase2XMLfeed.xml")); //anstatt FileReader InputStream?

		   return news;
	   }
	
	   
	   @PUT
	   @Produces("application/xml")
	   public Nachrichten createNachricht() throws JAXBException, FileNotFoundException {
		   
		  JAXBContext context = JAXBContext.newInstance(Nachrichten.class);
		  
		   //für löschen news s=News(id)
		   News s=new News();
		   
		   s.setId("a10");
		   s.setSerienname("test123");
		   s.getBild().setValue("http://n-cdn.serienjunkies.de/p/48838.jpg");
		   s.getArtikel().setValue("Tolle Serie");
		   s.setTitel("Titel der Serie");
		   s.setBeschreibung("beschreibung");
		   //return s;
		   
		   Nachrichten n = getNachricht();
			n.getFeed().getNews().add(s); //neue serie Hinzufügen

			Marshaller ma=context.createMarshaller();
			ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); //Erstellter Kommentar Formatiert eintragen
			ma.marshal(s, new FileOutputStream("C:/Users/Duy/git/wba2_phase2/WBA2_Phase2/src/Phase2XMLfeed.xml"));  //was muss hier hin?
			return n;
		   
	   }
	   @DELETE
	   @Produces("application/xml")
	   public Nachrichten deleteNews(@QueryParam("deleteid") int deleteid ) throws JAXBException, FileNotFoundException {
		   
		   JAXBContext context = JAXBContext.newInstance(Nachrichten.class);
		   
		   Nachrichten news = getNachricht();
		   News s = getNachricht().getFeed().getNews().get(deleteid);
		   
		   //s = null; News wird komplett gelöscht?
		   s.setId(null);
		   s.setSerienname(null);
		   s.getBild().setValue(null);
		   s.getArtikel().setValue(null);
		   s.setTitel(null);
		   s.setBeschreibung(null);
		   
			news.getFeed().getNews().add(s); //neue serie Hinzufügen

			Marshaller ma=context.createMarshaller();
			ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); //Erstellter Kommentar Formatiert eintragen
			ma.marshal(s, new FileOutputStream("C:/Users/Duy/git/wba2_phase2/WBA2_Phase2/src/Phase2XMLfeed.xml"));  //was muss hier hin?
			return news;
	 
	}
	   @GET
	   @Path("/news/{newsid}/kommentare/{kommentarid}")
	   @Produces ( "application/xml" )
	   public Kommentar kommentarAnzeigen(@PathParam("newsid") int newsid, @PathParam("kommentarid") int kommentarid ) throws JAXBException, FileNotFoundException {
		   
		   
		   Kommentar k = (Kommentar) getNachricht().getFeed().getNews().get(newsid).getKommentare().getKommentar().get(kommentarid);
		   return k;
		   
		   
	   }
	   
	   @PUT
	   @Produces("application/xml")
	   @Path("/news/{newsid}/kommentare")
	   public Kommentar putKommentar(@PathParam("newsid") int newsid, @QueryParam("putid") int putid) throws JAXBException, FileNotFoundException, DatatypeConfigurationException{
		   
		   JAXBContext context = JAXBContext.newInstance(Kommentar.class);
		  
		   Date now = new Date();
		   GregorianCalendar c = new GregorianCalendar();
		   c.setTime(now);                                        // aktuelle Zeit und Datum als XML Calendar
		   XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		   
		   Kommentar k = kommentarAnzeigen(newsid, putid);
		   k.setAutor("Max Mustermann");
		   
		   k.setDatum(date2);
		   k.setId("putid");
		   k.setInhalt("bla bla");
		   //auch georgian calendar
		   //k.setZeit("13:50");
		   
		   
		   
		   News news = new News();
		   news.getKommentare().getKommentar().add(5, k); 
			
			Marshaller ma=context.createMarshaller();
			ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); //Erstellter Kommentar Formatiert eintragen
			ma.marshal(k, new FileOutputStream("C:/Users/Duy/git/wba2_phase2/WBA2_Phase2/src/Phase2XMLfeed.xml"));  //was muss hier hin?
		return k;
		   
		   
	   }
	   @DELETE
	   @Produces("application/xml")
	   @Path("/news/{newsid}/kommentare/{deleteid}")
	   public Kommentar deleteKommentar(@PathParam("newsid") int newsid, @PathParam("deleteid") int deleteid ) throws JAXBException, FileNotFoundException {
		   
	   
		   JAXBContext context = JAXBContext.newInstance(Kommentar.class);
		   
		   
		   
		   Kommentar k = kommentarAnzeigen(newsid, deleteid);
		   k.setAutor(null);
		   k.setDatum(null);
		   k.setId(null);
		   k.setInhalt(null);
		   k.setZeit(null);
			
		   News s = getNachricht().getFeed().getNews().get(newsid);
		   s.getKommentare().getKommentar().add(k);
		   
			Marshaller ma=context.createMarshaller();
			ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); //Erstellter Kommentar Formatiert eintragen
			ma.marshal(s, new FileOutputStream("C:/Users/Duy/git/wba2_phase2/WBA2_Phase2/src/Phase2XMLfeed.xml"));  //was muss hier hin?
			return k;
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