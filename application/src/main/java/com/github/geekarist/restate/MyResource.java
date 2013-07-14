package com.github.geekarist.restate;

import java.io.IOException;
import java.io.StringWriter;
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

/**
 * Example resource class hosted at the URI path "/myresource"
 */
@Path("/myresource")
public class MyResource {
	
	private String newsUrl;
	
	public MyResource() {
		this.newsUrl = "http://cotation-immobiliere.fr";
	}
	
	public MyResource(String newsUrl) {
		this.newsUrl = newsUrl;
	}

	@GET
	@Produces("text/xml")
	public String getIt() throws IOException, JAXBException, ParseException {
		Document document = Jsoup.connect(newsUrl)
				.userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
				.referrer("http://www.google.fr")
				.get();
		String html = document.html();
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
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(origDate);
		String formattedDate = new SimpleDateFormat("EEE, dd MMM yyyy").format(date);
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
