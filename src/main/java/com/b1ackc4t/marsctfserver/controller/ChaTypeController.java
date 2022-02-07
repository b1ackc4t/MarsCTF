package com.b1ackc4t.marsctfserver.controller;

import com.b1ackc4t.marsctfserver.pojo.ChaType;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.ChaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChaTypeController {

    final ChaTypeService chaTypeService;

    @Autowired
    public ChaTypeController(ChaTypeService chaTypeService) {
        this.chaTypeService = chaTypeService;
    }

    /**
     * 获取所有题目类型
     * @return
     */
    @GetMapping("/chaType")
    public ReturnRes getAll() {
        List<ChaType> list = chaTypeService.list();
        if (list != null) {
            return new ReturnRes(true, list, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    /**
     * 根据id查找题目类型
     * @param id
     * @return
     */
    @GetMapping("/admin/chaType/{id:\\d+}")
    public ReturnRes getById(@PathVariable int id) {
        ChaType chaType = chaTypeService.getById(id);
        if (chaType != null) {
            return new ReturnRes(true, chaType);
        }
        return new ReturnRes(false);
    }
}
