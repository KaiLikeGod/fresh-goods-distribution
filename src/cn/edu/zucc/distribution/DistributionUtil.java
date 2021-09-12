package cn.edu.zucc.distribution;

import cn.edu.zucc.distribution.comtrol.example.*;
import cn.edu.zucc.distribution.itf.*;

public class DistributionUtil {
	public static IUserManager userManager=new ExampleUserManager();//需要换成自行设计的实现类
	public static IGoodsManager goodsManager=new ExampleGoodsManager();
	public static IOrderMannger orderMannger=new ExampleOrderManager();
	public static IDeliveryMannger deliveryMannger=new ExampleDeliveryManager();
}

