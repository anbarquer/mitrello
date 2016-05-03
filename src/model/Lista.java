package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Lista {
	private long id;
	private String name;
	private long board;

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

	public long getBoard() {
		return board;
	}

	public void setBoard(long board) {
		this.board = board;
	}

}
