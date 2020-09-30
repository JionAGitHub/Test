package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Spit>  findAll(){
        return spitDao.findAll();
    }

    public  Spit  findById(String id){
        return  spitDao.findById(id).get();
    }

    public  void save(Spit spit) {
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishdate(new Date());
        spit.setShare(0);
        spit.setVisits(0);
        spit.setThumbup(0);
        spit.setComment(0);
        spit.setState("1");
        
        if(spit.getParentid()!=null && !"".equals(spit.getParentid())){

            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }

        spitDao.save(spit);
    }

    public  void update(Spit spit) {
        spitDao.save(spit);
    }

    public  void  deleteById(String id) {
        spitDao.deleteById(id);
    }

    public Page<Spit>  findByParentid(String parentid, int page,int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return spitDao.findByParentid(parentid,pageable);
    }

    public void thumbup(String spitId) {
        //方法一
        Spit spit = spitDao.findById(spitId).get();
        spit.setThumbup((spit.getThumbup()==null ? 0 : spit.getThumbup())+1);
        spitDao.save(spit);
        //方式二
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");


    }
}
