package com.tensquare.spit.controller;


import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public Result  findAll(){
        return  new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    @RequestMapping(value ="/{spitId}",method = RequestMethod.POST)
    public Result  findById(@PathVariable String spitId){
        return  new Result(true, StatusCode.OK,"查询成功",spitService.findById(spitId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public  Result  save(@RequestBody Spit spit) {
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"保存成功");
    }

    @RequestMapping(value = "/{spitId}",method = RequestMethod.PUT)
    public Result  update(@PathVariable String spitId,@RequestBody Spit spit) {
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @RequestMapping(value = "/{spitId}",method = RequestMethod.DELETE)
    public Result  deleteById(@PathVariable String spitId) {
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public  Result findByParentid(@PathVariable String parentid,@PathVariable int page ,@PathVariable int size) {
        Page<Spit> pageData = spitService.findByParentid(parentid,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(pageData.getTotalElements(),pageData.getContent()));
    }

    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public  Result  thumbup(String spitId) {
        String userid = "111";
        if(redisTemplate.opsForValue().get("thumbup_"+userid) !=null){
            return new Result(false,StatusCode.REMOTEERROR,"不能重复操作");
        }
        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userid, 1);
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}