package resources;

/**
 * Enum class fetch resource path based on aliases passed
 * @author biraj
 *
 */
public enum APIResources {
	getBadgesAPI("/2.2/badges"),
	getRecipentAPI("/2.2/badges/recipients"),
	getRecipentsById("/2.2/badges/"),
	getBadgesById("/2.2/badges/");
	private String resource;
	
	APIResources(String resource) {
		// TODO Auto-generated constructor stub
		this.resource = resource;
	}
	
	public String getResource() {
		return resource;
	}
	

}
