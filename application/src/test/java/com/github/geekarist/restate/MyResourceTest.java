package com.github.geekarist.restate;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.ParseException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.input.ReaderInputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MyResourceTest {

	public MyResourceTest() {
	}

	@org.junit.BeforeClass
	public static void setUpClass() throws Exception {
	}

	@org.junit.AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void shouldProduceValidRssFeed() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, JAXBException, ParseException {
		// GIVEN
		MyResource instance = new MyResource();

		// WHEN
		String rss = instance.getIt();

		// THEN
		assertXpathMatches(rss, "//rss/channel/item[2]/link", 
				"http://www.lesechos.fr/patrimoine/immobilier/actu/0202857869421-embellie-surprise-du-pouvoir-d-achat-immobilier-des-parisiens-580285.php"
				+ "?xtor=EPR-1500-[patrimoine]-20130627-[s=461370_n=8_c=801_]-1291412[_SDV]@1");
		assertXpathMatches(rss, "/rss/channel/item[2]/pubDate", "Thu, 27 Jun 2013");
		assertXpathMatches(rss, "/rss/channel/item[2]/title", 
				"Embellie surprise du pouvoir d’achat immobilier des Parisiens");
		assertXpathMatches(rss, "/rss/channel/item[2]/source", "lesechos");

		assertXpathMatches(rss, "/rss/channel/item[3]/link", 
				"http://www.boursorama.com/actualites/immobilier-duflot-fait-enrager-les-pros-aff34549b570fd76a8bad88c06e94bc8");
		assertXpathMatches(rss, "/rss/channel/item[3]/pubDate", "Wed, 26 Jun 2013");
		assertXpathMatches(rss, "/rss/channel/item[3]/title", "Duflot fait enrager les pros");
		assertXpathMatches(rss, "/rss/channel/item[3]/source", "boursorama");
	}

	private void assertXpathMatches(String xml, String xpath, String value) throws ParserConfigurationException, SAXException,
			IOException, XPathExpressionException {
		assertThat(textByXpath(xml, xpath)).isEqualTo(value);
	}

	private String textByXpath(String rss, String xpath) throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		InputStream input = new ReaderInputStream(new StringReader(rss));
		Document doc = builder.parse(input);
		XPath xpathObject = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpathObject.compile(xpath);
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		StringBuffer text = new StringBuffer();
		NodeList nodes = (NodeList) result;
		for (int i = 0; i < nodes.getLength(); i++) {
			Node item = nodes.item(i);
			text.append(item.getTextContent());
		}
		return text.toString();
	}
}
