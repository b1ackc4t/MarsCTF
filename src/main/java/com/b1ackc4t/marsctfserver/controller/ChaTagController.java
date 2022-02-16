package com.b1ackc4t.marsctfserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.b1ackc4t.marsctfserver.dao.ChaTagMapper;
import com.b1ackc4t.marsctfserver.pojo.ChaTag;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.ChaTagService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ChaTagController {

    final ChaTagService chaTagService;
    final ChaTagMapper chaTagMapper;

    public ChaTagController(ChaTagService chaTagService, ChaTagMapper chaTagMapper) {
        this.chaTagService = chaTagService;
        this.chaTagMapper = chaTagMapper;
    }

    @GetMapping("/admin/chaTag")
    public ReturnRes getAll() {
        List<ChaTag> list = chaTagMapper.selectAllTag();
        if (list != null) {
            return new ReturnRes(true, list);
        }
        return new ReturnRes(false);
    }

    @GetMapping("/admin/chaTag/{id:\\d+}")
    public ReturnRes getById(@PathVariable int id) {
        ChaTag chaTag = chaTagMapper.selectTagById(id);
        if (chaTag != null) {
            return new ReturnRes(true, chaTag);
        }
        return new ReturnRes(false);
    }

    @GetMapping("/chaTag")
    public ReturnRes getByType(@RequestParam(required = false) String type) {
        if (type != null) {
            return chaTagService.getTagsByType(type);
        } else {
            return chaTagService.getTags();
        }

    }

    @GetMapping("/admin/chaTag/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes queryByPage(@PathVariable int pageSize,
                                 @PathVariable int pageNum) {
        PageInfo<ChaTag> pageInfo = chaTagService.getAllTagByPage(pageNum, pageSize);
        if (pageInfo != null) {
            return new ReturnRes(true, pageInfo);
        }
        return new ReturnRes(false);
    }

    @PostMapping("/admin/chaTag")
    public ReturnRes operate(@RequestParam String action,
                             @RequestParam(name = "data") String chaTagJson) {
        ChaTag chaTag = JSONObject.parseObject(chaTagJson, ChaTag.class);
        if (action.equals("save")) {
            if (chaTagService.save(chaTag)) {
                return new ReturnRes(true, chaTag, "标签添加成功");
            }
        } else if (action.equals("update")) {
            chaTag.setTname(null);
            if (chaTagService.updateById(chaTag)) {
                return new ReturnRes(true, chaTag, "标签更新成功");
            }
        } else if (action.equals("remove")) {
            return chaTagService.removeTag(chaTag);
        } else {
            return new ReturnRes(false, "操作暂不支持");
        }
        return new ReturnRes(false, "操作发生错误");
    }

    @GetMapping("/admin/chaTagAndType")
    public ReturnRes getChaTagAndType() {
        List<ChaTag> list = chaTagMapper.selectAllTag();
        Map<String, List<List>>  map = new TreeMap<>();
        for (ChaTag chaTag : list) {
            if (map.containsKey(chaTag.getTname())) {
                map.get(chaTag.getTname()).add(Arrays.asList(chaTag.getTgid(), chaTag.getTgname()));
            } else {
                List<List> list1 = new ArrayList<>();
                list1.add(Arrays.asList(chaTag.getTgid(), chaTag.getTgname()));
                map.put(chaTag.getTname(), list1);
            }
        }

        return new ReturnRes(true, map);
    }

}
