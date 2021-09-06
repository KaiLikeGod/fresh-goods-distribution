package cn.edu.zucc.distribution.model;

import java.util.Date;

public class BeanPlan {
	public static final String[] tableTitles={"???","????","??????","???????"};

	/**
	 * ?????§Ú???javabean?????????????????col??????????§Ö???????0???
	 */
	private int planid;
	private String userid;
	private int planorder;
	private String planname;
	private Date createtime;
	private int stepcount;
	private int start;
	private int finish;

	public int getPlanid() {
		return planid;
	}

	public void setPlanid(int planid) {
		this.planid = planid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getPlanorder() {
		return planorder;
	}

	public void setPlanorder(int planorder) {
		this.planorder = planorder;
	}

	public String getPlanname() {
		return planname;
	}

	public void setPlanname(String planname) {
		this.planname = planname;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getStepcount() {
		return stepcount;
	}

	public void setStepcount(int stepcount) {
		this.stepcount = stepcount;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getFinish() {
		return finish;
	}

	public void setFinish(int finish) {
		this.finish = finish;
	}

	public String getCell(int col){
		if(col==0) return 	Integer.toString(this.getPlanorder()) ;
		else if(col==1) return this.getPlanname();
		else if(col==2) return Integer.toString(this.getStepcount());
		else if(col==3) return Integer.toString(this.getFinish());
		else return "";
	}

}
