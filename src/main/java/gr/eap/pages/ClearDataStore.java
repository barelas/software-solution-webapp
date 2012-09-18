/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap.pages;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ClearDataStore extends BasePage {

    public ClearDataStore(final PageParameters parameters) {
		try {
			getDataStore().clear();
			add(new Label("output","Datastore cleared."));
		} catch (Exception e) {
			add(new Label("output","Problem with clearing datastore."));
			error(e.getMessage());
			e.printStackTrace();
		}
    }
}
