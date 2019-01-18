package pojo;

/**
 * CookieData类
 * <p>cookie对象的三个属性：
 * <p>1、domainName: cookie对应的域名，即cookie是请求哪个地 
 * <p>2、cookieKey：cookie的key；
 *<p> 3、cookieValue：cookie的value
 * 
 */
public class CookieData {
	
	private String domainName; 
	private String cookieKey;
	private String cookieValue;
	
	public String getDomaiName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getCookieKey() {
		return cookieKey;
	}
	public void setCookieKey(String cookieKey) {
		this.cookieKey = cookieKey;
	}
	public String getCookieValue() {
		return cookieValue;
	}
	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}
	@Override
	public String toString() {
		return "CookieData [domaiName=" + domainName + ", cookieKey=" + cookieKey + ", cookieValue=" + cookieValue + "]";
	}
	
	public CookieData(String domainName, String cookieKey, String cookieValue) {
		super();
		this.domainName = domainName;
		this.cookieKey = cookieKey;
		this.cookieValue = cookieValue;
	}
	public CookieData() {
		super();
	}

}
