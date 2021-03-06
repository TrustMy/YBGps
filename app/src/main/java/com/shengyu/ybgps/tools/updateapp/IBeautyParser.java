package com.shengyu.ybgps.tools.updateapp;



import com.shengyu.ybgps.tools.bean.Bean;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Trust on 2016/8/16.
 */
public interface IBeautyParser {

    /**
     *
     * 解析输入流，获取Beauty列表
     * @param is
     * @return
     * @throws Exception
     */
    public List<Bean> parse(InputStream is) throws Exception;

    /**
     *
     * 序列化Beauty对象集合，得到XML形式的字符串
     * @param beauties
     * @return
     * @throws Exception
     */
    public String serialize(List<Bean> beauties) throws Exception;

}
