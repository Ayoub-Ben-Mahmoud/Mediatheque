package classes;
public class Documents {
	private String ISBN;
	private String title;
	private String author;
	private boolean availability;
	public Documents(String id,String t,String auth,boolean avail) {
		this.ISBN=id;
		this.title=t;
		this.author=auth;
		this.availability=avail;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public boolean isAvailability() {
		return availability;
	}
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
}
