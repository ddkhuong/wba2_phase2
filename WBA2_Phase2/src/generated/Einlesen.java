package generated;
import generated.Rezept.Kommentare.Kommentar;
import generated.Rezept.Zutaten.Zutat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


public class Einlesen {
	public static void main(String[] args) throws Exception {
		JAXBContext context = JAXBContext.newInstance(Rezept.class);
		
		// Java objekte aus XML erstellen
		Unmarshaller um=context.createUnmarshaller();
		Rezept r=(Rezept) um.unmarshal(new FileInputStream("src/Aufgabe3Rezept.xml"));
		int alt=0;
		Scanner in= new Scanner(System.in);
		
		System.out.println("Zum ausgeben 1 drücken, neuer Kommentar mit 2");
		int a=in.nextInt();
		String nameAutor="";
		String eingegebenerKommentar="";
		if (a==1)  // Ausgabe des gesamten Datensatzes
				{
			for (Zutat z:r.getZutaten().getZutat()){
				if(z.getEinheit()==null)
				System.out.println(z.getMenge()+" "+z.getName());
				else{
					System.out.println(z.getMenge()+" "+z.getEinheit()+" "+z.getName());
				}
			}
			
			System.out.println("für "+r.getPortionen().getAnzahl()+" Portionen");
			System.out.println("Arbeitszeit"+r.getZubereitung().getArbeitszeit());
			System.out.println("Schwierigkeitsgrad "+r.getZubereitung().getSchwierigkeitsgrad());
			System.out.println("Brennwert: "+r.getZubereitung().getBrennwert());
			System.out.println(r.getZubereitung().getBeschreibung());

			for(Kommentar k:r.getKommentare().getKommentar()){
				System.out.println(k.getAutor());
				System.out.println(k.getId());
				System.out.println(k.getDatum());
				//System.out.println(k.getZeit()); XML Calendar format enthlält zeit und datum
				System.out.println(k.getValue());
					}
			}
		else if (a==2){                 // Neuer Kommentar
			System.out.println("geben Sie ihren Namen ein:");
			nameAutor=in.next()+in.nextLine();
			System.out.println("geben Sie ihren den Kommentar ein:");
			eingegebenerKommentar=in.next()+in.nextLine();
		}
		else {
			System.out.println("Fehler!");   //fehlerausgabe bei falscher eingabe
			return;
		}
		Date now = new Date();
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(now);                                        // aktuelle Zeit und Datum als XML Calendar
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c); 
		
		for(Kommentar k:r.getKommentare().getKommentar() ) //größte Id herausfinden
		{  
		String stringZahl = k.getId();
	    int intZahl = Integer.parseInt(stringZahl);
		if(intZahl>alt)
			alt=intZahl;
	  }
		Kommentar k = new Kommentar();
		//Kommentar erstellen
		k.setAutor(nameAutor);
		k.setDatum(date2); 
		String altString = String.valueOf(alt+1);//größte id +1
		k.setId(altString); // fortlaufende ID
		//k.setId((new Integer((int)(Math.random()*42*42))).toString()); // Random ID
		k.setZeit(date2); 
		k.setValue(eingegebenerKommentar);
		
		r.getKommentare().getKommentar().add(k);
		
		
		Marshaller ma=context.createMarshaller();
		ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); //Erstellter Kommentar Formatiert eintragen
		ma.marshal(r, new FileOutputStream("src/Aufgabe3Rezept.xml")); 
		
	}
	

}
