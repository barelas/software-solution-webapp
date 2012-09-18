/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap.pages;

import gr.eap.EapSession;
import gr.eap.model.Analysis;
import gr.eap.model.Lysh;
import gr.eap.util.Anapty3hChoiceRenderer;
import gr.eap.util.Filo3eniaChoiceRenderer;
import gr.eap.util.warners.AskedKeyusersWarner;
import gr.eap.util.warners.ItDeptExistsWarner;
import gr.eap.util.warners.SensitiveDataWarner;
import gr.eap.util.warners.Warner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ViewAnalysis extends BasePage {

	private Analysis analysis = null;
	
	Map<String, Lysh> bestChoices;
	AttributeAppender markChoice = new AttributeAppender("class",new Model<String>("mark_choice")," ");

	public ViewAnalysis(final PageParameters parameters) {
		if (parameters == null || parameters.isEmpty()) {
			analysis = ((EapSession) getSession()).getAnalysis();
		} else {
			String code = parameters.get(0).toString();
			if (code == null) {
				getSession().warn("Κενός κωδικός.");
				setResponsePage(HomePage.class);
				return;
			}
			analysis = getDataStore().get(code);
			((EapSession) getSession()).setAnalysis(analysis);
		}

		if (analysis == null) {
			getSession().error("Δε βρέθηκε ανάλυση.");
			setResponsePage(HomePage.class);
			return;
		}

		add(new Label("title", analysis.getTitle()));
		add(new Label("usageEstimation", String.valueOf(analysis.getUsageEstimation())));
		add(new Label("description", analysis.getDescription()));
		add(new Label("keywords", analysis.getKeywords()));
		add(new Label("it_dept", getNaiOxi(analysis.isItDepartment())));
		add(new Label("key_users", getNaiOxi(analysis.isKeyusers())));
		add(new Label("sens_data", getNaiOxi(analysis.isSensitiveData())));

		analysis.prepareLyseisForCalculations();

		bestChoices = getBestChoices();

		final Anapty3hChoiceRenderer anapty3hChoiceRenderer = new Anapty3hChoiceRenderer();
		final Filo3eniaChoiceRenderer filo3eniaChoiceRenderer = new Filo3eniaChoiceRenderer();
		
		add(new PropertyListView<Lysh>("lyseis", analysis.getLyseis()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Lysh> item) {
				Lysh lysh = item.getModelObject();
				item.add(new Label("title"));
				
				setLabel(item,"totalCostF");
				setLabel(item,"kostosAnapty3hsF");
				setLabel(item,"ethsioKostosF");
				setLabel(item,"ethsioKostosMeKatToArxikoKostosF");
				setLabel(item,"xronosAnapty3hs");
				setLabel(item,"independence");
				setLabel(item,"independenceGain");
				
				WebMarkupContainer vsc = new WebMarkupContainer("viewSolutionContainer");
				vsc.setDefaultModel(new CompoundPropertyModel<Lysh>(lysh));
				vsc.add(new Label("title"));
				vsc.add(new Label("techDesc"));
				vsc.add(new Label("anapty3hBy", anapty3hChoiceRenderer.getDisplayValue(lysh.getAnapty3hBy()).toString()));
				vsc.add(new Label("synthrhshBy", anapty3hChoiceRenderer.getDisplayValue(lysh.getSynthrhshBy()).toString()));
				vsc.add(new Label("filo3eniaBy", filo3eniaChoiceRenderer.getDisplayValue(lysh.getFilo3eniaBy()).toString()));
				vsc.add(new Label("diaxeirishBy", anapty3hChoiceRenderer.getDisplayValue(lysh.getDiaxeirishBy()).toString()));
				vsc.add(new Label("kostosAnapty3hsF"));
				vsc.add(new Label("xronosAnapty3hs"));
				vsc.add(new Label("ethsioKostosSynthrhshsF"));
				vsc.add(new Label("ethsioKostosFilo3eniasF"));
				vsc.add(new Label("ethsioKostosDiaxeirishsF"));
				vsc.add(new Label("ethsioKostosAdeiwnF"));
				item.add(vsc);
			}
		});
		
		List<String> warnings = getWarnings();
		add(new WebMarkupContainer("no_warnings").setVisible(warnings.isEmpty()));
		add(new ListView<String>("warningList",warnings) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new Label("warning",item.getModelObject()));
			}
		});
		
	}
	
	private List<String> getWarnings() {
		List<String> warnings = new LinkedList<String>();
		for (Warner w:getWarners()) {
			String warning = w.warn(analysis);
			if (warning!=null) warnings.add(warning);
		}
		return warnings;
	}
	
	private List<Warner> getWarners() {
		List<Warner> w = new LinkedList<Warner>();
		w.add(new AskedKeyusersWarner());
		w.add(new ItDeptExistsWarner());
		w.add(new SensitiveDataWarner());
		return w;
	}
	
	private void setLabel(ListItem<Lysh> item,String labelId) {
		Label label = new Label(labelId);
		if (item.getModelObject().equals(bestChoices.get(labelId))) {
			label.add(markChoice);
		}
		item.add(label);
	}

	private Map<String,Lysh> getBestChoices() {
		Map<String,Lysh> m = new HashMap<String,Lysh>();
		for (Entry<String,Comparator<Lysh>> entry:getComparators().entrySet()) {
			m.put(entry.getKey(),findBest(entry.getValue()));
		}
		return m;
	}

	private Map<String,Comparator<Lysh>> getComparators() {
		Map<String,Comparator<Lysh>> m = new HashMap<String,Comparator<Lysh>>();
		m.put("totalCostF",new Comparator<Lysh>() {
			public int compare(Lysh o1, Lysh o2) {
				return o1.getTotalCost().subtract(o2.getTotalCost()).intValue();
			}
		});
		m.put("kostosAnapty3hsF",new Comparator<Lysh>() {
			public int compare(Lysh o1, Lysh o2) {
				return o1.getKostosAnapty3hs().subtract(o2.getKostosAnapty3hs()).intValue();
			}
		});
		m.put("ethsioKostosF",new Comparator<Lysh>() {
			public int compare(Lysh o1, Lysh o2) {
				return o1.getEthsioKostos().subtract(o2.getEthsioKostos()).intValue();
			}
		});
		m.put("ethsioKostosMeKatToArxikoKostosF",new Comparator<Lysh>() {
			public int compare(Lysh o1, Lysh o2) {
				return o1.getEthsioKostosMeKatToArxikoKostos().subtract(o2.getEthsioKostosMeKatToArxikoKostos()).intValue();
			}
		});
		m.put("xronosAnapty3hs",new Comparator<Lysh>() {
			public int compare(Lysh o1, Lysh o2) {
				return (int) (o1.getXronosAnapty3hs() - o2.getXronosAnapty3hs());
			}
		});
		m.put("independence",new Comparator<Lysh>() {
			public int compare(Lysh o1, Lysh o2) {
				return (int) (o1.getIndependence() - o2.getIndependence());
			}
		});
		m.put("independenceGain",new Comparator<Lysh>() {
			public int compare(Lysh o1, Lysh o2) {
				return o1.getIndependenceGain().subtract(o2.getIndependenceGain()).intValue();
			}
		});
		return m;
	}

	private Lysh findBest(Comparator<Lysh> comparator) {
		if (analysis.getLyseis() == null || analysis.getLyseis().isEmpty()) {
			return null;
		} else if (analysis.getLyseis().size() == 1) {
			return analysis.getLyseis().get(0);
		}
		List<Lysh> s = new ArrayList<Lysh>(analysis.getLyseis());
		Collections.sort(s, comparator);
		return s.get(0);
	}

	private String getNaiOxi(boolean b) {
		return b ? "Ναι" : "Όχι";
	}
}
