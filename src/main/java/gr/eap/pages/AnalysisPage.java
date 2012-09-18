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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.AjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class AnalysisPage extends BasePage {

	private Analysis analysis = null;
	private List<Integer> normalChoices = Arrays.asList(new Integer[] {1,2,3});
	private List<Integer> weightedChoices = Arrays.asList(new Integer[] {1,2,4});
	
    public AnalysisPage(final PageParameters parameters) {
		if (parameters==null || parameters.isEmpty()) {
			analysis = ((EapSession)getSession()).getAnalysis();
		} else {
			String code = parameters.get(0).toString();
			if (code==null) {
				analysis = new Analysis();
				warn("Κενός κωδικός.");
			} else if ("new".equals(code)) {
				analysis = new Analysis();
			} else {
				analysis = getDataStore().get(code);
				if (analysis==null) {
					analysis = new Analysis();
					warn("Δε βρέθηκε ανάλυση με κωδικό: " + code);
				}
			}
			
		}
		if (analysis==null) {
			analysis = new Analysis();
		}
		((EapSession)getSession()).setAnalysis(analysis);
		
		Form<Analysis> form  = new Form<Analysis>("form",new CompoundPropertyModel<Analysis>(analysis));
		form.add(new TextField<String>("title"));
		form.add(new TextArea<String>("description"));
		form.add(new TextField<String>("keywords"));
		form.add(new TextField<String>("usageEstimation"));
		form.add(new CheckBox("keyusers"));
		form.add(new CheckBox("itDepartment"));
		form.add(new CheckBox("sensitiveData"));
		
		form.add(new SubmitLink("submit") {
			@Override
			public void onSubmit() {
				setResponsePage(ViewAnalysis.class);
			}
		});
		add(form);
		
		final WebMarkupContainer solutionsContainer = new WebMarkupContainer("solutionsContainer");
		solutionsContainer.setOutputMarkupId(true);
		form.add(solutionsContainer);
		final WebMarkupContainer sygkrishContainer = new WebMarkupContainer("sygkrishContainer");
		sygkrishContainer.setOutputMarkupId(true);
		form.add(sygkrishContainer);
		
		solutionsContainer.add(new PropertyListView<Lysh>("solutions",analysis.getLyseis()) {
			@Override
			protected void populateItem(final ListItem<Lysh> item) {
				item.add(new Label("title"));
				Form<Lysh> lyshForm = new Form<Lysh>("solution_form");
				lyshForm.setDefaultModel(new CompoundPropertyModel<Lysh>(item.getModelObject()));
				lyshForm.add(new TextField<String>("title"));
				lyshForm.add(new DropDownChoice<Integer>("anapty3hBy",weightedChoices,new Anapty3hChoiceRenderer()));
				lyshForm.add(new DropDownChoice<Integer>("synthrhshBy",weightedChoices,new Anapty3hChoiceRenderer()));
				lyshForm.add(new TextArea<String>("techDesc"));
				lyshForm.add(new DropDownChoice<Integer>("filo3eniaBy",normalChoices,new Filo3eniaChoiceRenderer()));
				lyshForm.add(new DropDownChoice<Integer>("diaxeirishBy",normalChoices,new Anapty3hChoiceRenderer()));
				lyshForm.add(new AjaxSubmitLink("save_solution") {
					private static final long serialVersionUID = 1L;
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						target.add(solutionsContainer);
						target.add(sygkrishContainer);
						target.prependJavaScript("hideSolutionForm();");
					}
					@Override
					protected void onError(AjaxRequestTarget target, Form<?> form) {
						getSession().error("Παρουσιάστηκε πρόβλημα!!!");
						target.add(getFeedbackPanel());
					}
				});
				lyshForm.add(new AjaxFallbackLink<AnalysisPage>("cancel") {
					private static final long serialVersionUID = 1L;
					@Override
					public void onClick(AjaxRequestTarget target) {
						target.add(solutionsContainer);
						target.add(sygkrishContainer);
						target.prependJavaScript("hideSolutionForm();");
					}
				});
				item.add(lyshForm);
				item.add(new AjaxFallbackLink<AnalysisPage>("delete") {
					private static final long serialVersionUID = 1L;
					@Override
					public void onClick(AjaxRequestTarget target) {
						analysis.getLyseis().remove(item.getModelObject());
						target.add(solutionsContainer);
						target.add(sygkrishContainer);
					}
					@Override
					protected IAjaxCallDecorator getAjaxCallDecorator() {
						return new AjaxCallDecorator() {
							private static final long serialVersionUID = 1L;
							@Override
							public CharSequence decorateScript(Component c, CharSequence script) {
								return "if( !confirm('Είστε σίγουρος για τη διαγραφή της λύσης με τιτλο \"" + item.getModelObject().getTitle() + "\";') ) return false;" + script;
							}
						};
					}
				});
			}
		});
		
		sygkrishContainer.add(new PropertyListView<Lysh>("solutions",analysis.getLyseis()) {
			@Override
			protected void populateItem(final ListItem<Lysh> item) {
				item.add(new Label("title"));
				Form<Lysh> lyshForm = new Form<Lysh>("solution_form");
				lyshForm.setDefaultModel(new CompoundPropertyModel<Lysh>(item.getModelObject()));
				lyshForm.add(new Label("title"));
				lyshForm.add(new TextField<BigDecimal>("kostosAnapty3hsF"));
				lyshForm.add(new TextField<Long>("xronosAnapty3hs"));
				lyshForm.add(new TextField<BigDecimal>("ethsioKostosSynthrhshsF"));
				lyshForm.add(new TextField<BigDecimal>("ethsioKostosFilo3eniasF"));
				lyshForm.add(new TextField<BigDecimal>("ethsioKostosDiaxeirishsF"));
				lyshForm.add(new TextField<BigDecimal>("ethsioKostosAdeiwnF"));
				lyshForm.add(new AjaxSubmitLink("save_solution") {
					private static final long serialVersionUID = 1L;
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						target.add(solutionsContainer);
						target.add(sygkrishContainer);
						target.prependJavaScript("hideSolutionForm();");
					}
					@Override
					protected void onError(AjaxRequestTarget target, Form<?> form) {
						getSession().error("Παρουσιάστηκε πρόβλημα!!!");
						target.add(getFeedbackPanel());
					}
				});
				lyshForm.add(new AjaxFallbackLink<AnalysisPage>("cancel") {
					private static final long serialVersionUID = 1L;
					@Override
					public void onClick(AjaxRequestTarget target) {
						target.add(solutionsContainer);
						target.add(sygkrishContainer);
						target.prependJavaScript("hideSolutionForm();");
					}
				});
				item.add(lyshForm);
			}
		});
		
		form.add(new AjaxFallbackLink<AnalysisPage>("add_solution") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				analysis.getLyseis().add(new Lysh());
				target.add(solutionsContainer);
				target.add(sygkrishContainer);
			}
		});
    }
}
