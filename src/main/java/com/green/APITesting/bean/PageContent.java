package com.green.APITesting.bean;

/**
 * @author Dieson Zuo
 * @date Nov 16, 2016 10:45:15 AM
 */
public class PageContent {
	/**
	 * 应用name
	 */
	String name = null;
	/**
	 * 应用介绍
	 */
	String description = null;
	/**
	 * appid
	 */
	String appid =null;
	
	public String toString() {
		
		StringBuilder sbl = new StringBuilder();
		
		if (this.name != null) {
			sbl.append("name：\t");
			sbl.append(this.name);
			sbl.append("\n");
		}
		if (this.description != null) {
			sbl.append("description：\t");
			sbl.append(this.description);
			sbl.append("\n");
		}
		if (this.appid != null) {
			sbl.append("appid：\t");
			sbl.append(this.appid);
			sbl.append("\n");
		}
		sbl.append("\n");
		return sbl.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

}
