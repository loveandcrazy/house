package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.AddressMapper;


import org.linlinjava.litemall.db.domain.Address;
import org.linlinjava.litemall.db.domain.AddressExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AddressService {
    @Resource
    private AddressMapper addressMapper;

//    public List<Address> queryByUid(Integer uid) {
//        AddressExample example = new AddressExample();
//        example.or().andHouseIdEqualTo(uid).andDeletedEqualTo(false);
//        return addressMapper.selectByExample(example);
//    }
//
//    public Address query(Integer userId, Integer id) {
//        AddressExample example = new AddressExample();
//        example.or().andIdEqualTo(id).andHouseIdEqualTo(userId).andDeletedEqualTo(false);
//        return addressMapper.selectOneByExample(example);
//    }
//
//    public int add(Address address) {
//        address.setAddTime(LocalDateTime.now());
//        address.setUpdateTime(LocalDateTime.now());
//        return addressMapper.insertSelective(address);
//    }
//
//    public int update(Address address) {
//        address.setUpdateTime(LocalDateTime.now());
//        return addressMapper.updateByPrimaryKeySelective(address);
//    }
//
//    public void delete(Integer id) {
//        addressMapper.logicalDeleteByPrimaryKey(id);
//    }
//
//    public Address findDefault(Integer userId) {
//        AddressExample example = new AddressExample();
//        example.or().andHouseIdEqualTo(userId).andIsDefaultEqualTo(true).andDeletedEqualTo(false);
//        return addressMapper.selectOneByExample(example);
//    }
//
//    public void resetDefault(Integer userId) {
//        Address address = new Address();
//        address.setIsDefault(false);
//        address.setUpdateTime(LocalDateTime.now());
//        AddressExample example = new AddressExample();
//        example.or().andHouseIdEqualTo(userId).andDeletedEqualTo(false);
//        addressMapper.updateByExampleSelective(address, example);
//    }
//
//    public List<Address> querySelective(Integer userId, String name, Integer page, Integer limit, String sort, String order) {
//        AddressExample example = new AddressExample();
//        AddressExample.Criteria criteria = example.createCriteria();
//
//        if (userId != null) {
//            criteria.andHouseIdEqualTo(userId);
//        }
//        if (!StringUtils.isEmpty(name)) {
//            criteria.andNameLike("%" + name + "%");
//        }
//        criteria.andDeletedEqualTo(false);
//
//        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
//            example.setOrderByClause(sort + " " + order);
//        }
//
//        PageHelper.startPage(page, limit);
//        return addressMapper.selectByExample(example);
//    }
}
