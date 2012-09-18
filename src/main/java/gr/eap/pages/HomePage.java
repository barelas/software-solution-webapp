/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap.pages;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends BasePage {
	private static final long serialVersionUID = 1L;

	public String code;
	
    public HomePage(final PageParameters parameters) {
    	
    	add(new BookmarkablePageLink<AnalysisPage>("new_analysis",AnalysisPage.class,new PageParameters().set(0,"new")));
    	
		Form<HomePage> form = new Form<HomePage>("form",new CompoundPropertyModel<HomePage>(this));
		form.add(new TextField<String>("code"));
		form.add(new SubmitLink("submit") {
			@Override
			public void onSubmit() {
				setResponsePage(ViewAnalysis.class,new PageParameters().set(0,code));
			}
		});
		add(form);
    }
}
