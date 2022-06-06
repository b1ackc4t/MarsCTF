package com.b1ackc4t.marsctfserver.controller;

import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.DockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class DockerController {
    final DockerService dockerService;

    @Autowired
    public DockerController(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    @PostMapping("/user/container/create/{cid:\\d+}")
    public ReturnRes createContainer(@PathVariable Integer cid,
                                    Authentication authentication) throws IOException, InterruptedException {
        Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
        return dockerService.createContainerForUser(cid, uid);
    }

    @GetMapping("/user/container/{cid:\\d+}")
    public ReturnRes getContainerForUser(@PathVariable Integer cid,
                                         Authentication authentication) {
        Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
        return dockerService.getContainerByCidUid(cid, uid);
    }

    @PostMapping("/user/container/destroy/{cid:\\d+}")
    public ReturnRes destroyContainer(@PathVariable Integer cid,
                                     Authentication authentication) throws IOException, InterruptedException {
        Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
        return dockerService.removeContainerByCidUid(cid, uid);
    }

    @GetMapping("/admin/container/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getContainerForUser(@PathVariable int pageSize,
                                         @PathVariable int pageNum) {
        return dockerService.getContainerByPage(pageNum, pageSize);
    }

    @PostMapping("/admin/container/addTime/{uid:\\d+}/{cid:\\d+}")
    public ReturnRes addTimeContainer(@PathVariable Integer uid,
                                      @PathVariable Integer cid,
                                      @RequestParam Integer second) {
        return dockerService.addTimeForAdmin(uid, cid, second);
    }

    @PostMapping("/admin/container/destroy/{uid:\\d+}/{cid:\\d+}")
    public ReturnRes destroyContainerForAdmin(@PathVariable Integer uid,
                                              @PathVariable Integer cid) throws IOException {
        return dockerService.removeContainerByCidUid(cid, uid);
    }

    @PostMapping("/user/container/addTime/{cid:\\d+}")
    public ReturnRes addTimeContainerForUser(@PathVariable Integer cid,
                                             Authentication authentication) {
        Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
        return dockerService.addTimeForUser(uid, cid);
    }

    @GetMapping("/admin/container/search/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getContainerForUser(@PathVariable int pageSize,
                                         @PathVariable int pageNum,
                                         @RequestParam String key,
                                         @RequestParam String value) {
        return dockerService.searchContainerByPage(key, value, pageNum, pageSize);
    }

    @GetMapping("/admin/container/check")
    public ReturnRes check() {
        return dockerService.check();
    }
}
