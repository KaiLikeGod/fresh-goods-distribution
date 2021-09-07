package cn.edu.zucc.distribution;

import cn.edu.zucc.distribution.comtrol.example.ExampleGoodsManager;
import cn.edu.zucc.distribution.comtrol.example.ExamplePlanManager;
import cn.edu.zucc.distribution.comtrol.example.ExampleStepManager;
import cn.edu.zucc.distribution.comtrol.example.ExampleUserManager;
import cn.edu.zucc.distribution.itf.IGoodsManager;
import cn.edu.zucc.distribution.itf.IPlanManager;
import cn.edu.zucc.distribution.itf.IStepManager;
import cn.edu.zucc.distribution.itf.IUserManager;

public class DistributionUtil {
	public static IPlanManager planManager=new ExamplePlanManager();//需要换成自行设计的实现类
	public static IStepManager stepManager=new ExampleStepManager();//需要换成自行设计的实现类
	public static IUserManager userManager=new ExampleUserManager();//需要换成自行设计的实现类
	public static IGoodsManager goodsManager=new ExampleGoodsManager();
}

