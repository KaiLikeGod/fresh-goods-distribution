package cn.edu.zucc.distribution.model;

public class buy {
    public static final String [] tableTitles={"采购单编号", "商品编号", "存储仓库", "采购数量"};
    private int buyid;
    private int goodsid;
    private int houseid;
    private int buynumber;

    public int getBuyid() {
        return buyid;
    }

    public void setBuyid(int buyid) {
        this.buyid = buyid;
    }

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

    public int getBuynumber() {
        return buynumber;
    }

    public void setBuynumber(int buynumber) {
        this.buynumber = buynumber;
    }

    public String getCell(int col){
        if (col==0) return Integer.toString(this.getBuyid());
        else if (col==1) return Integer.toString(this.getGoodsid());
        else if (col==2) return Integer.toString(this.getHouseid());
        else if (col==3) return Integer.toString(this.getBuynumber());
        else return "";
    }
}
