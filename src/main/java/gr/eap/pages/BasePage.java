/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap.pages;

import gr.eap.DataStore;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class BasePage extends WebPage {
	
	public BasePage() {
		add(new FeedbackPanel("main_feedback").setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true));
	}
	
	FeedbackPanel getFeedbackPanel() {
		return (FeedbackPanel) get("main_feedback");
	}
	
	DataStore getDataStore() {
		DataStore dataStore = null;
		try {
			dataStore = DataStore.get();
		} catch (Exception e) {
			e.printStackTrace();
			error(e.getMessage());
		}
		return dataStore;
	}
}
