package com.github.geekarist.restate;

import java.util.ArrayList;
import java.util.List;

public class RssChannel {

	private String title;
	private String link;
	private String description;
	private List<RssItem> items = new ArrayList<RssItem>();

	public RssChannel(String title, String link, String description) {
		this.title = title;
		this.link = link;
		this.description = description;
	}

	public void add(RssItem item) {
		items.add(item);
	}

	@Override
	public String toString() {
		return "RssChannel [title=" + title + ", link=" + link + ", description=" + description + ", items=" + items + "]";
	}
	
}
