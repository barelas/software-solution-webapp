/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Analysis implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String description;
	private String keywords;
	private String code = null;
	private boolean keyusers = false;
	private boolean sensitiveData = false;
	private boolean itDepartment = false;
	private int usageEstimation = 5;
	
	private Date created = new Date();
	private Date modified = created;
	private List<Lysh> lyseis = new LinkedList<Lysh>();
	
	public Analysis() {
		lyseis.add(new Lysh());
	}
	
	public void prepareLyseisForCalculations() {
		BigDecimal max = new BigDecimal(0);
		for (Lysh l:lyseis) {
			l.setUsageEstimation(usageEstimation);
			max = max.max(l.getTotalCost());
		}
		System.out.println("Max found: " + max);
		for (Lysh l:lyseis) {
			l.setMaximuxCostFromAllLyseis(max);
		}
	}
	
	public void touch() {
		modified = new Date();
	}
	
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public boolean isKeyusers() {
		return keyusers;
	}

	public void setKeyusers(boolean keyusers) {
		this.keyusers = keyusers;
	}

	public boolean isSensitiveData() {
		return sensitiveData;
	}

	public void setSensitiveData(boolean sensitiveData) {
		this.sensitiveData = sensitiveData;
	}

	public boolean isItDepartment() {
		return itDepartment;
	}

	public void setItDepartment(boolean itDepartment) {
		this.itDepartment = itDepartment;
	}

	public int getUsageEstimation() {
		return usageEstimation;
	}

	public void setUsageEstimation(int usageEstimation) {
		this.usageEstimation = usageEstimation;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreated() {
		return created;
	}
	public Date getModified() {
		return modified;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Lysh> getLyseis() {
		return lyseis;
	}

	public void setLyseis(List<Lysh> lyseis) {
		this.lyseis = lyseis;
	}
}
