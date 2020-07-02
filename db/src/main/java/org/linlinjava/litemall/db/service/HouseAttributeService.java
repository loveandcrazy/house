package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.HouseAttributeMapper;
import org.linlinjava.litemall.db.domain.HouseAttribute;
import org.linlinjava.litemall.db.domain.HouseAttributeExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HouseAttributeService {
    @Resource
    private HouseAttributeMapper houseAttributeMapper;

    public List<HouseAttribute> queryByGid(Integer houseId) {
        HouseAttributeExample example = new HouseAttributeExample();
        example.or().andHouseIdEqualTo(houseId).andDeletedEqualTo(false);
        return houseAttributeMapper.selectByExample(example);
    }

    public void add(HouseAttribute houseAttribute) {
        houseAttribute.setAddTime(LocalDateTime.now());
        houseAttribute.setUpdateTime(LocalDateTime.now());
        houseAttributeMapper.insertSelective(houseAttribute);
    }

    public HouseAttribute findById(Integer id) {
        return houseAttributeMapper.selectByPrimaryKey(id);
    }

    public void deleteByGid(Integer gid) {
        HouseAttributeExample example = new HouseAttributeExample();
        example.or().andHouseIdEqualTo(gid);
        houseAttributeMapper.logicalDeleteByExample(example);
    }

    public void deleteById(Integer id) {
        houseAttributeMapper.logicalDeleteByPrimaryKey(id);
    }

    public void updateById(HouseAttribute attribute) {
        attribute.setUpdateTime(LocalDateTime.now());
        houseAttributeMapper.updateByPrimaryKeySelective(attribute);
    }
}
