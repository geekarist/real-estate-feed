package com.github.geekarist.restate;

import java.io.IOException;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
	public String getIt() throws IOException {	
		URL resource = Resources.getResource("news.html");
		String html = Resources.toString(resource, Charsets.UTF_8);
		String rss = toRss(html);
		return rss;
	}

	private String toRss(String html) {
		Document document = Jsoup.parse(html);
		Elements select = document.select(".InfoListSmall");
		RssChannel channel = new RssChannel("Cotation Immobilière", "http://cotation-immobiliere.fr", "REVUE DE PRESSE IMMO");

		for (Element e : select) {
			String link = e.attr("href");
			String title = e.select("span").text();
			String source = e.select("b").text();
			RssItem item = new RssItem(link, title, source);
			channel.add(item);
		}
		return serialize(channel);
	}

	private String serialize(RssChannel channel) {
		return channel.toString();
	}
}
