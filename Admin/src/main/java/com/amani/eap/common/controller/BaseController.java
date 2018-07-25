package com.amani.eap.common.controller;

import com.amani.eap.common.constant.BaseConstants;
import com.amani.eap.common.entity.TableResultEntity;
import com.amani.eap.common.response.ResponseMessage;
import com.amani.eap.common.service.BaseService;
import com.amani.eap.common.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class BaseController<Service extends BaseService, Entity> {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected Service baseService;

    @PostMapping("add")
    public ResponseMessage<Entity> add(@RequestBody Entity entity) {
        baseService.insertSelective(entity);
        return new ResponseMessage<Entity>().success();
    }

    @GetMapping("get/{id}")
    public ResponseMessage<Entity> get(@PathVariable String id) {
        return new ResponseMessage<>().success(baseService.selectById(id));
    }

    @PutMapping("put")
    public ResponseMessage<Entity> update(@RequestBody Entity entity) {
        baseService.updateById(entity);
        return new ResponseMessage<Entity>().success();
    }

    @PutMapping("put/selective")
    public ResponseMessage<Entity> updateSelective(@RequestBody Entity entity) {
        baseService.updateSelectiveById(entity);
        return new ResponseMessage<Entity>().success();
    }

    @DeleteMapping("delete/{id}")
    public ResponseMessage<Entity> remove(@PathVariable String id) {
        baseService.deleteById(id);
        return new ResponseMessage<Entity>().success();
    }

    @GetMapping("all")
    public ResponseMessage<List<Entity>> all() {
        return new ResponseMessage<>().success(baseService.selectListAll());
    }

    @GetMapping("page")
    public ResponseMessage<TableResultEntity<Entity>> page(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return new ResponseMessage().success(baseService.selectByQuery(query));
    }

    protected String getCurrentUserName() {
        String authorization = request.getHeader(BaseConstants.REQUEST_HEADER_AUTH);
        return new String(Base64Utils.decodeFromString(authorization));

    }

    protected String getCurrentUserId() {
        return request.getHeader(BaseConstants.REQUEST_HEADER_UID);
    }
}
