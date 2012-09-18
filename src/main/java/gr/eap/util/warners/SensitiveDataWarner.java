/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap.util.warners;

import gr.eap.model.Analysis;
import gr.eap.model.Lysh;

public class SensitiveDataWarner implements Warner {

	public String warn(Analysis analysis) {
		if (analysis.isSensitiveData() && ! hasAtLeastASolutionWithOnsiteOnly(analysis)) {
			return "Έχετε σημειώσει πως το σύστημα θα διαχειρίζεται ευαίσθητα δεδομένα, όμως δεν υπάρχει υποψήφια λύση που να αναθέτει τη διαχείριση και τη φιλοξενία στον ίδιο τον οργανισμό.";
		} else {
			return null;
		}
	}

	private boolean hasAtLeastASolutionWithOnsiteOnly(Analysis a) {
		for (Lysh l:a.getLyseis()) {
			if (solutionHasOnsite(l)) return true;
		}
		return false;
	}
	
	private boolean solutionHasOnsite(Lysh l) {
		return l.getDiaxeirishBy()<=2 && l.getFilo3eniaBy()<=2;
	}
}
