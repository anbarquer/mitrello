package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tarjeta {
	private long id;
	private String name;
	private long list;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getList() {
		return list;
	}

	public void setList(long list) {
		this.list = list;
	}

}
