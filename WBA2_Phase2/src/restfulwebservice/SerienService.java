package restfulwebservice;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import resources.Serie.AlleStaffeln;
import resources.Serie.Cast;
import resources.Serie.Episode;
import resources.Serie.Serie;
import resources.Serie.Serien;
import resources.Serie.Staffel;

/**
 * @author Duy
 * 
 */
@Path("/serie")
public class SerienService {

	/**
	 * Unmarshalt die Liste aller Serien.
	 * 
	 * @return Serien-Listen-Objekt
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public Serien unmarshalSerien() throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(Serien.class);
		Unmarshaller um = context.createUnmarshaller();
		Serien serien = (Serien) um.unmarshal(new FileInputStream("src/SerienXML.xml"));
		return serien;
	}

	/**
	 * Marshalt die veränderte XML-Datei "SerienXML.xml".
	 * 
	 * @param s Ein Objekt der Klasse Serien
	 * @throws JAXBException
	 */
	public void marshalSerien(Serien s) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Serien.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		try {
			m.marshal(s, new FileOutputStream("src/SerienXML.xml"));
		} catch (FileNotFoundException e) {
			System.out.println("Serie konnte nicht gespeichert werden");
		}
	}

	/**
	 * Nimmt die ID des letzten Objekts "Serie" in der Liste und erhöht diese um eins.
	 * 
	 * @return gibt die um eins erhöhte ID zurück
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public String nextIdSerie() throws JAXBException, FileNotFoundException {
		List<Serie> list = unmarshalSerien().getSerie();
		int id = Integer.parseInt(list.get(list.size() - 1).getId());

		if (list.size() > 0) {
			id++;
		} else {
			id = 0;
		}
		return String.valueOf(id);
	}

	/**
	 * Gibt eine Liste aller Serien-Objekte zurück
	 * 
	 * @return Liste aller Serien
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Serien getSerien() throws JAXBException, FileNotFoundException {
		Serien serien = unmarshalSerien();

		return serien;

	}

	/**
	 * Ausgabe der Serie mit dem übergebenen Namen
	 * 
	 * @param serienname Name der auszugebenen Serie
	 * @return angeforderte Serie
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Path("/{serienname}")
	@Produces(MediaType.APPLICATION_XML)
	public Serie getSerie(@PathParam("serienname") String serienname)
			throws JAXBException, FileNotFoundException {
		
		Serien s_daten = unmarshalSerien();
		Serie s = null;
		
		for (int i = 0; i < s_daten.getSerie().size(); i++) {
			if (serienname.equals(s_daten.getSerie().get(i).getName())) {
				s = s_daten.getSerie().get(i);
			}
		}
		return s;
	}

	/**
	 * Ausgabe der ID der ausgewählten Serie
	 * 
	 * @param serienname Name der auszuwählenden Serie
	 * @return ID der ausgewählten Serie
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Path("/{serienname}/id")
	@Produces(MediaType.TEXT_PLAIN)
	public String getSerienID(@PathParam("serienname") String serienname)
			throws JAXBException, FileNotFoundException {

		Serie s = getSerie(serienname);

		String s_id = s.getId();

		return s_id;
	}

	/**
	 * Löscht die Serie mit übergebenen ID
	 * 
	 * @param serienname Name der Serie, die gelöscht werden soll
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@DELETE
	@Path("/{serienname}")
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteSerie(@PathParam("serienname") String serienname)
			throws JAXBException, FileNotFoundException {

		Serien s_daten = unmarshalSerien();

		for (int i = 0; i < s_daten.getSerie().size(); i++) {
			
			if (serienname.equals(s_daten.getSerie().get(i).getName())) {
				
				s_daten.getSerie().remove(i);
				marshalSerien(s_daten);
				System.out.println("Serie wurde gelöscht");
				return Response.ok().build();
			}
		}
		System.out.println("News wurde nicht gefunden. ");
		return Response.status(404).build();

	}

	/**
	 * Erstellt eine neue Serie und fügt diese zur vorhandenen Liste hinzu,
	 * die ID ist dabei die des letzten Objekts in der Liste um eins erhöht.
	 * 
	 * Das Objekt s muss in der XML-Struktur der Serie übergeben werden.
	 * 
	 * @param s Serien-Objekt in XML-Struktur
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response postSerie(Serie s) throws JAXBException,
			FileNotFoundException {

		Serien s_daten = unmarshalSerien();

		s.setId(nextIdSerie());

		s_daten.getSerie().add(s);
		marshalSerien(s_daten);

		return Response.status(201).build();

	}

	/**
	 * Verändert eine bereits vorhandene Serie oder fügt sie hinzu, wenn 
	 * sie noch nicht vorhanden ist.
	 * 
	 * @param serien_id ID der zu verändernden Serie
	 * @param s Serien-Objekt in XML-Struktur
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@PUT
	@Path("/{serien_id}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response putSerie(@PathParam("serien_id") String serien_id, Serie s)
			throws JAXBException, FileNotFoundException {

		Serien s_daten = unmarshalSerien();
		int id_temp = Integer.parseInt(serien_id);
	
		//Wenn Serien bereits vorhanden ist, dann verändern
		for (int i = 0; i < s_daten.getSerie().size(); i++) {
			if (serien_id.equals(s_daten.getSerie().get(i).getId())) {
				
				String id = s_daten.getSerie().get(i).getId();
				s.setId(id);
				s_daten.getSerie().set((Integer.parseInt(id)-1), s);//Vermeidung von IndexOutOfBoundsException
				
				break;

			}
			//Wenn Serie nicht vorhanden ist, dann hinzufügen
			if (id_temp > s_daten.getSerie().size()) {
				s.setId(nextIdSerie());
				s_daten.getSerie().add(s);
				break;
			}

		}

		marshalSerien(s_daten);

		return Response.ok().build();
	}

	/**
	 * Gibt eine Liste aller Staffel-Objekte zurück.
	 * 
	 * @param serie ID der Serie, der die Staffel zugeordnet ist
	 * @return Liste von Staffel-Objekten
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Path("/{serienname}/staffel")
	@Produces(MediaType.APPLICATION_XML)
	public AlleStaffeln getStaffeln(@PathParam("serienname") String serienname)
			throws JAXBException, FileNotFoundException {

		Serie s = getSerie(serienname);
		AlleStaffeln st = s.getAlleStaffeln();

		return st;

	}

	/**
	 * Ausgabe der mit der ID versehenen Staffel.
	 * 
	 * @param serienname Name der Serie, der die Staffel zugeordnet ist
	 * @param staffel_id ID der Staffel die anfordert werden soll
	 * @return angeforderte Staffel
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Path("/{serienname}/staffel/{staffel_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Staffel getStaffel(@PathParam("serienname") String serienname,
			@PathParam("staffel_id") String staffel_id) throws JAXBException,
			FileNotFoundException {

		AlleStaffeln st_list = getStaffeln(serienname);
		Staffel s = null;

		for (int i = 0; i < st_list.getStaffel().size(); i++) {
			if (st_list.getStaffel().get(i).getId().equals(staffel_id)) {
				s = st_list.getStaffel().get(i);
			}
		}
		return s;

	}

	/**
	 * Erstellt eine neue Staffel und fügt diese zur vorhandenen Liste hinzu,
	 * Die ID ist dabei die des letzten Objekts in der Liste um eins erhöht.
	 * 
	 * Das Objekt st muss in der XML-Struktur der Staffel übergeben werden.
	 * 
	 * 
	 * @param serienname Name der Serie, der die Staffel zugeordnet ist
	 * @param st Staffel-Objekt in XML-Form
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@POST
	@Path("/{serienname}/staffel/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response postStaffel(@PathParam("serienname") String serienname, Staffel st)
			throws JAXBException, FileNotFoundException {

		Serien s_daten = unmarshalSerien();
		AlleStaffeln st_list = getStaffeln(serienname);
		int size = st_list.getStaffel().size();
		int s_id = Integer.parseInt(getSerienID(serienname));

		int lastid = Integer.parseInt(st_list.getStaffel().get(size - 1).getId());

		if (size > 0) {
			lastid++;
		} else {
			lastid = 0;
		}

		st.setId(String.valueOf(lastid));
		s_daten.getSerie().get(s_id - 1).getAlleStaffeln().getStaffel().add(st);//(s_id-1) zur Vermeidung von IndexOutOfBoundsException
		
		marshalSerien(s_daten);

		return Response.status(201).build();
	}

	/**
	 * Löscht die Staffel mit der übergebenen ID
	 * 
	 * @param serienname Serie, der die Staffel zugeordnet ist
	 * @param staffel_id ID der zu löschenden Staffel
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@DELETE
	@Path("/{serienname}/staffel/{staffel_id}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteStaffel(@PathParam("serienname") String serienname,
			@PathParam("staffel_id") String staffel_id) throws JAXBException,
			FileNotFoundException {

		Serien s_daten = unmarshalSerien();
		AlleStaffeln st_list = getStaffeln(serienname);
		int s_id = Integer.parseInt(getSerienID(serienname));

		for (int i = 0; i < st_list.getStaffel().size(); i++) {
			
			if (staffel_id.equals(st_list.getStaffel().get(i).getId())) {
				
				s_daten.getSerie().get(s_id - 1).getAlleStaffeln().getStaffel().remove(i);
				marshalSerien(s_daten);
				System.out.println("Staffel wurde gelöscht");
				return Response.ok().build();
			}
		}
		System.out.println("Staffel wurde nicht gefunden. ");
		return Response.status(404).build();
	}

	/**
	 * Verändert eine bereits vorhandene Staffel oder fügt diese hinzu wenn 
	 * sie noch nicht vorhanden ist.
	 * 
	 * @param serienname Name der Serie, der die Staffel zugeordnet ist
	 * @param staffel_id ID der zu verändernden Staffel
	 * @param st Staffel-Objekt in XML-Struktur
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@PUT
	@Path("/{serienname}/staffel/{staffel_id}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response putStaffel(@PathParam("serienname") String serienname,
			@PathParam("staffel_id") String staffel_id, Staffel st)
			throws JAXBException, FileNotFoundException {

		Serien s_daten = unmarshalSerien();
		AlleStaffeln s_list = getStaffeln(serienname);
		int id_temp = Integer.parseInt(getSerienID(serienname));
		String s_id = getSerienID(serienname);

		//Wenn Staffel vorhanden, dann verändern
		for (int i = 0; i < s_list.getStaffel().size(); i++) {
			if (s_id.equals(s_list.getStaffel().get(i).getId())) {

				String id = s_list.getStaffel().get(i).getId();
				st.setId(id);
				s_daten.getSerie().get(i).getAlleStaffeln().getStaffel().set((Integer.parseInt(id) - 1), st); // Vermeidung von IndexOutOfBoundsException
				break;

			}
			//Wenn Staffel nicht vorhanden, dann hinzufügen
			if (id_temp > s_list.getStaffel().size()) {
				st.setId(nextIdSerie());
				s_daten.getSerie().get(i).getAlleStaffeln().getStaffel()
						.add(st);
				break;
			}
		}

		marshalSerien(s_daten);

		return Response.ok().build();
	}

	/**
	 * Ausgabe der mit der ID "episoden_id" versehenen Episode.
	 * 
	 * @param serienname Name der Serie, der die Staffel zugeordnet ist
	 * @param staffel_id ID der Staffel, der die Episode zugeordnet ist
	 * @param episoden_id ID der auszugebenen Episode
	 * @return angeforderte Episode
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Path("/{serienname}/staffel/{staffel_id}/episode/{episoden_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Episode getEpisode(@PathParam("serienname") String serienname,
			@PathParam("staffel_id") String staffel_id,
			@PathParam("episoden_id") String episoden_id) throws JAXBException,
			FileNotFoundException {
		
		int e_id = (Integer.parseInt(episoden_id)-1);
		
		Staffel st = getStaffel(serienname, staffel_id);
		Episode e = st.getEpisode().get(e_id);
	
		return e;

	}

	/**
	 * Erstelle eine neue Episode und fügt diese zur vorhandenen Liste hinzu.
	 * Die ID ist dabei die des letzten Objekts in der Liste um eins erhöht.
	 * 
	 * Das Objekt e muss in der XML-Struktur der News übergeben werden.
	 * 
	 * 
	 * @param serienname Serie, der die Staffel zugeordnet ist
	 * @param staffel_id ID der Staffel, der die Episode zugeordnet werden soll
	 * @param e Episoden-Objekt in XML-Struktur
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@POST
	@Path("/{serienname}/staffel/{staffel_id}/episode/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response postEpisode(@PathParam("serienname") String serienname,
			@PathParam("staffel_id") String staffel_id, Episode e)
			throws JAXBException, FileNotFoundException {

		Serien s_daten = unmarshalSerien();
		int s_id = Integer.parseInt(getSerienID(serienname));
		int st_id = Integer.parseInt(staffel_id);

		List<Episode> st = s_daten.getSerie().get((s_id - 1)).getAlleStaffeln().getStaffel().get((st_id - 1)).getEpisode();

		int size = st.size();
		int lastid = Integer.parseInt(st.get(size - 1).getId());

		if (size > 0) {
			lastid++; //ID des neu hinzugefügten Objekts
		} else {
			lastid = 0;
		}

		e.setId(String.valueOf(lastid));

		s_daten.getSerie().get((s_id - 1)).getAlleStaffeln().getStaffel().get((st_id - 1)).getEpisode().add(e);
		marshalSerien(s_daten);

		return Response.status(201).build();

	}

	/**
	 * Löscht die Episode mit der übergebenen ID
	 * 
	 * 
	 * @param serienname Serie, der die Staffel zugeordnet ist
	 * @param staffel_id ID der Staffel, der die Episode zugeordnet ist
	 * @param episoden_id ID der Episode, die gelöscht werden soll
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@DELETE
	@Path("/{serienname}/staffel/{staffel_id}/episode/{episoden_id}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteEpisode(@PathParam("serienname") String serienname,
			@PathParam("staffel_id") String staffel_id,
			@PathParam("episoden_id") String episoden_id) throws JAXBException,
			FileNotFoundException {

		Serien s_daten = unmarshalSerien();
		int s_id = Integer.parseInt(getSerienID(serienname));
		int st_id = Integer.parseInt(staffel_id);

		List<Episode> st_list = s_daten.getSerie().get((s_id - 1))
				.getAlleStaffeln().getStaffel().get((st_id - 1)).getEpisode();

		for (int i = 0; i < st_list.size(); i++) {
			if (episoden_id.equals(st_list.get(i).getId())) {
				s_daten.getSerie().get((s_id - 1)).getAlleStaffeln().getStaffel().get((st_id - 1)).getEpisode().remove(i);
				marshalSerien(s_daten);
				System.out.println("Episode wurde gelöscht");
				return Response.ok().build();
			}

		}
		System.out.println("Episode wurde nicht gefunden. ");
		return Response.status(404).build();
	}

	/**
	 * Gibt eine Liste aller Darsteller einer Serie zurück
	 * 
	 * @param serienname Serie, der der Cast zugeordnet wird
	 * @return Cast-Objekt der Serie
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Path("/{serienname}/cast")
	@Produces(MediaType.APPLICATION_XML)
	public Cast getCast(@PathParam("serienname") String serienname) throws JAXBException,
			FileNotFoundException {
		
		Serie s = getSerie(serienname);
		Cast c = s.getCast();

		return c;
	}

	/**
	 * Verändert den bereits vorhandenen Cast.
	 * 
	 * @param serienname Serie, der der Cast zugeordnet ist
	 * @param c Cast-Objekt in XML-Struktur
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@PUT
	@Path("/{serienname}/cast")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response putCast(@PathParam("serienname") String serienname, Cast c)
			throws JAXBException, FileNotFoundException {

		Serien s_daten = unmarshalSerien();
		int s_id = Integer.parseInt(getSerienID(serienname));

		s_daten.getSerie().get((s_id - 1)).setCast(c);

		marshalSerien(s_daten);

		return Response.ok().build();

	}

}
