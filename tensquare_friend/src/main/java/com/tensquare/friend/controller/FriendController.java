package com.tensquare.friend.controller;


import com.mysql.cj.x.protobuf.Mysqlx;
import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest  request;

    @Autowired
    private UserClient  userClient;

    /**
     * 添加好友或添加非好友
     */

    public Result  addFriend(@PathVariable String friendid ,@PathVariable String type) {
        Claims claims  = (Claims) request.getAttribute("claims_user");
        if(claims==null) {
            return  new Result(false, StatusCode.LOGINERROR,"权限不足");
        }
        String userid = claims.getId();
        //判断是否添加好友还是添加非好友
        if(type!=null){
            if(type=="1") {
                //添加好友
                int flag = friendService.addFriend(userid,friendid);
                if(flag == 0) {
                    return new Result(false,StatusCode.ERROR,"不能重复添加好友");
                }
                if(flag ==1) {
                    userClient.updatefanscountandfollowcount(userid,friendid,1);
                    return  new Result(true,StatusCode.OK,"添加成功");
                }
            }else if(type=="2") {
                //添加非好友
                int flag = friendService.addNoFriend(userid,friendid);
                if(flag == 0) {
                    return new Result(false,StatusCode.ERROR,"不能重复添加非好友");
                }
                if(flag ==1) {
                    return  new Result(true,StatusCode.OK,"添加成功");
                }
            }
            return new Result(false, StatusCode.ERROR,"参数异常");
        }else {
            return new Result(false, StatusCode.ERROR,"参数异常");
        }
    }

    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public  Result  deleteFriend(@PathVariable String friendid) {
        Claims claims  = (Claims) request.getAttribute("claims_user");
        if(claims==null) {
            return  new Result(false, StatusCode.LOGINERROR,"权限不足");
        }
        String userid = claims.getId();
        friendService.deleteFriend(userid,friendid);
        userClient.updatefanscountandfollowcount(userid,friendid,-1);
        return  new Result(true,StatusCode.OK,"删除成功");
    }
}
