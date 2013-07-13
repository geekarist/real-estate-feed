package com.github.geekarist.restate;

import javax.xml.bind.annotation.XmlElement;

public class RssItem {

	@XmlElement
	private String link;
	@XmlElement
	private String title;
	@XmlElement
	private String source;
	@XmlElement
	private String pubDate;

	public RssItem(String link, String title, String source, String pubDate) {
		this.link = link;
		this.title = title;
		this.source = source;
		this.pubDate = pubDate;
	}

	@Override
	public String toString() {
		return "RssItem [link=" + link + ", title=" + title + ", source=" + source + ", pubDate=" + pubDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((pubDate == null) ? 0 : pubDate.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RssItem other = (RssItem) obj;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (pubDate == null) {
			if (other.pubDate != null)
				return false;
		} else if (!pubDate.equals(other.pubDate))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
