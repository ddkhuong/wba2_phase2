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

@Path("/profile")
public class ProfileService {
	
	
	public Profile unmarshalProfil() throws JAXBException, FileNotFoundException{
		JAXBContext context = JAXBContext.newInstance(Profile.class);
		   Unmarshaller um = context.createUnmarshaller();
		   Profile profil = (Profile) um.unmarshal(new FileInputStream("src/ProfilXML.xml"));
		   return profil;
	}
	
	public void marshalProfil(Profile p) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Profile.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		try{
		m.marshal(p, new FileOutputStream("src/ProfilXML.xml"));
		} catch (FileNotFoundException e) {
			System.out.println("Nachricht konnte nicht gespeichert werden");
		}
	}

	public String nextIdProfil() throws JAXBException, FileNotFoundException{
		List<Profil> list = unmarshalProfil().getProfil();
		int id = Integer.parseInt(list.get(list.size()-1).getId());
					
		if (list.size() > 0 ){
			id++;
		}
		else{
			id = 0;
		}
		return String.valueOf(id);
	}
	
	
	
	
	@GET
	   @Produces( MediaType.APPLICATION_XML )
	   public Profile getProfile() throws JAXBException, FileNotFoundException{
		
		Profile profil_daten = unmarshalProfil();
		   
		return profil_daten;
		   
	   }
	
	@GET
	@Path("/{profil_id}")
	@Produces( MediaType.APPLICATION_XML )
	public Profil getProfil(@PathParam("profil_id") String profil_id) throws JAXBException, FileNotFoundException{
			Profile profil_daten = unmarshalProfil();
			Profil p = null;
			for(int i=0; i<profil_daten.getProfil().size(); i++){
				if(profil_id.equals(profil_daten.getProfil().get(i).getId())){
					p = profil_daten.getProfil().get(i);
				}	
			}
			return p;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response postProfil(Profil p) throws JAXBException, FileNotFoundException{
		Profile profil_daten = unmarshalProfil();
		 
		p.setId(nextIdProfil());
		
		profil_daten.getProfil().add(p);
		marshalProfil(profil_daten);
		
		return Response.status(201).build();
		
		 
		
	}
	 @PUT
	   @Path("/{profil_id}")
	   @Consumes(MediaType.APPLICATION_XML)
	   @Produces(MediaType.APPLICATION_XML)
	   public Response putProfil(@PathParam("profil_id") String profil_id,
			   					Profil p) throws JAXBException, FileNotFoundException{
		 	Profile profil_daten = unmarshalProfil();
		 	int id_temp = Integer.parseInt(profil_id);
		 	for(int i=0; i<profil_daten.getProfil().size(); i++ ){
			    if(profil_daten.getProfil().get(i).getId().equals(profil_id)){
					   String id = profil_daten.getProfil().get(i).getId();
					   p.setId(id);
					   profil_daten.getProfil().set((Integer.parseInt(id)-1), p);
				   }
					   if(id_temp > profil_daten.getProfil().size()){ 
						   p.setId(nextIdProfil());
						   profil_daten.getProfil().add(p);
						   break;
					   }
		 	}
				   		   	
				   
		   
		   
		   marshalProfil(profil_daten);
		   
		   return Response.ok().build();
	 }
	 
	 @DELETE
	 @Path("/{profil_id}")
	 @Produces(MediaType.APPLICATION_XML)
	 public Response deleteProfil(@PathParam("profil_id") String profil_id,
				Profil p) throws JAXBException, FileNotFoundException{
		 Profile profil_daten = unmarshalProfil();
	   		for(int i=0; i<profil_daten.getProfil().size(); i++){
	   			if(profil_id.equals(profil_daten.getProfil().get(i).getId())){
	   				profil_daten.getProfil().remove(i);
	   				marshalProfil(profil_daten);
	   				System.out.println("Profil wurde gelöscht");
	   				return Response.ok().build();
	   			}
	   		}
	   		System.out.println("Profil wurde nicht gefunden. ");
	   		return Response.status(404).build();
	 }
	
	 
	 @GET
	 @Path("/{profil_id}/filter")
	 @Produces(MediaType.APPLICATION_XML)
	 public Filtercontainer getAllFilter(@PathParam("profil_id") String profil_id) throws JAXBException, FileNotFoundException{
		 Profil p = getProfil(profil_id);
		 Filtercontainer f = p.getFiltercontainer();
		 
		 return f;	 
	 }
	 
	 @GET
	 @Path("/{profil_id}/filter/{filter_id}")
	 @Produces(MediaType.APPLICATION_XML)
	 public Filter getFilter(@PathParam("profil_id") String profil_id,
			 				@PathParam("filter_id") String filter_id) throws JAXBException, FileNotFoundException{
		 Filtercontainer f_list = getAllFilter(profil_id);
		 Filter f = null;
		   
		   for(int i=0; i<f_list.getFilter().size(); i++){
			   if(f_list.getFilter().get(i).getId().equals(filter_id)){
				   f=f_list.getFilter().get(i);
			   }
		   }
		   
		   
		   return f;
	 }
	 @POST
	 @Path("/{profil_id}/filter/")
	 @Produces(MediaType.APPLICATION_XML)
	 public Response postFilter(@PathParam("profil_id") String profil_id,
			 					Filter f) throws JAXBException, FileNotFoundException{
		 Profile p_daten = unmarshalProfil();
		 Filtercontainer f_list = getAllFilter(profil_id);	   
		 int size = f_list.getFilter().size();
		 
		 int lastid = Integer.parseInt(f_list.getFilter().get(size-1).getId());
		   
		   if(size > 0){
			   lastid++;
	   		}
	   		else{
	   		lastid=0;
	   		}
		   
		   f.setId(String.valueOf(lastid));
		   p_daten.getProfil().get(Integer.parseInt(profil_id)-1).getFiltercontainer().getFilter().add(f);
		   marshalProfil(p_daten);
		   
		   return Response.status(201).build();
	 }
	 
	 @DELETE
	 @Path("/{profil_id}/filter/{filter_id}")
	 @Consumes(MediaType.APPLICATION_XML)
	 @Produces(MediaType.APPLICATION_XML)
	 public Response deleteFilter(@PathParam("profil_id") String profil_id,
			   						@PathParam("filter_id") String filter_id) throws JAXBException, FileNotFoundException{
		   
		   	Profile profil_daten = unmarshalProfil(); 
		   	Filtercontainer f_list = getAllFilter(profil_id);
	   		for(int i=0; i<f_list.getFilter().size(); i++){
	   			if(filter_id.equals(f_list.getFilter().get(i).getId())){
	   				profil_daten.getProfil().get(Integer.parseInt(profil_id)-1).getFiltercontainer().getFilter().remove(i);
	   				marshalProfil(profil_daten);
	   				System.out.println("Filter wurde gelöscht");
	   				return Response.ok().build();
	   			}
	   		}
	   		System.out.println("Filter wurde nicht gefunden. ");
	   		return Response.status(404).build();
}
	 
	 @GET
	 @Path("/{profil_id}/abos")
	 @Produces(MediaType.APPLICATION_XML)
	 public Serien getAbonnierteSerien(@PathParam("profil_id") String profil_id) throws JAXBException, FileNotFoundException{
		 Profil p = getProfil(profil_id);
		 Serien a = p.getAbos().getSerien();
		 
		 return a;	 
	 }
	 
	 @POST
	 @Path("/{profil_id}/abos")
	 @Produces(MediaType.APPLICATION_XML)
	 public Response postAbonnierteSerie(@PathParam("profil_id") String profil_id,
			 				  @QueryParam("serie") String serie) throws JAXBException, FileNotFoundException{
		 Profile p_daten = unmarshalProfil();
		 
		 p_daten.getProfil().get(Integer.parseInt(profil_id)-1).getAbos().getSerien().getSerie().add(serie);		 
		 marshalProfil(p_daten);
		   
		 return Response.status(201).build();
	 }
	 
	 
	 @DELETE
	 @Path("/{profil_id}/abos/")
	 @Consumes(MediaType.APPLICATION_XML)
	 @Produces(MediaType.APPLICATION_XML)
	 public Response deleteAbonnierteSerie(@PathParam("profil_id") String profil_id,
			   						@QueryParam("serie") String serie) throws JAXBException, FileNotFoundException{
		   
		   	Profile profil_daten = unmarshalProfil(); 
		   	Serien s_list = getAbonnierteSerien(profil_id);
		   	int serien_id = (Integer.parseInt(serie)-1);
		   	int p_id = (Integer.parseInt(profil_id)-1);
		   	
		   	
		   	if(s_list != null && serien_id <= s_list.getSerie().size() ){
		   	profil_daten.getProfil().get(p_id).getAbos().getSerien().getSerie().remove(serien_id);
		   			   	
	   		
		   	marshalProfil(profil_daten);
		   	System.out.println("Abonnierte Serie wurde gelöscht");
		   	return Response.ok().build();
		   	}
		   	
		   	else{
	   		System.out.println("Serie wurde nicht gefunden. ");
	   		return Response.status(404).build();
		   	}
}
	
}


