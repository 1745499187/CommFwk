package com.chinawiserv.service;

public abstract class AbstractCommFwkService implements CommFwkService {
	
	/** The name */
	private String name;

	public AbstractCommFwkService(String name) {
		if(name == null) {
			this.name = CommFwkService.class.getSimpleName();
		}
		else {
			this.name = name;
		}
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
