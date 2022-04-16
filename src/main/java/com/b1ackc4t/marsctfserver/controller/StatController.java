package com.b1ackc4t.marsctfserver.controller;

import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StatController {

    final StatService statService;

    @Autowired
    public StatController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping("/infoCount")
    public ReturnRes getInfoCount() {
        return statService.getInfoCount();
    }

    @GetMapping("/chaOfTypeCount")
    public ReturnRes getChaOfTypeCount() {
        return statService.getChaCountByType();
    }

    @GetMapping("/userRank")
    public ReturnRes getUserRank() {
        return statService.getTotalRanking(10);
    }

    @GetMapping("/userRank/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getUserRankView(@PathVariable int pageSize,
                                 @PathVariable int pageNum) {
        return statService.getRankView(pageSize, pageNum);
    }
}
