package com.example.lxcblog.Utils;

/**
 * 生成全局唯一uid
 */
public class Smell {
    private static long lastTime = 0;

    public static long getUid() {

        StringBuilder sb = new StringBuilder();
        // 第一位固定为1
        sb.append("1");
        // 加入机器时间
        long nowTime = System.currentTimeMillis();
        // 判断机器是否回拨
        //       如果机器回拨 抛出异常
        if (nowTime < lastTime) {
            try {
                throw new Exception("时间错误");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            // 将时间加入字符串
            sb.append(nowTime);
            // 加入当前的线程
            sb.append(Thread.currentThread().getId());
        }
        return Long.parseLong(sb.toString());
    }
}
