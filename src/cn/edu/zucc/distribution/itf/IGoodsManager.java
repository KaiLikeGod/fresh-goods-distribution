package cn.edu.zucc.distribution.itf;

import cn.edu.zucc.distribution.model.*;
import cn.edu.zucc.distribution.util.BaseException;

import java.util.List;

public interface IGoodsManager{
    public List<goods> loadAll() throws BaseException;
}
