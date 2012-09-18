/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap.pages;

import gr.eap.model.Analysis;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ShowDataStore extends BasePage {

    public ShowDataStore(final PageParameters parameters) {
		List<Analysis> analyses = getDataStore().getAll();
		add(new PropertyListView<Analysis>("list",analyses) {
			@Override
			protected void populateItem(ListItem<Analysis> item) {
				item.add(new BookmarkablePageLink<ViewAnalysis>("viewlink",ViewAnalysis.class,new PageParameters().set(0,item.getModelObject().getCode())).add(new Label("code")));
				item.add(new Label("title"));
				item.add(new Label("created"));
				item.add(new Label("modified"));
				item.add(new Label("obj",item.getModelObject().toString()));
			}
		});
    }
}
