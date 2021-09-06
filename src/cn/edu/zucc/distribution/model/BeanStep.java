package cn.edu.zucc.distribution.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanStep {
	public static final String[] tblStepTitle={"???","????","?????????","?????????","????????","?????????"};
	/**
	 * ?????§Ú???javabean?????????????????col??????????§Ö???????0???
	 */
	private int stepid;
	private int planid;
	private int steporder;
	private String stepname;
	private Date stepbegin;
	private Date stepend;
	private Date realbegin;
	private Date realend;
	public int getStepid() {
		return stepid;
	}
	public void setStepid(int stepid) {
		this.stepid = stepid;
	}
	public int getPlanid() {
		return planid;
	}
	public void setPlanid(int planid) {
		this.planid = planid;
	}
	public int getSteporder() {
		return steporder;
	}
	public void setSteporder(int steporder) {
		this.steporder = steporder;
	}
	public String getStepname() {
		return stepname;
	}
	public void setStepname(String stepname) {
		this.stepname = stepname;
	}
	public Date getStepbegin() {
		return stepbegin;
	}
	public void setStepbegin(Date stepbegin) {
		this.stepbegin = stepbegin;
	}
	public Date getStepend() {
		return stepend;
	}
	public void setStepend(Date stepend) {
		this.stepend = stepend;
	}
	public Date getRealbegin() {
		return realbegin;
	}
	public void setRealbegin(Date realbegin) {
		this.realbegin = realbegin;
	}
	public Date getRealend() {
		return realend;
	}
	public void setRealend(Date realend) {
		this.realend = realend;
	}
	SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
	public String getCell(int col){
		if(col==0) return Integer.toString(this.getSteporder());
		else if(col==1) return this.getStepname();
		else if(col==2) return f.format(this.getStepbegin());
		else if(col==3) return f.format(this.getStepend());
		else if(col==4) {
			if(this.getRealbegin()!=null)
				return f.format(this.getRealbegin());
			else
				return "¦Ä???";

		}
		else if(col==5)
		{
			if(this.getRealend()!=null)
				return f.format(this.getRealend());
			else
				return "¦Ä????";


		}


		else return "";
	}
}
