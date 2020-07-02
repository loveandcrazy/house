package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.RegionMapper;


import org.linlinjava.litemall.db.domain.Region;
import org.linlinjava.litemall.db.domain.RegionExample;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RegionService {

    @Resource
    private RegionMapper regionMapper;

    public List<Region> getAll(){
        RegionExample example = new RegionExample();
        byte b = 4;
        example.or().andTypeNotEqualTo(b);
        return regionMapper.selectByExample(example);
    }

    public List<Region> queryByPid(Integer parentId) {
        RegionExample example = new RegionExample();
        example.or().andPidEqualTo(parentId);
        return regionMapper.selectByExample(example);
    }

    public Region findById(Integer id) {
        return regionMapper.selectByPrimaryKey(id);
    }

    public List<Region> querySelective(String name, Integer code, Integer page, Integer size, String sort, String order) {
        RegionExample example = new RegionExample();
        RegionExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(code)) {
            criteria.andCodeEqualTo(code);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return regionMapper.selectByExample(example);
    }

}
