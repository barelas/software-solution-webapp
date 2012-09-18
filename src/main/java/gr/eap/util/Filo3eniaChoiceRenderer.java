/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap.util;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

public class Filo3eniaChoiceRenderer implements IChoiceRenderer<Integer> {

	public Object getDisplayValue(Integer object) {
		switch (object) {
		case 1:
			return "Δεν απαιτείται φιλοξενία";
		case 2:
			return "Σε εξυπηρετητές του τμήματος ή του τμήματος ΤΠΕ του οργανισμού";
		case 3:
		case 4:
			return "Σε εξυπηρετητές που βρίσκονται σε κάποια υπηρεσία φιλοξενίας του Διαδικτύου";
		default:
			return "-";
		}
	}

	public String getIdValue(Integer object, int index) {
		return String.valueOf(object);
	}


}
