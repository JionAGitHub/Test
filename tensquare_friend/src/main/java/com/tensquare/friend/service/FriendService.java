package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao   noFriendDao;

    public int addFriend(String userid, String friendid) {
        //先判断从userid到friendid是否有数据，有就是重复添加好友
        Friend friend = friendDao.findByFriendidAndUserid(friendid,userid);
        if(friend!=null) {
            return 0;
        }
        //直接添加好友，让好友表中userid到friendid的方向type为0
        friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //判断从userid到friendid是否有数据，如果有，把双方的状态改为1
        if(friendDao.findByFriendidAndUserid(friendid,userid)==null){
            friendDao.updateislike("1",userid,friendid);
            friendDao.updateislike("1",friendid,userid);

        }
        return 1;
    }

    public int addNoFriend(String userid, String friendid) {
        NoFriend noFriend = noFriendDao.findByFriendidAndUserid(userid, friendid);
        if(noFriend!=null) {
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;
    }

    public void deleteFriend(String userid, String friendid) {
        //删除好友表中userid 到friendid这条数据
        friendDao.deleteFriend(userid,friendid);
        //更新friendid到userid的islike 为0
        friendDao.updateislike("0",userid,friendid);
        //非好友表添加数据
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
}
