package com.example.administrator.eventbustest.enent;

/**
 * @author ZD
 *         created at 2017/6/26 10:52
 *         description：定义粘性事件
 *         黏性事件，就是在发送事件之后再订阅该事件也能收到该事件，跟黏性广播类似
 */

public class StickyEvent {
    public String message;
    ;

    public StickyEvent(String message) {
        this.message = message;
    }


}
