package pojo;

import java.util.List;

/**
 * Creates pojo class containing Badges data
 * @author biraj
 *
 */
public class GetBadges {
	private List<Items> items;
	private String has_more;
	private Integer quota_max;
	private Integer quota_remaining;
	
	public List<Items> getItems() {
		return items;
	}
	public void setItems(List<Items> items) {
		this.items = items;
	}
	public String getHas_more() {
		return has_more;
	}
	public void setHas_more(String has_more) {
		this.has_more = has_more;
	}
	public Integer getQuota_max() {
		return quota_max;
	}
	public void setQuota_max(Integer quota_max) {
		this.quota_max = quota_max;
	}
	public Integer getQuota_remaining() {
		return quota_remaining;
	}
	public void setQuota_remaining(Integer quota_remaining) {
		this.quota_remaining = quota_remaining;
	}

	

}
