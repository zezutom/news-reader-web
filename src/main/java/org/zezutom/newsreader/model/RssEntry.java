package org.zezutom.newsreader.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;


public class RssEntry {
	
	public static final String TO_STRING_FORMAT = "[CATEGORY]:%s[TITLE]:%s[URL]:%s[AUTHOR]:%s[PUBLISHED]:%s";
	
	public static final String DATE_FORMAT = "EEEE MMMM dd, yyyy HH:mm:ss";
	
	private String category;
	
    private String title;

    private String link;
    
    private String author;
    
    private String publishedDate;
    
    private String description;

    
    
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
	@Override
	public String toString() {
		return String.format(TO_STRING_FORMAT, category, title, link, author, publishedDate);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result
				+ ((publishedDate == null) ? 0 : publishedDate.hashCode());
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
		RssEntry other = (RssEntry) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (publishedDate == null) {
			if (other.publishedDate != null)
				return false;
		} else if (!publishedDate.equals(other.publishedDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public static RssEntry createInstance(String category, SyndEntry syndEntry) {
		// Custom format
		RssEntry rssEntry = new RssEntry();

		rssEntry.setCategory(category);
		rssEntry.setLink(syndEntry.getLink());
		rssEntry.setTitle(syndEntry.getTitle());
		rssEntry.setAuthor(syndEntry.getAuthor());
		
		SyndContent description = syndEntry.getDescription();
		
		if (description != null) {
			rssEntry.setDescription(description.getValue());
		}

		Date published = syndEntry.getPublishedDate();
		
		if (published != null) {
			DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);		
			rssEntry.setPublishedDate(dateFormat.format(published));			
		}
		
		return rssEntry;		
	}
}
