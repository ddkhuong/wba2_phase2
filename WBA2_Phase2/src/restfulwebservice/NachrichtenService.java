package restfulwebservice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import resources.Nachrichten.Kommentar;
import resources.Nachrichten.Kommentare;
import resources.Nachrichten.Nachrichten;
import resources.Nachrichten.News;

import java.lang.String;
import java.util.List;

/**
 * @author Duy
 *
 */
@Path("/news")
public class NachrichtenService {

	/**
	 * Unmarshalt den Feed, der eine Liste aller News enthält.
	 * 
	 * @return Feed-Objekt
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public Nachrichten unmarshalFeed() throws JAXBException,
			FileNotFoundException {

		JAXBContext context = JAXBContext.newInstance(Nachrichten.class);
		Unmarshaller um = context.createUnmarshaller();
		Nachrichten feed = (Nachrichten) um.unmarshal(new FileInputStream("src/FeedXML.xml"));
		return feed;
	}

	/**
	 * Marshalt die veränderte XML-Datei "FeedXML.xml"
	 * 
	 * @param n Ein Objekt der Klasse Nachrichten
	 * @throws JAXBException
	 */
	public void marshalFeed(Nachrichten n) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(Nachrichten.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		try {
			m.marshal(n, new FileOutputStream("src/FeedXML.xml"));
		} catch (FileNotFoundException e) {
			System.out.println("Nachricht konnte nicht gespeichert werden");
		}
	}

