/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap.util;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

public class Anapty3hChoiceRenderer implements IChoiceRenderer<Integer> {

	public Object getDisplayValue(Integer object) {
		switch (object) {
		case 1:
			return "Ομάδα υπάλληλων – εργαζομένων του ίδιου του τμήματος";
		case 2:
			return "Τμήμα ΤΠΕ του οργανισμού";
		case 3:
		case 4:
			return "Εξωτερικός παράγοντας (συνεργάτης ή εταιρεία πληροφορικής)";
		default:
			return "-";
		}
	}

	public String getIdValue(Integer object, int index) {
		return String.valueOf(object);
	}


}
