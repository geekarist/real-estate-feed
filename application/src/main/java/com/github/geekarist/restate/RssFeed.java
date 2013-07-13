package com.github.geekarist.restate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rss")
public class RssFeed {
	
	@XmlElement
	private RssChannel channel;

	public void setChannel(RssChannel channel) {
		this.channel = channel;
	}

	@Override
	public String toString() {
		return "RssFeed [channel=" + channel + "]";
	}
}
