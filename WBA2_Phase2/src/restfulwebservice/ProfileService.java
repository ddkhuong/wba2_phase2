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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import resources.Profil.Filter;
import resources.Profil.Filtercontainer;
import resources.Profil.Profil;
import resources.Profil.Profile;
import resources.Profil.Serien;

/**
 * @author Duy
 *
 */
/**
 * @author Duy
 *
 */
@Path("/profile")
public class ProfileService {

	/**
	 * Unmarshalt eine Liste aller Profil-Objekte 
	 * 
	 * @return Liste der Profil-Objekte
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public Profile unmarshalProfil() throws JAXBException,
			FileNotFoundException {

		JAXBContext context = JAXBContext.newInstance(Profile.class);
		Unmarshaller um = context.createUnmarshaller();
		Profile profil = (Profile) um.unmarshal(new FileInputStream("src/ProfilXML.xml"));
		return profil;
	}

	/**
	 * Marshalt die veränderte XML-Datei "ProfilXML.xml".
	 * 
	 * @param p Ein Objekt der Klasse Profile
	 * @throws JAXBException
	 */
	public void marshalProfil(Profile p) throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(Profile.class);
		Marshaller m = context.createMarshaller();
		
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		try {
			m.marshal(p, new FileOutputStream("src/ProfilXML.xml"));
		} catch (FileNotFoundException e) {
			System.out.println("Nachricht konnte nicht gespeichert werden");
		}
	}

	/**
	 * Nimmt die ID des letzten Objekts "Profil" in der Liste und erhöht diese um
	 * eins.
	 * 
	 * @return gibt die um eins erhöhte ID zurück
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	public String nextIdProfil() throws JAXBException, FileNotFoundException {

		List<Profil> list = unmarshalProfil().getProfil();

		// list.size()-1 zur Vermeidung eine IndexOutOfBoundsException
		int id = Integer.parseInt(list.get(list.size() - 1).getId());

		if (list.size() > 0) {
			id++;
		} else {
			id = 0;
		}
		return String.valueOf(id);
	}

	/**
	 * Gibt eine Liste aller vorhandenen Profile zurück.
	 * 
	 * 
	 * @return eine Liste aller Profil-Objekte
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Profile getProfile() throws JAXBException, FileNotFoundException {

		Profile profil_daten = unmarshalProfil();
		
		return profil_daten;

	}

	/**
	 * Gibt ein bestimmtes Profil mit der ID "profil_id" zurück
	 * 
	 * @param profil_id ID des angeforderten Objekts
	 * @return angefordertes Profil
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Path("/{profil_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Profil getProfil(@PathParam("profil_id") String profil_id)
			throws JAXBException, FileNotFoundException {

		Profile profil_daten = unmarshalProfil();
		Profil p = null;
		for (int i = 0; i < profil_daten.getProfil().size(); i++) {
			
			if (profil_id.equals(profil_daten.getProfil().get(i).getId())) {
				
				p = profil_daten.getProfil().get(i);
			}
		}
		return p;
	}

	/**
	 * Erstellt ein neues Profil und fügt diese zur vorhandenen Liste hinzu, die
	 * ID ist dabei die des letzten Objekts in der Liste um eins erhöht.
	 * 
	 * Das Objekt p muss in der XML-Struktur des Profils übergeben werden.
	 * 
	 * @param p Profil-Objekt in XMl-Struktur
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response postProfil(Profil p) throws JAXBException,
			FileNotFoundException {

		Profile profil_daten = unmarshalProfil();

		p.setId(nextIdProfil()); //Setzen der neuen Profil-ID

		profil_daten.getProfil().add(p);
		marshalProfil(profil_daten);

		return Response.status(201).build();

	}

	/**
	 * Verändert ein bereits vohandenes Profil oder fügt eins hinzu, wenn es
	 * noch nicht vorhanden ist
	 * 
	 * @param profil_id
	 * @param p Profil-Objekt in XML-Struktur
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@PUT
	@Path("/{profil_id}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response putProfil(@PathParam("profil_id") String profil_id, Profil p)
			throws JAXBException, FileNotFoundException {

		Profile profil_daten = unmarshalProfil();
		int id_temp = Integer.parseInt(profil_id);

		for (int i = 0; i < profil_daten.getProfil().size(); i++) {
			
			if (profil_daten.getProfil().get(i).getId().equals(profil_id)) {
				
				String id = profil_daten.getProfil().get(i).getId();
				
				p.setId(id);
				profil_daten.getProfil().set((Integer.parseInt(id) - 1), p);
			}
			if (id_temp > profil_daten.getProfil().size()) {
				p.setId(nextIdProfil());
				profil_daten.getProfil().add(p);
				break;
			}
		}

		marshalProfil(profil_daten);

		return Response.ok().build();
	}

	/**
	 * Löscht das Profil mit der übergebenen ID.
	 * 
	 * @param profil_id ID des zu löschenden Profils
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@DELETE
	@Path("/{profil_id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteProfil(@PathParam("profil_id") String profil_id) throws JAXBException, FileNotFoundException {

		Profile profil_daten = unmarshalProfil();

		for (int i = 0; i < profil_daten.getProfil().size(); i++) {
			
			if (profil_id.equals(profil_daten.getProfil().get(i).getId())) {
				
				profil_daten.getProfil().remove(i);
				marshalProfil(profil_daten);
				System.out.println("Profil wurde gelöscht");
				return Response.ok().build();
			}
		}
		System.out.println("Profil wurde nicht gefunden. ");
		return Response.status(404).build();
	}

	/**
	 * Gibt eine Liste alle Filterobjekte zurück.
	 * 
	 * @param profil_id ID des Profils, dem die Filter zugeordnet sind
	 * @return Liste der Filter-Objekte
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Path("/{profil_id}/filter")
	@Produces(MediaType.APPLICATION_XML)
	public Filtercontainer getAllFilter(@PathParam("profil_id") String profil_id)
			throws JAXBException, FileNotFoundException {

		Profil p = getProfil(profil_id);
		Filtercontainer f = p.getFiltercontainer();

		return f;
	}

	/**
	 * Ausgabe des mit der ID "filter_id" versehenen Filters.
	 * 
	 * @param profil_id ID des Profils, dem die Filter zugeordnet sind
	 * @param filtername Name des auszugebenen Filter-Objekts
	 * @return angeforderter Filter
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Path("/{profil_id}/filter/{filtername}")
	@Produces(MediaType.APPLICATION_XML)
	public Filter getFilter(@PathParam("profil_id") String profil_id,
			@PathParam("filtername") String filtername) throws JAXBException,
			FileNotFoundException {

		Filtercontainer f_list = getAllFilter(profil_id);
		Filter f = null;

		for (int i = 0; i < f_list.getFilter().size(); i++) {
			if (f_list.getFilter().get(i).getId().equals(filtername)) {
				f = f_list.getFilter().get(i);
			}
		}

		return f;
	}

	/**
	 * Erstellt einen neuen Filter und fügt diese zur vorhandenen Liste hinzu, die
	 * ID ist dabei die des letzten Objekts in der Liste um eins erhöht.
	 * 
	 * Das Objekt f muss in der XML-Struktur der Filter übergeben werden.
	 * 
	 * @param profil_id ID des Profils, dem der Filter zugeordnet werden soll
	 * @param f Filter-Objekt in XML-Struktur
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@POST
	@Path("/{profil_id}/filter/")
	@Produces(MediaType.APPLICATION_XML)
	public Response postFilter(@PathParam("profil_id") String profil_id,
			@QueryParam("filtername") String filtername,
			Filter f) throws JAXBException, FileNotFoundException {

		Profile p_daten = unmarshalProfil();
		
		
		//Vermeidung von IndexOutOfBoundsException
		int p_id = (Integer.parseInt(profil_id) - 1);

		
		f.setId(filtername);
		p_daten.getProfil().get(p_id).getFiltercontainer().getFilter().add(f);
		marshalProfil(p_daten);

		return Response.status(201).build();
	}

	/**
	 * Löscht den Filter mit der übergebenen ID "filter_id".
	 * 
	 * @param profil_id ID des Profils, dem der Filter zugeordnet ist
	 * @param filtername Name des zu löschenden Filter-Objekts
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@DELETE
	@Path("/{profil_id}/filter/{filtername}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteFilter(@PathParam("profil_id") String profil_id,
			@PathParam("filtername") String filtername) throws JAXBException,
			FileNotFoundException {

		Profile profil_daten = unmarshalProfil();
		Filtercontainer f_list = getAllFilter(profil_id);
		//Vermeidung von IndexOutOfBoundsException
		int p_id = (Integer.parseInt(profil_id) - 1);
		
		for (int i = 0; i < f_list.getFilter().size(); i++) {
			
			if (filtername.equals(f_list.getFilter().get(i).getId())) {
				
				profil_daten.getProfil().get(p_id).getFiltercontainer().getFilter().remove(i);
				marshalProfil(profil_daten);
				System.out.println("Filter wurde gelöscht");
				return Response.ok().build();
			}
		}
		System.out.println("Filter wurde nicht gefunden. ");
		return Response.status(404).build();
	}

	
	/**
	 * Fügt eine Serie dem Filter hinzu
	 * 
	 * @param profil_id ID des Profils, dem der Filter zugeordnet ist
	 * @param filtername Name des Filters, dem die Serie hinzugefügt werden soll 
	 * @param serienname Serie die hinzugefügt werden soll
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@POST
	@Path("/{profil_id}/filter/{filtername}")
	@Produces(MediaType.APPLICATION_XML)
	public Response postSerieInFilter(
			@PathParam("profil_id") String profil_id,
			@PathParam("filtername") String filtername,
			@QueryParam("serienname") String serienname) throws JAXBException,
			FileNotFoundException {

		Profile p_daten = unmarshalProfil();
		Filtercontainer f_list = getAllFilter(profil_id);
		
		
		//Vermeidung von IndexOutOfBoundsException
		int p_id = (Integer.parseInt(profil_id) - 1);
		
		for(int i = 0; i < f_list.getFilter().size(); i++) {			
			if ( filtername.equals(f_list.getFilter().get(i).getId())) {
				
				p_daten.getProfil().get(p_id).getFiltercontainer().getFilter().get(i).getSerie().add(serienname);
				break;
				}
			
		}
			
		marshalProfil(p_daten);
		
		return Response.status(201).build();
	}
	
	/**
	 * Löscht eine bestimmte Serie aus dem Filter
	 * 
	 * 
	 * @param profil_id ID des Profils, dem der Filter zugeordnet ist
	 * @param filter_id ID des Filters dem die Serie zugeordnet ist
	 * @param serienname Name der Serie, die gelöscht werden soll
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@DELETE
	@Path("/{profil_id}/filter/{filtername}/{serienname}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteSerieInFilter(
			@PathParam("profil_id") String profil_id,
			@PathParam("filtername") String filtername,
			@PathParam("serienname") String serienname) throws JAXBException,
			FileNotFoundException {
		

		int p_id = (Integer.parseInt(profil_id) - 1);

		
		Profile profil_daten = unmarshalProfil();
		Filtercontainer f_list = getAllFilter(profil_id);
		
		
	
		for(int i=0; i<f_list.getFilter().size(); i++){
			
			if (filtername.equals(f_list.getFilter().get(i).getId())){
				
				List<String> s_list = f_list.getFilter().get(i).getSerie();
				
				for(int j=0; j<s_list.size(); j++){
					
					if(serienname.equals(s_list.get(j))){
						
						profil_daten.getProfil().get(p_id).getFiltercontainer().getFilter().get(i).getSerie().remove(j);
					
					
			marshalProfil(profil_daten);
			System.out.println("Serie wurde aus Filter gelöscht");
			return Response.ok().build();
					}
				}
			}
		}

	
			System.out.println("Serie wurde nicht gefunden.");
			return Response.status(404).build();
		
	}
	
	
	/**
	 * Gibt eine Liste aller Abo-Objekte, die abonnierten Serien, zurück.
	 * 
	 * @param profil_id ID des Profils, dem die das Abo zugeordnet ist.
	 * @return Liste aller abonnierten Serien
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@GET
	@Path("/{profil_id}/abos")
	@Produces(MediaType.APPLICATION_XML)
	public Serien getAbonnierteSerien(@PathParam("profil_id") String profil_id)
			throws JAXBException, FileNotFoundException {
		
		Profil p = getProfil(profil_id);
		Serien a = p.getAbos().getSerien();

		return a;
	}

	/**
	 * Erstellt ein neues Serien-Objekt, das der Liste der Serien im Abo hizugefügt wird.
	 * 
	 * 
	 * @param profil_id ID des Profils, dem das Abo zu geordnet ist
	 * @param serie die Serie, die neu zu der Liste Serien im Abonnement hinzugefügt werden soll
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@POST
	@Path("/{profil_id}/abos")
	@Produces(MediaType.APPLICATION_XML)
	public Response postAbonnierteSerie(
			@PathParam("profil_id") String profil_id,
			@QueryParam("serienname") String serienname) throws JAXBException,
			FileNotFoundException {

		Profile p_daten = unmarshalProfil();
		
		//Vermeidung von IndexOutOfBoundsException
		int p_id = (Integer.parseInt(profil_id) - 1);

		p_daten.getProfil().get(p_id).getAbos().getSerien().getSerie().add(serienname);
		marshalProfil(p_daten);

		return Response.status(201).build();
	}

	/**
	 * Löscht die Serie mit dem übergebenen Namen.
	 * 
	 * @param profil_id ID des Profil, dem das Abo zugeordnet ist
	 * @param serie die zu löschende Serie
	 * @return Status-Code ok bei Erfolg, wenn nicht dann Code 404 als Fehler
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	@DELETE
	@Path("/{profil_id}/abos/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteAbonnierteSerie(
			@PathParam("profil_id") String profil_id,
			@QueryParam("serienname") String serienname) throws JAXBException,
			FileNotFoundException {

		Profile profil_daten = unmarshalProfil();
		Serien s_list = getAbonnierteSerien(profil_id);
		
		//Vermeidung von IndexOutOfBoundsException
	
		int p_id = (Integer.parseInt(profil_id) - 1);

		for(int i=0; i<s_list.getSerie().size(); i++){
			if(serienname.equals(s_list.getSerie().get(i))){
			profil_daten.getProfil().get(p_id).getAbos().getSerien().getSerie().remove(i);

			marshalProfil(profil_daten);
			System.out.println("Abonnierte Serie wurde gelöscht");
			return Response.ok().build();
		}	
		
		}	
			System.out.println("Serie wurde nicht gefunden.");
			return Response.status(404).build();
		}
		
			
	
	
}
