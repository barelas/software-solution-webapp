/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap;

import gr.eap.model.Analysis;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class EapSession extends WebSession {
	
	private Analysis analysis = null;

	public EapSession(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	public Analysis getAnalysis() {
		return analysis;
	}

	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
	}

}
