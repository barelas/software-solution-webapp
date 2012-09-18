/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap.util.warners;

import gr.eap.model.Analysis;

public class AskedKeyusersWarner implements Warner {

	public String warn(Analysis analysis) {
		if (analysis.isKeyusers()) {
			return "Έχετε σημειώσει πως υπάρχουν έμπειροι χρήστες (key-users), οι οποίοι θα μπορούσαν να βοηθήσουν στην επιλογή και το σχεδιασμό της λύσης. Έχουν λάβει μέρος στη διαδικασία;";
		} else {
			return null;
		}
	}

}
