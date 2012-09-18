/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap.pages;

import gr.eap.EapSession;
import gr.eap.model.Analysis;

import org.apache.wicket.markup.html.basic.Label;

public class SaveAnalysis extends BasePage {
	
	public SaveAnalysis() {
		Analysis analysis = ((EapSession)getSession()).getAnalysis();
		if (analysis==null) {
			error("Πρόβλημα: Δε βρέθηκε ενεργή ανάλυση.");
			return;
		}
		try {
			getDataStore().saveAnalysis(analysis);
		} catch (Exception e) {
			getSession().error("Δεν ήταν δυνατή η αποθήκευση");
			getSession().error(e.getMessage());
			e.printStackTrace();
		}
		add(new Label("code",analysis.getCode()));
	}
}
