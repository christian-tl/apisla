package com.dangdang.logtest;

import java.text.DecimalFormat;

public class SlaBean {

	private String slatime;
	private float ms10;
	private float ms20;
	private float ms30;
	private float ms40;
	private float ms50;
	private float ms60;
	private float ms70;
	private float ms80;
	private float ms90;
	
	private float ms100;
	private float ms200;
	private float ms500;
	
	private float s1;
	
	private int sla500;
	private int sla800;
	private int sla995;
	private int sla999;
	private int total;
	
	DecimalFormat fnum = new DecimalFormat("##0"); 
	
	public String getSlatime() {
		return slatime;
	}
	public void setSlatime(String slatime) {
		this.slatime = slatime;
	}
	public int getMs10() {
		return Integer.parseInt(fnum.format(ms10 * 100));
	}
	public void setMs10(float ms10) {
		this.ms10 = ms10;
	}
	public int getMs20() {
		return Integer.parseInt(fnum.format(ms20 * 100));
	}
	public void setMs20(float ms20) {
		this.ms20 = ms20;
	}
	public int getMs30() {
		return Integer.parseInt(fnum.format(ms30 * 100));
	}
	public void setMs30(float ms30) {
		this.ms30 = ms30;
	}
	public int getMs40() {
		return Integer.parseInt(fnum.format(ms40 * 100));
	}
	public void setMs40(float ms40) {
		this.ms40 = ms40;
	}
	public int getMs50() {
		return Integer.parseInt(fnum.format(ms50 * 100));
	}
	public void setMs50(float ms50) {
		this.ms50 = ms50;
	}
	public int getMs60() {
		return Integer.parseInt(fnum.format(ms60 * 100));
	}
	public void setMs60(float ms60) {
		this.ms60 = ms60;
	}
	public int getMs70() {
		return Integer.parseInt(fnum.format(ms70 * 100));
	}
	public void setMs70(float ms70) {
		this.ms70 = ms70;
	}
	public int getMs80() {
		return Integer.parseInt(fnum.format(ms80 * 100));
	}
	public void setMs80(float ms80) {
		this.ms80 = ms80;
	}
	public int getMs90() {
		return Integer.parseInt(fnum.format(ms90 * 100));
	}
	public void setMs90(float ms90) {
		this.ms90 = ms90;
	}
	public int getMs100() {
		return Integer.parseInt(fnum.format(ms100 * 100));
	}
	public void setMs100(float ms100) {
		this.ms100 = ms100;
	}
	public int getMs200() {
		return Integer.parseInt(fnum.format(ms200 * 100));
	}
	public void setMs200(float ms200) {
		this.ms200 = ms200;
	}
	public int getMs500() {
		return Integer.parseInt(fnum.format(ms500 * 100));
	}
	public void setMs500(float ms500) {
		this.ms500 = ms500;
	}
	public int getS1() {
		return Integer.parseInt(fnum.format(s1 * 100));
	}
	public void setS1(float s1) {
		this.s1 = s1;
	}
	public int getSla500() {
		return sla500;
	}
	public void setSla500(int sla500) {
		this.sla500 = sla500;
	}
	public int getSla800() {
		return sla800;
	}
	public void setSla800(int sla800) {
		this.sla800 = sla800;
	}
	public int getSla995() {
		return sla995;
	}
	public void setSla995(int sla995) {
		this.sla995 = sla995;
	}
	public int getSla999() {
		return sla999;
	}
	public void setSla999(int sla999) {
		this.sla999 = sla999;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
