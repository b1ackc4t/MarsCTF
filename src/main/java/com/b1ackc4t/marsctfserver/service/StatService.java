package com.b1ackc4t.marsctfserver.service;


import com.b1ackc4t.marsctfserver.pojo.ReturnRes;

public interface StatService {
    ReturnRes getInfoCount();
    ReturnRes getChaCountByType();
    ReturnRes getTotalRanking(int num);
    ReturnRes getRankView(int pageSize, int pageNum);
}