	/**
	 * Nimmt die ID des letzten Objekts "News" in der Liste und erhöht diese um
	 * eins.
	 * 
	 * @return gibt die um eins erhöhte ID zurück
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public String nextIdNews() throws JAXBException, FileNotFoundException {

		List<News> list = unmarshalFeed().getFeed().getNews();
		
		// list.size()-1 zur Vermeidung eine IndexOutOfBoundsException
		int id = Integer.parseInt(list.get(list.size()-1).getId());

		if (list.size() > 0) {
			id++;
		} else {
			id = 0;
		}
		return String.valueOf(id);
	}

	/**
	 * Gibt eine Liste aller Nachrichten-Objekte zurück.
	 * 
	 * @return Liste aller Nachrichten
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Nachrichten getNachrichten() throws JAXBException,
			FileNotFoundException {
		
		Nachrichten news = unmarshalFeed();

		return news;

	}

	/**
	 * Ausgabe der mit der ID versehenen News.
	 * 
	 * @param news_id ID der auszugebenden News
	 * @return angeforderte News
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Path("/{news_id}")
	@Produces(MediaType.APPLICATION_XML)
	public News getNews(@PathParam("news_id") String news_id)
			throws JAXBException, FileNotFoundException {

		Nachrichten news_daten = unmarshalFeed();
		News n = null;
		
		//Durchlaufen der Liste und Vergleich der ID bis gesuchtes Objekt gefunden
		for (int i = 0; i < news_daten.getFeed().getNews().size(); i++) {
			if (news_id.equals(news_daten.getFeed().getNews().get(i).getId())) {
				n = news_daten.getFeed().getNews().get(i);
			}
		}
		return n;
	}

	/**
	 * Löscht die News mit der übergebenen ID "news_id".
	 * 
	 * 
	 * @param news_id ID der zu löschenden News
	 * @return Status-Code ok wenn gelöscht, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@DELETE
	@Path("{news_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteNews(@PathParam("news_id") String news_id)
			throws JAXBException, FileNotFoundException {

		Nachrichten news_daten = unmarshalFeed();
		
		//Durchsuchen der Liste nach der News mit der ID "news_id"
		for (int i = 0; i < news_daten.getFeed().getNews().size(); i++) {
			if (news_id.equals(news_daten.getFeed().getNews().get(i).getId())) {
				
				//Entfernen der News
				news_daten.getFeed().getNews().remove(i);
				marshalFeed(news_daten);
				System.out.println("News wurde gelöscht");
				return Response.ok().build();
			}
		}
		System.out.println("News wurde nicht gefunden. ");
		return Response.status(404).build();
	}

	/**
	 * Erstellt eine neue News und fügt diese zur vorhandenen Liste hinzu, die
	 * ID ist dabei die des letzten Objekts in der Liste um eins erhöht.
	 * 
	 * Das Objekt n muss in der XML-Struktur der News übergeben werden. 
	 * 
	 * 
	 * @param n News-Objekt in XML-Struktur
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response postNachricht(News n) throws JAXBException,
			FileNotFoundException {

		Nachrichten news_daten = unmarshalFeed();

		n.setId(nextIdNews()); //Setzen der News-ID 

		news_daten.getFeed().getNews().add(n);
		marshalFeed(news_daten);

		return Response.status(201).build();

	}

	/**
	 * Verändert eine bereits vorhandene News oder fügt sie hinzu, wenn diese 
	 * noch nicht verhanden ist.
	 * 
	 * @param news_id ID der zu verändernden News
	 * @param n News-Objekt in XML-Struktur
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@PUT
	@Path("/{news_id}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response putNachricht(@PathParam("news_id") String news_id, News n)
			throws JAXBException, FileNotFoundException {

		Nachrichten news_daten = unmarshalFeed();
		int id_temp = Integer.parseInt(news_id);

		//Einfügen falls bereits vorhanden
		for (int i = 0; i < news_daten.getFeed().getNews().size(); i++) {
			if (news_daten.getFeed().getNews().get(i).getId().equals(news_id)) {
				String id = news_daten.getFeed().getNews().get(i).getId();
				n.setId(id);
				news_daten.getFeed().getNews().set((Integer.parseInt(id) - 1), n);
			}
			//Erstellen falls noch nicht vorhanden
			if (id_temp > news_daten.getFeed().getNews().size()) {
				n.setId(nextIdNews());
				news_daten.getFeed().getNews().add(n);
				break;
			}

		}

		marshalFeed(news_daten);

		return Response.ok().build();
	}

	/**
	 * Gibt eine Liste der gesamten Kommentar-Objekte, die einer bestimmten
	 * News zugeordnet ist, aus.
	 * 
	 * 
	 * @param news_id ID der News, für die die Kommentare ausgegeben werden sollen
	 * @return Liste der Kommentar-Objekte
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Path("/{news_id}/kommentare")
	@Produces(MediaType.APPLICATION_XML)
	public Kommentare getKommentare(@PathParam("news_id") String news_id)
			throws JAXBException, FileNotFoundException {
		News n = getNews(news_id);
		Kommentare k = n.getKommentare();
		return k;
	}

	/**
	 * Gibt den Kommentar mit der "kommentar_id" aus, die zur News mit der 
	 * "news_id" gehört.
	 * 
	 * 
	 * @param news_id ID der ausgewählten News
	 * @param kommentar_id ID des auszugebenen Kommentar
	 * @return Kommentar-Objekt in XML-Struktur
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Path("{news_id}/kommentare/{kommentar_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Kommentar getKommentar(@PathParam("news_id") String news_id,
			@PathParam("kommentar_id") String kommentar_id)
			throws JAXBException, FileNotFoundException {

		Kommentare k_list = getKommentare(news_id);
		Kommentar k = null;

	
		for (int i = 0; i < k_list.getKommentar().size(); i++) {
			if (k_list.getKommentar().get(i).getId().equals(kommentar_id)) {
				k = k_list.getKommentar().get(i);
			}
		}

		return k;
	}

	/**
	 * Erstellt einen neuen Kommentar und fügt diesen zur vorhandenen Liste
	 * an Kommentaren hinzu.
	 * 
	 * Das Objekt k muss in der XML-Struktur eines Kommentars übergeben werden
	 *  
	 * 
	 * @param news_id ID der ausgewöhlten News
	 * @param k Kommentar-Objekt in XML-Form
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	@POST
	@Path("/{news_id}/kommentare/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response postKommentar(@PathParam("news_id") String news_id,
			Kommentar k) throws FileNotFoundException, JAXBException {

		Nachrichten news_daten = unmarshalFeed();
		Kommentare k_list = getKommentare(news_id);
		int size = k_list.getKommentar().size();
		int lastid = Integer.parseInt(k_list.getKommentar().get(size - 1).getId()); //ID des letzten Listen-Objekts
		int n_id = (Integer.parseInt(news_id) - 1);

		if (size > 0) {
			lastid++; //ID, die dem neuen Objekt übergeben wird
		} else {
			lastid = 0;
		}

		k.setId(String.valueOf(lastid));
		news_daten.getFeed().getNews().get(n_id).getKommentare().getKommentar().add(k);
		marshalFeed(news_daten);

		return Response.status(201).build();
	}

	/**
	 * Löscht den ausgewählten Kommentar einer ausgewählten News.
	 * 
	 * 
	 * @param news_id ID der auszuwählenden News
	 * @param kommentar_id ID des auszuwählenden Kommentars
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@DELETE
	@Path("/{news_id}/kommentare/{kommentar_id}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteKommentar(@PathParam("news_id") String news_id,
			@PathParam("kommentar_id") String kommentar_id)
			throws JAXBException, FileNotFoundException {

		Nachrichten news_daten = unmarshalFeed();
		Kommentare k_list = getKommentare(news_id);
		int n_id = (Integer.parseInt(news_id) - 1);

		for (int i = 0; i < k_list.getKommentar().size(); i++) {
			if (kommentar_id.equals(k_list.getKommentar().get(i).getId())) {
				news_daten.getFeed().getNews().get(n_id).getKommentare().getKommentar().remove(i);
				marshalFeed(news_daten);
				System.out.println("Kommentar wurde gelöscht");
				return Response.ok().build();
			}
		}
		System.out.println("Kommentar wurde nicht gefunden. ");
		return Response.status(404).build();
	}

}