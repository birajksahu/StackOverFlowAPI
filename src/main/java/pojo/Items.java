package pojo;
/**
 * Creates pojo class containing Badge's Items data
 * @author biraj
 *
 */
public class Items {
	private String badge_type;
	private Integer award_count; 
	private String rank;
	private String badge_id;
	private String link;
	private String name;
	
	public String getBadge_type() {
		return badge_type;
	}
	public void setBadge_type(String badge_type) {
		this.badge_type = badge_type;
	}
	public Integer getAward_count() {
		return award_count;
	}
	public void setAward_count(Integer award_count) {
		this.award_count = award_count;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getBadge_id() {
		return badge_id;
	}
	public void setBadge_id(String badge_id) {
		this.badge_id = badge_id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	

}
