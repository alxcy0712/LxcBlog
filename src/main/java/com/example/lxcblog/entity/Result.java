package com.example.lxcblog.entity;

/**
 * @param <T>
 * @param      code   1 成功    0 失败
 * @param      msg    成功？ 失败？
 * @param      data   返回的信息
 */
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result(){

    }
    public Result(T data){
        this.data = data;
    }
    public static Result success(){
        Result result = new Result();
        result.setCode("1");
        result.setMsg("成功");
        return result;
    }
    public static <T> Result<T> success(T data){
        Result<T> result = new Result<T>();
        result.setCode("1");
        result.setMsg("成功");
        result.setData(data);
        return result;
    }
    public static <T> Result<T> success(String code , String msg , T data){
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    public static <T> Result<T> error(String code , String msg , T data){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
