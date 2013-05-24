package restfulwebservice;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.*;

public class TestClient
{
   public static void main( String[] args )
   {
      String url = ( args.length > 0 ) ? args[0] : "http://localhost:4434/schlagzeuge";
      System.out.println( "URL: " + url );

      WebResource wrs = Client.create().resource( url );

      System.out.println("\nTextausgabe:");
      System.out.println( wrs.accept(MediaType.APPLICATION_XML).get( String.class ) );
      System.out.println( wrs.accept(MediaType.TEXT_PLAIN).get( String.class ) );
   }
}