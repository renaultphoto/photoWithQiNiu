package renault.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 图片类
 * @author renault
 *
 */
@SuppressWarnings("serial")

public class Image implements Serializable {

	private int id;
	private String name;
	private String url;
	private Date date;
	private User user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Image [id=" + id + ", name=" + name + ", url=" + url
				+ ", date=" + date + ", user=" + user + "]";
	}
	
	
}
