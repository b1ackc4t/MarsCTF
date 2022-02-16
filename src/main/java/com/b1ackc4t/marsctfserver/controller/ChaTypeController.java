package com.b1ackc4t.marsctfserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.b1ackc4t.marsctfserver.dao.ChaTypeMapper;
import com.b1ackc4t.marsctfserver.pojo.ChaType;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.ChaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChaTypeController {

    final ChaTypeService chaTypeService;
    final ChaTypeMapper chaTypeMapper;

    @Autowired
    public ChaTypeController(ChaTypeService chaTypeService, ChaTypeMapper chaTypeMapper) {
        this.chaTypeService = chaTypeService;
        this.chaTypeMapper = chaTypeMapper;
    }

    /**
     * 获取所有题目类型
     * @return
     */
    @GetMapping("/chaType")
    public ReturnRes getAll() {
        List<ChaType> list = chaTypeMapper.selectAll();
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

    @GetMapping("/admin/chaType/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getByPageForAdmin(@PathVariable int pageSize,
                                       @PathVariable int pageNum) {
        return chaTypeService.getTypesByPageForAdmin(pageSize, pageNum);
    }

    @PostMapping("/admin/chaType")
    public ReturnRes operate(@RequestParam String action,
                             @RequestParam(name = "data") String chaTypeJson) {
        ChaType chaType = JSONObject.parseObject(chaTypeJson, ChaType.class);
        if (action.equals("save")) {
            return chaTypeService.saveType(chaType);
        } else if (action.equals("update")) {
            return chaTypeService.updateType(chaType);
        } else if (action.equals("remove")) {
            return chaTypeService.removeType(chaType);
        } else {
            return new ReturnRes(false, "操作暂不支持");
        }
    }
}
