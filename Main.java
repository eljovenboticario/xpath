import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
 
public class Main {
 
public static void main(String[] args) throws Exception {
 
//Carga el documento de XML desde un archivo
DocumentBuilderFactory builderfactory = DocumentBuilderFactory.newInstance();
builderfactory.setNamespaceAware(true);
 
DocumentBuilder builder = builderfactory.newDocumentBuilder();
Document xmlDocument = builder.parse(
new File(Main.class.getResource("books.xml").getFile().replace("%20", " ")));
 
XPathFactory factory = javax.xml.xpath.XPathFactory.newInstance();
XPath xPath = factory.newXPath();

//Nombre del autor del libro de horror:
System.out.println("\n1) Nombre del autor del libro de horror: ");
String genreID = "Horror";
XPathExpression xPathExpression = xPath.compile("//catalog//book[genre='" +genreID + "']//author");
String autor = xPathExpression.evaluate(xmlDocument,XPathConstants.STRING).toString();
System.out.println("Autor: " + autor);

//El precio total de comprar todos los libros de fantasía:
System.out.println("\n2) El precio total de comprar todos los libros de fantasía: ");
String genreID2 = "Fantasy";
xPathExpression = xPath.compile("//catalog//book[genre='" +genreID2 + "']//price");

NodeList nodeListPrice =  (NodeList) xPathExpression.evaluate(xmlDocument,XPathConstants.NODESET);
System.out.println("Número total de libros de fantasía es: " + nodeListPrice.getLength());

float total=0, precio=0;
for (int index = 0; index < nodeListPrice.getLength(); index++) //ciclo para calcular los precios de los libros de fantasía
{
	String precioLibro = nodeListPrice.item(index).getTextContent();
	precio=Float.parseFloat(precioLibro); //convierte de string a float
	total=precio+total;
}
System.out.println("Precio total: $" +total );

//Lista de todos los libros de computación que tengan que ver con Microsoft
System.out.println("\n3) Lista de todos los libros de Microsoft: ");
String genreID3 = "Computer";
xPathExpression = xPath.compile("//catalog//book[genre='" +genreID3 + "']");

NodeList nodeListComputer =  (NodeList) xPathExpression.evaluate(xmlDocument,XPathConstants.NODESET);
System.out.println("Número total de libros de computación es: " + nodeListComputer.getLength());

String palabra = "Microsoft", texto;
int totalMicrosoft=0;
boolean resultado = false;

for (int temp = 0; temp < nodeListComputer.getLength(); temp++)
{
	Node node = nodeListComputer.item(temp);
	Element eElement = (Element) node;

	texto=eElement.getElementsByTagName("description").item(0).getTextContent();
	resultado=texto.contains(palabra);
	if(resultado)
	{
		String libroMicrosoft = nodeListComputer.item(temp).getTextContent();
		System.out.println("Libro no."+temp+ libroMicrosoft);
		totalMicrosoft = temp;
	}
}
System.out.println("Total de libros de Microsoft: " +totalMicrosoft );
}
}
