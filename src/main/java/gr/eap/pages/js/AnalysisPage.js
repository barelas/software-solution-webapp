#-------------------------------------------------------------------------------
# Copyright (c) 2012 George Barelas.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the GNU Public License v3.0
# which accompanies this distribution, and is available at
# http://www.gnu.org/licenses/gpl.html
#-------------------------------------------------------------------------------
var currentStep = 1;
var maxSteps = 3;

$(document).ready(function(){
	currentStep = 1;
	$('form > div').scrollTo($('#step1'),1000);
	
	$('#scroll_left').click(function(){
		scrollLeft();
		return false;
	});
	
	$('#scroll_right').click(function(){
		scrollRight();
		return false;
	});
	
});

function scrollLeft() {
	if (currentStep > 1) {
		$('form > div').scrollTo($('#step' + (--currentStep)),1000);
	}
}

function scrollRight() {
	if (currentStep < 3) {
		$('form > div').scrollTo($('#step' + (++currentStep)),1000);
	}
}

function hideSolutionForm() {
	$('.solution_form').fadeOut();
}
