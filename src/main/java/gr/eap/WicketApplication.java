/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap;

import gr.eap.pages.AnalysisPage;
import gr.eap.pages.ClearDataStore;
import gr.eap.pages.HomePage;
import gr.eap.pages.SaveAnalysis;
import gr.eap.pages.ShowDataStore;
import gr.eap.pages.ViewAnalysis;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.settings.IMarkupSettings;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see gr.eap.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		// add your configuration here
		
		IMarkupSettings iMarkupSettings = getMarkupSettings();
		iMarkupSettings.setStripWicketTags(true);
		
		mountPage("analysis",AnalysisPage.class);
		mountPage("save",SaveAnalysis.class);
		mountPage("view",ViewAnalysis.class);
		mountPage("cleardatastore",ClearDataStore.class);
		mountPage("showdatastore",ShowDataStore.class);
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new EapSession(request);
	}
}
