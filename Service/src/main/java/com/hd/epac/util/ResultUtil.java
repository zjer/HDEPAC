package com.hd.epac.util;

public class ResultUtil {
    /// <summary>
    /// 操作结果类型
    /// </summary>
    public Object state;
    /// <summary>
    /// 获取 消息内容
    public String message;
    /// <summary>
    /// 获取 返回数据
    /// </summary>
    public Object rows;

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }
    public static ResultUtil success(String msg, Object rows){
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setMessage(msg);
        resultUtil.setRows(rows);
        resultUtil.state = true;
        return resultUtil;
    }
    public static ResultUtil error(String msg){
        ResultUtil resultUtil = new ResultUtil();
        resultUtil.setMessage(msg);
        resultUtil.state = false;
        return resultUtil;
    }
}
