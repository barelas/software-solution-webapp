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

public class ItDeptExistsWarner implements Warner {

	public String warn(Analysis analysis) {
		if (analysis.isItDepartment() && !itDeptSolutionExists(analysis)) {
			return "Έχετε σημειώσει πως υπάρχει τμήμα ΤΠΕ στον οργανισμό, αλλά σε καμία από τις υποψήφιες λύσεις, το τμήμα ΤΠΕ δε στηρίζει κάποια από τις φάσεις του έργου.";
		} else {
			return null;
		}
	}
	
	private boolean itDeptSolutionExists(Analysis a) {
		for (Lysh l:a.getLyseis()) {
			if (solutionHasItDept(l)) return true;
		}
		return false;
	}

	private boolean solutionHasItDept(Lysh l) {
		return (l.getAnapty3hBy()<=2) || (l.getDiaxeirishBy()<=2) || (l.getSynthrhshBy()<=2) || (l.getFilo3eniaBy()==2);
	}
}
