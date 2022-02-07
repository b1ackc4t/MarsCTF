package com.b1ackc4t.marsctfserver.pojo.jsonview;

import com.alibaba.fastjson.serializer.PropertyFilter;
import com.b1ackc4t.marsctfserver.pojo.Challenge;
import com.b1ackc4t.marsctfserver.pojo.User;

public class ListChallenge implements PropertyFilter {
    @Override
    public boolean apply(Object object, String name, Object value) {
        if (object instanceof Challenge) {
            switch (name) {
                case "desc":
                case "score":
                case "tid":
                case "exposed":
                case "cretime":
                case "fid":
                    return false;
                default:
                    return true;
            }
        }
        return true;
    }
}
