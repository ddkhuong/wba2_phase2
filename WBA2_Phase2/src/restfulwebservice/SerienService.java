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
import resources.Serie.Darsteller;
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
	 * Marshalt die ver�nderte XML-Datei "SerienXML.xml".
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
	 * Nimmt die ID des letzten Objekts "Serie" in der Liste und erh�ht diese um eins.
	 * 
	 * @return gibt die um eins erh�hte ID zur�ck
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
	 * Gibt eine Liste aller Serien-Objekte zur�ck
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
	 * Ausgabe der Serie mit dem �bergebenen Namen
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
	 * Ausgabe der ID der ausgew�hlten Serie
	 * 
	 * @param serienname Name der auszuw�hlenden Serie
	 * @return ID der ausgew�hlten Serie
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
	 * L�scht die Serie mit �bergebenen ID
	 * 
	 * @param serienname Name der Serie, die gel�scht werden soll
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
				System.out.println("Serie wurde gel�scht");
				return Response.ok().build();
			}
		}
		System.out.println("Serie wurde nicht gefunden. ");
		return Response.status(404).build();

	}

	/**
	 * Erstellt eine neue Serie und f�gt diese zur vorhandenen Liste hinzu,
	 * die ID ist dabei die des letzten Objekts in der Liste um eins erh�ht.
	 * 
	 * Das Objekt s muss in der XML-Struktur der Serie �bergeben werden.
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

		

		s_daten.getSerie().add(s);
		marshalSerien(s_daten);

		return Response.status(201).build();

	}

	/**
	 * Ver�ndert eine bereits vorhandene Serie oder f�gt sie hinzu, wenn 
	 * sie noch nicht vorhanden ist.
	 * 
	 * @param serienname Name der zu ver�ndernden Serie
	 * @param s Serien-Objekt in XML-Struktur
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@PUT
	@Path("/{serienname}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response putSerie(@PathParam("serienname") String serienname, Serie s)
			throws JAXBException, FileNotFoundException {

		Serien s_daten = unmarshalSerien();
	
		
		for (int i = 0; i < s_daten.getSerie().size(); i++) {
			
			
			if (serienname.equals(s_daten.getSerie().get(i).getId())) {
				
				System.out.println("if 1");
				s.setId(serienname);
				s_daten.getSerie().set(i, s);
				
				break;
				}

		}	

		marshalSerien(s_daten);

		return Response.ok().build();
	}

	/**
	 * Gibt eine Liste aller Staffel-Objekte zur�ck.
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
	 * Erstellt eine neue Staffel und f�gt diese zur vorhandenen Liste hinzu,
	 * Die ID ist dabei die des letzten Objekts in der Liste um eins erh�ht.
	 * 
	 * Das Objekt st muss in der XML-Struktur der Staffel �bergeben werden.
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
		
		

		
		for(int i=0; i<s_daten.getSerie().size(); i++){
			if(serienname.equals(s_daten.getSerie().get(i).getId())){
		s_daten.getSerie().get(i).getAlleStaffeln().getStaffel().add(st);
			}
		}
		marshalSerien(s_daten);

		return Response.status(201).build();
		
		
	}
	
	/**
	 * L�scht die Staffel mit der �bergebenen ID
	 * 
	 * @param serienname Serie, der die Staffel zugeordnet ist
	 * @param staffel_id ID der zu l�schenden Staffel
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
		


		for(int i=0; i<s_daten.getSerie().size(); i++){
			if(serienname.equals(s_daten.getSerie().get(i).getId())){
				for (int j = 0; j < st_list.getStaffel().size(); j++) {			
					if (staffel_id.equals(st_list.getStaffel().get(j).getId())) {
				
				s_daten.getSerie().get(i).getAlleStaffeln().getStaffel().remove(j);
				marshalSerien(s_daten);
				System.out.println("Staffel wurde gel�scht");
				return Response.ok().build();
					}
				}
			}
		}
		System.out.println("Staffel wurde nicht gefunden. ");
		return Response.status(404).build();
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
	@Path("/{serienname}/staffel/{staffel_id}/episode/{episodenname}")
	@Produces(MediaType.APPLICATION_XML)
	public Episode getEpisode(@PathParam("serienname") String serienname,
			@PathParam("staffel_id") String staffel_id,
			@PathParam("episodenname") String episodenname) throws JAXBException,
			FileNotFoundException {
		
				
		Staffel st = getStaffel(serienname, staffel_id);
		Episode e = null;
		
		for(int i=0; i<st.getEpisode().size(); i++){
			if(st.getEpisode().get(i).getId().equals(episodenname)){
				e = st.getEpisode().get(i);
				break;
			}
		}
		
	
		return e;

	}

	/**
	 * Erstelle eine neue Episode und f�gt diese zur vorhandenen Liste hinzu.
	 * Die ID ist dabei die des letzten Objekts in der Liste um eins erh�ht.
	 * 
	 * Das Objekt e muss in der XML-Struktur der News �bergeben werden.
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
		AlleStaffeln s_list = getStaffeln(serienname);
		
		

		

		for(int i=0; i<s_daten.getSerie().size(); i++){
			if(serienname.equals(s_daten.getSerie().get(i).getId())){
				for (int j = 0; j < s_list.getStaffel().size(); j++) {
					String st_id = s_list.getStaffel().get(j).getId();
					if (st_id.equals(s_list.getStaffel().get(j).getId())) {
						
		s_daten.getSerie().get(i).getAlleStaffeln().getStaffel().get((j)).getEpisode().add(e);
	
					}
				}
			}
		}
		marshalSerien(s_daten);
		return Response.status(201).build();

	}

	/**
	 * L�scht die Episode mit der �bergebenen ID
	 * 
	 * 
	 * @param serienname Serie, der die Staffel zugeordnet ist
	 * @param staffel_id ID der Staffel, der die Episode zugeordnet ist
	 * @param episoden_id ID der Episode, die gel�scht werden soll
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
		AlleStaffeln st_list = getStaffeln(serienname);
		
		

		for(int i=0; i<s_daten.getSerie().size(); i++){
			if(serienname.equals(s_daten.getSerie().get(i).getId())){
				for (int j = 0; j < st_list.getStaffel().size(); j++) {
					String st_id = st_list.getStaffel().get(j).getId();
					if (st_id.equals(st_list.getStaffel().get(j).getId())) {
						for(int k=0; k<st_list.getStaffel().get(j).getEpisode().size(); k++){
							if(episoden_id.equals(st_list.getStaffel().get(j).getEpisode().get(k).getId())){
					
						
					
				s_daten.getSerie().get(i).getAlleStaffeln().getStaffel().get(j).getEpisode().remove(k);
				marshalSerien(s_daten);
				System.out.println("Episode wurde gel�scht");
				return Response.ok().build();
								}
							}
						}
				}
			}
		}
		System.out.println("Episode wurde nicht gefunden. ");
		return Response.status(404).build();
	}

	/**
	 * Gibt eine Liste aller Darsteller einer Serie zur�ck
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
	 * Ver�ndert den bereits vorhandenen Cast.
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
		
		for(int i=0; i<s_daten.getSerie().size(); i++){
			if(serienname.equals(s_daten.getSerie().get(i).getId())){
				
					s_daten.getSerie().get(i).setCast(c);
			}
		}
		marshalSerien(s_daten);

		return Response.ok().build();

	}
	
	/**
	 * @param serienname
	 * @param d
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@POST
	@Path("/{serienname}/cast")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response postCastDarsteller(@PathParam("serienname") String serienname, Darsteller d)
			throws JAXBException, FileNotFoundException {
		
		Serien s_daten = unmarshalSerien();
		Cast c = getCast(serienname);
		for(int i=0; i<s_daten.getSerie().size(); i++){
			int d_id = c.getDarsteller().size();
			if(serienname.equals(s_daten.getSerie().get(i).getId())){
				d.setId(String.valueOf(d_id));
				s_daten.getSerie().get(i).getCast().getDarsteller().add(d);
			}
		}
		marshalSerien(s_daten);
		return Response.status(201).build();
	}
	
	/**
	 * L�scht einen Darsteller aus dem Cast
	 * 
	 * @param serienname Serie, dem der Cast angeh�rt
	 * @param darstellername Darsteller, der gel�scht werden soll 
	 * @return tatus-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@DELETE
	@Path("/{serienname}/cast/{darstellername}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteCastDarsteller(@PathParam("serienname") String serienname,
			@PathParam("darstellername") String darstellername) throws JAXBException, FileNotFoundException{
		
		Serien s_daten = unmarshalSerien();
		Cast c = getCast(serienname);
		


		for(int i=0; i<s_daten.getSerie().size(); i++){
			if(serienname.equals(s_daten.getSerie().get(i).getId())){
				for (int j = 0; j < c.getDarsteller().size(); j++) {			
					if (darstellername.equals(c.getDarsteller().get(j).getId())) {
				
				s_daten.getSerie().get(i).getCast().getDarsteller().remove(j);
				marshalSerien(s_daten);
				System.out.println("Darsteller wurde gel�scht");
				return Response.ok().build();
					}
				}
			}
		}
		System.out.println("Darsteller wurde nicht gefunden. ");
		return Response.status(404).build();
	}
		
	
}
