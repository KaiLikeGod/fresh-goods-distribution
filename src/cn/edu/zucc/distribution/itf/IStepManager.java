package cn.edu.zucc.distribution.itf;

import java.util.List;

import cn.edu.zucc.distribution.model.BeanPlan;
import cn.edu.zucc.distribution.model.BeanStep;
import cn.edu.zucc.distribution.util.BaseException;

public interface IStepManager {
	public List<BeanStep> loadSteps(BeanPlan plan)throws BaseException;
	
	/**
	 * ��Ӳ���
	 * ����Ĳ������Ϊ��ǰ�ƻ���������+1
	 * ע�⣺������ַ�����ʱ�����͵�ת������Ӻ�������ƻ�������Ӧ������ֵ
	 * @param plan
	 * @param name
	 * @param planstartdate
	 * @param planfinishdate
	 * @throws BaseException
	 */
	public void add(BeanPlan plan, String name, String planstartdate, String planfinishdate)throws BaseException;

	
	
	/**
	 * ɾ�����裬
	 * ע��ɾ����������ƻ����ж�Ӧ�Ĳ�������
	 * @param step
	 * @throws BaseException
	 */
	public void deleteStep(BeanStep step)throws BaseException;

	/**
	 * ���õ�ǰ�����ʵ�ʿ�ʼʱ�䣬����Ӧ�ļƻ������ѿ�ʼ��������
	 * 
	 * @param step
	 * @throws BaseException
	 */
	public void startStep(BeanStep step)throws BaseException;
	/**
	 * ���õ�ǰ�����ʵ�����ʱ�䣬����Ӧ�ļƻ���������ɲ�������
	 * @param step
	 * @throws BaseException
	 */
	public void finishStep(BeanStep step)throws BaseException;
	/**
	 * ������ǰ�����˳���
	 * ע�⣺���ݿ���У�plan_id,step_order�Ͻ�����Ψһ������������ǰ��������ֵ����һ��������ֵʱ���ܳ������ֵһ�������
	 * @param step
	 * @throws BaseException
	 */
	public void moveUp(BeanStep step)throws BaseException;
	public void moveDown(BeanStep step)throws BaseException;
	

}
