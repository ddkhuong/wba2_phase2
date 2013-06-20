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

@Path("/news")
public class NachrichtenService {

	/**
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public Nachrichten unmarshalFeed() throws JAXBException,
			FileNotFoundException {

		JAXBContext context = JAXBContext.newInstance(Nachrichten.class);
		Unmarshaller um = context.createUnmarshaller();
		Nachrichten news = (Nachrichten) um.unmarshal(new FileInputStream(
				"src/FeedXML.xml"));
		return news;
	}

	/**
	 * @param n
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
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public String nextIdNews() throws JAXBException, FileNotFoundException {

		List<News> list = unmarshalFeed().getFeed().getNews();
		int id = Integer.parseInt(list.get(list.size() - 1).getId());

		if (list.size() > 0) {
			id++;
		} else {
			id = 0;
		}
		return String.valueOf(id);
	}

	/**
	 * @return
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
	 * @param news_id
	 * @return
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
		for (int i = 0; i < news_daten.getFeed().getNews().size(); i++) {
			if (news_id.equals(news_daten.getFeed().getNews().get(i).getId())) {
				n = news_daten.getFeed().getNews().get(i);
			}
		}
		return n;
	}

	/**
	 * @param news_id
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@DELETE
	@Path("{news_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteNews(@PathParam("news_id") String news_id)
			throws JAXBException, FileNotFoundException {

		Nachrichten news_daten = unmarshalFeed();
		for (int i = 0; i < news_daten.getFeed().getNews().size(); i++) {
			if (news_id.equals(news_daten.getFeed().getNews().get(i).getId())) {
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
	 * @param n
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response postNachricht(News n) throws JAXBException,
			FileNotFoundException {

		Nachrichten news_daten = unmarshalFeed();

		n.setId(nextIdNews());

		news_daten.getFeed().getNews().add(n);
		marshalFeed(news_daten);

		return Response.status(201).build();

	}

	/**
	 * @param news_id
	 * @param n
	 * @return
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

		for (int i = 0; i < news_daten.getFeed().getNews().size(); i++) {
			if (news_daten.getFeed().getNews().get(i).getId().equals(news_id)) {
				String id = news_daten.getFeed().getNews().get(i).getId();
				n.setId(id);
				news_daten.getFeed().getNews()
						.set((Integer.parseInt(id) - 1), n);
			}
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
	 * @param news_id
	 * @return
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
	 * @param news_id
	 * @param kommentar_id
	 * @return
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
	 * @param news_id
	 * @param k
	 * @return
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
		int lastid = Integer.parseInt(k_list.getKommentar().get(size - 1)
				.getId());

		if (size > 0) {
			lastid++;
		} else {
			lastid = 0;
		}

		k.setId(String.valueOf(lastid));
		news_daten.getFeed().getNews().get(Integer.parseInt(news_id) - 1)
				.getKommentare().getKommentar().add(k);
		marshalFeed(news_daten);

		return Response.status(201).build();
	}

	/**
	 * @param news_id
	 * @param kommentar_id
	 * @param k
	 * @return
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@DELETE
	@Path("/{news_id}/kommentare/{kommentar_id}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteKommentar(@PathParam("news_id") String news_id,
			@PathParam("kommentar_id") String kommentar_id, Kommentar k)
			throws JAXBException, FileNotFoundException {

		Nachrichten news_daten = unmarshalFeed();
		Kommentare k_list = getKommentare(news_id);

		for (int i = 0; i < k_list.getKommentar().size(); i++) {
			if (kommentar_id.equals(k_list.getKommentar().get(i).getId())) {
				news_daten.getFeed().getNews()
						.get(Integer.parseInt(news_id) - 1).getKommentare()
						.getKommentar().remove(i);
				marshalFeed(news_daten);
				System.out.println("Kommentar wurde gelöscht");
				return Response.ok().build();
			}
		}
		System.out.println("Kommentar wurde nicht gefunden. ");
		return Response.status(404).build();
	}

}