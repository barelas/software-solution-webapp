/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap.util.warners;

import gr.eap.model.Analysis;

public interface Warner {
	
	public String warn(Analysis analysis);

}
