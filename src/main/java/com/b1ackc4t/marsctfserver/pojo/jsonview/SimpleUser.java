package com.b1ackc4t.marsctfserver.pojo.jsonview;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.b1ackc4t.marsctfserver.pojo.User;

public class SimpleUser implements PropertyFilter {
    @Override
    public boolean apply(Object object, String name, Object value) {
        if (object instanceof User) {
            switch (name) {
                case "upassword":
                case "role":
                case "regTime":
                case "lastActive":
                    return false;
                default:
                    return true;
            }
        }
        return true;
    }
}
