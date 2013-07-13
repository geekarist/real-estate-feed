package com.github.geekarist.restate;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

/**
 * Example resource class hosted at the URI path "/myresource"
 */
@Path("/myresource")
public class MyResource {

	@GET
	@Produces("text/xml")
	public String getIt() throws IOException, JAXBException, ParseException {
		URL resource = Resources.getResource("news.html");
		String html = Resources.toString(resource, Charsets.UTF_8);
		String rss = toRss(html);
		return rss;
	}

	private String toRss(String html) throws JAXBException, ParseException {
		Document document = Jsoup.parse(html);
		Elements select = document.select(".InfoListSmall");
		RssFeed feed = new RssFeed();
		RssChannel channel = new RssChannel("Cotation Immobilière", "http://cotation-immobiliere.fr", "REVUE DE PRESSE IMMO");
		feed.setChannel(channel);

		for (Element e : select) {
			String link = e.attr("href");
			String pubDate = reformatDate(e);
			String title = e.select("span").text();
			String source = e.select("b").text();
			RssItem item = new RssItem(link, title, source, pubDate);
			channel.add(item);
		}
		return serialize(feed);
	}

	private String reformatDate(Element e) throws ParseException {
		String origDate = e.text().split(" - ")[0] + "/" + Calendar.getInstance().get(Calendar.YEAR);
		System.out.println(origDate);
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(origDate);
		String formattedDate = new SimpleDateFormat("EEE, dd MMM yyyy").format(date);
		System.out.println(formattedDate);
		return formattedDate;
	}

	private String serialize(RssFeed feed) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(RssFeed.class);
		Marshaller marshaller = context.createMarshaller();
		StringWriter writer = new StringWriter();
		marshaller.marshal(feed, writer);
		return writer.toString();
	}
}
