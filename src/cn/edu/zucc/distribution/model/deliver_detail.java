package cn.edu.zucc.distribution.model;

public class deliver_detail {
    public static final String [] tableTitles={"∂©µ•±‡∫≈", "≈‰ÀÕµ•±‡∫≈", "≈‰ÀÕ–Ú∫≈"};

    private int orderid;
    private int deliveryid;
    private int deliverynum;

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getDeliveryid() {
        return deliveryid;
    }

    public void setDeliveryid(int deliveryid) {
        this.deliveryid = deliveryid;
    }

    public int getDeliverynum() {
        return deliverynum;
    }

    public void setDeliverynum(int deliverynum) {
        this.deliverynum = deliverynum;
    }

    public String getCell(int col){
        if (col==0) return Integer.toString(this.getOrderid());
        else if (col==1) return Integer.toString(this.getDeliveryid());
        else if (col==2) return Integer.toString(this.getDeliverynum());
        else return "";
    }
}
