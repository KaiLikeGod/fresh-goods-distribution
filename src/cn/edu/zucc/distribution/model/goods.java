package cn.edu.zucc.distribution.model;

public class goods {
    public static final String [] tableTitlesCustomer={"商品编号","仓库编号","商品类别","商品名称", "商品价格", "商品尺寸","商品重量","库存数量","是否需要冷藏"};

    private int goodsid;
    private int houseid;
    private int typeid;
    private String goodsname;
    private float goodsprice;
    private float goodssize;
    private float ordersweight;
    private int inventorynumber;
    private boolean keepneedcold;

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public int getHouseid() {
        return houseid;
    }

    public void setHouseid(int houseid) {
        this.houseid = houseid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public float getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(float goodsprice) {
        this.goodsprice = goodsprice;
    }

    public float getGoodssize() {
        return goodssize;
    }

    public void setGoodssize(float goodssize) {
        this.goodssize = goodssize;
    }

    public float getOrdersweight() {
        return ordersweight;
    }

    public void setOrdersweight(float ordersweight) {
        this.ordersweight = ordersweight;
    }

    public int getInventorynumber() {
        return inventorynumber;
    }

    public void setInventorynumber(int inventorynumber) {
        this.inventorynumber = inventorynumber;
    }

    public boolean isKeepneedcold() {
        return keepneedcold;
    }

    public void setKeepneedcold(boolean keepneedcold) {
        this.keepneedcold = keepneedcold;
    }

    public String getCell(int col){
        if (col==0) return Integer.toString(this.getGoodsid());
        else if (col==1) return Integer.toString(this.getHouseid());
        else if (col==2) return Integer.toString(this.getTypeid());
        else if (col==3) return this.getGoodsname();
        else if (col==4) return Float.toString(this.getGoodsprice());
        else if (col==5) return Float.toString(this.getGoodssize());
        else if (col==6) return Float.toString(this.getOrdersweight());
        else if (col==7) return Integer.toString(this.getInventorynumber());
        else if (col==8) {
            if (this.isKeepneedcold()) return "是";
            else return "否";
        }
        else return "";
    }
}
