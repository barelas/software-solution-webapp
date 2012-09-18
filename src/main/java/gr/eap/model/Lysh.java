/*******************************************************************************
 * Copyright (c) 2012 George Barelas.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package gr.eap.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Lysh implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final int scale = 2;
	
	private String title = "Νέα προτεινόμενη λύση";
	private int anapty3hBy;
	private int synthrhshBy;
	private String techDesc;
	private int filo3eniaBy;
	private int diaxeirishBy;
	private int usageEstimation;
	
	private BigDecimal kostosAnapty3hs = BigDecimal.ZERO;
	private long xronosAnapty3hs = 0;
	private BigDecimal ethsioKostosSynthrhshs = BigDecimal.ZERO;
	private BigDecimal ethsioKostosFilo3enias = BigDecimal.ZERO;
	private BigDecimal ethsioKostosDiaxeirishs = BigDecimal.ZERO;
	private BigDecimal ethsioKostosAdeiwn = BigDecimal.ZERO;
	private BigDecimal maximuxCostFromAllLyseis = BigDecimal.ZERO;
	private NumberFormat numberFormat = DecimalFormat.getCurrencyInstance(Locale.GERMANY);
	
	
	public BigDecimal getTotalCost() {
		return kostosAnapty3hs.add(getEthsioKostos().multiply(new BigDecimal(usageEstimation)));
	}
	
	public String getTotalCostF() {
		return numberFormat.format(getTotalCost());
	}
	
	public BigDecimal getEthsioKostos() {
		return ethsioKostosSynthrhshs.add(ethsioKostosFilo3enias).add(ethsioKostosDiaxeirishs).add(ethsioKostosAdeiwn);
	}
	
	public String getEthsioKostosF() {
		return numberFormat.format(getEthsioKostos());
	}
	
	public BigDecimal getEthsioKostosMeKatToArxikoKostos() {
		if (usageEstimation==0) {
			return getEthsioKostos();
		}
		return getEthsioKostos().add(kostosAnapty3hs.divide(new BigDecimal(usageEstimation),scale,RoundingMode.DOWN));
	}
	
	public String getEthsioKostosMeKatToArxikoKostosF() {
		return numberFormat.format(getEthsioKostosMeKatToArxikoKostos());
	}
	
	public int getIndependence() {
		return anapty3hBy + synthrhshBy + filo3eniaBy + diaxeirishBy;
	}
	
	public BigDecimal getIndependenceGain() {
		if (maximuxCostFromAllLyseis.equals(BigDecimal.ZERO)) {
			return new BigDecimal(getIndependence());
		}
		return getTotalCost().divide(maximuxCostFromAllLyseis,scale,RoundingMode.DOWN).multiply(new BigDecimal(getIndependence()));
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getAnapty3hBy() {
		return anapty3hBy;
	}
	public void setAnapty3hBy(int anapty3hBy) {
		this.anapty3hBy = anapty3hBy;
	}
	public int getSynthrhshBy() {
		return synthrhshBy;
	}
	public void setSynthrhshBy(int synthrhshBy) {
		this.synthrhshBy = synthrhshBy;
	}
	public String getTechDesc() {
		return techDesc;
	}
	public void setTechDesc(String techDesc) {
		this.techDesc = techDesc;
	}
	public int getFilo3eniaBy() {
		return filo3eniaBy;
	}
	public void setFilo3eniaBy(int filo3eniaBy) {
		this.filo3eniaBy = filo3eniaBy;
	}
	public int getDiaxeirishBy() {
		return diaxeirishBy;
	}
	public void setDiaxeirishBy(int diaxeirishBy) {
		this.diaxeirishBy = diaxeirishBy;
	}
	public BigDecimal getKostosAnapty3hs() {
		return kostosAnapty3hs;
	}
	public void setKostosAnapty3hs(BigDecimal kostosAnapty3hs) {
		this.kostosAnapty3hs = kostosAnapty3hs;
	}
	public long getXronosAnapty3hs() {
		return xronosAnapty3hs;
	}
	public void setXronosAnapty3hs(long xronosAnapty3hs) {
		this.xronosAnapty3hs = xronosAnapty3hs;
	}
	public BigDecimal getEthsioKostosSynthrhshs() {
		return ethsioKostosSynthrhshs;
	}
	public void setEthsioKostosSynthrhshs(BigDecimal ethsioKostosSynthrhshs) {
		this.ethsioKostosSynthrhshs = ethsioKostosSynthrhshs;
	}
	public BigDecimal getEthsioKostosFilo3enias() {
		return ethsioKostosFilo3enias;
	}
	public void setEthsioKostosFilo3enias(BigDecimal ethsioKostosFilo3enias) {
		this.ethsioKostosFilo3enias = ethsioKostosFilo3enias;
	}
	public BigDecimal getEthsioKostosDiaxeirishs() {
		return ethsioKostosDiaxeirishs;
	}
	public void setEthsioKostosDiaxeirishs(BigDecimal ethsioKostosDiaxeirishs) {
		this.ethsioKostosDiaxeirishs = ethsioKostosDiaxeirishs;
	}
	public BigDecimal getEthsioKostosAdeiwn() {
		return ethsioKostosAdeiwn;
	}
	public void setEthsioKostosAdeiwn(BigDecimal ethsioKostosAdeiwn) {
		this.ethsioKostosAdeiwn = ethsioKostosAdeiwn;
	}
	
	public int getUsageEstimation() {
		return usageEstimation;
	}
	public void setUsageEstimation(int usageEstimation) {
		this.usageEstimation = usageEstimation;
	}
	private synchronized BigDecimal parseInput(String input) throws ParseException {
		BigDecimal res = null;
		boolean retry = false;
		try {
			res = new BigDecimal(numberFormat.parse(input).toString());
		} catch (ParseException e) {
			retry = true;
		}
		if (retry) {
			try {
				res = new BigDecimal(numberFormat.parse(input+" €").toString());
			} catch (ParseException e) {
				throw e;
			}
		}
		return res;
	}

	public BigDecimal getMaximuxCostFromAllLyseis() {
		return maximuxCostFromAllLyseis;
	}

	public void setMaximuxCostFromAllLyseis(BigDecimal maximuxCostFromAllLyseis) {
		this.maximuxCostFromAllLyseis = maximuxCostFromAllLyseis;
	}
	
	public String getKostosAnapty3hsF() {
		return numberFormat.format(kostosAnapty3hs);
	}
	public void setKostosAnapty3hsF(String kostosAnapty3hs) throws ParseException {
		this.kostosAnapty3hs = parseInput(kostosAnapty3hs);
	}
	public String getEthsioKostosSynthrhshsF() {
		return numberFormat.format(ethsioKostosSynthrhshs);
	}
	public void setEthsioKostosSynthrhshsF(String ethsioKostosSynthrhshs) throws ParseException {
		this.ethsioKostosSynthrhshs = parseInput(ethsioKostosSynthrhshs);
	}
	public String getEthsioKostosFilo3eniasF() {
		return numberFormat.format(ethsioKostosFilo3enias);
	}
	public void setEthsioKostosFilo3eniasF(String ethsioKostosFilo3enias) throws ParseException {
		this.ethsioKostosFilo3enias = parseInput(ethsioKostosFilo3enias);
	}
	public String getEthsioKostosDiaxeirishsF() {
		return numberFormat.format(ethsioKostosDiaxeirishs);
	}
	public void setEthsioKostosDiaxeirishsF(String ethsioKostosDiaxeirishs) throws ParseException {
		this.ethsioKostosDiaxeirishs = parseInput(ethsioKostosDiaxeirishs);
	}
	public String getEthsioKostosAdeiwnF() {
		return numberFormat.format(ethsioKostosAdeiwn);
	}
	public void setEthsioKostosAdeiwnF(String ethsioKostosAdeiwn) throws ParseException {
		this.ethsioKostosAdeiwn = parseInput(ethsioKostosAdeiwn);
	}
}
