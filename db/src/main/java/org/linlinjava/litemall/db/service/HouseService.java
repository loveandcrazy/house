package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.HouseMapper;


import org.linlinjava.litemall.db.domain.House;
import org.linlinjava.litemall.db.domain.House.Column;
import org.linlinjava.litemall.db.domain.HouseExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class HouseService {
    Column[] columns = new Column[]{Column.id, Column.name, Column.brief, Column.picUrl, Column.isHot, Column.isNew, Column.price, Column.province, Column.city, Column.county ,Column.addressDetail};
    @Resource
    private HouseMapper houseMapper;

    /**
     * 获取热卖房源
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<House> queryByHot(int offset, int limit) {
        HouseExample example = new HouseExample();
        example.or().andIsHotEqualTo(true).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return houseMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 获取新品上市
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<House> queryByNew(int offset, int limit) {
        HouseExample example = new HouseExample();
        example.or().andIsNewEqualTo(true).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return houseMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 获取省
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<House> queryByProvince(String province,int offset, int limit) {
        HouseExample example = new HouseExample();
        example.or().andProvinceLike(province).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return houseMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 获取市
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<House> queryByCity(String city,int offset, int limit) {
        HouseExample example = new HouseExample();
        example.or().andCityLike(city).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return houseMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 获取县
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<House> queryByCounty(String county,int offset, int limit) {
        HouseExample example = new HouseExample();
        example.or().andCountyLike(county).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return houseMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 获取详细地址
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<House> queryByDetailAddress(String detail_address,int offset, int limit) {
        HouseExample example = new HouseExample();
        example.or().andAddressDetailLike(detail_address).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return houseMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 获取分类下的房源
     *
     * @param catList
     * @param offset
     * @param limit
     * @return
     */
    public List<House> queryByCategory(List<Integer> catList, int offset, int limit) {
        HouseExample example = new HouseExample();
        example.or().andCategoryIdIn(catList).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time  desc");
        PageHelper.startPage(offset, limit);

        return houseMapper.selectByExampleSelective(example, columns);
    }


    /**
     * 获取分类下的房源
     *
     * @param catId
     * @param offset
     * @param limit
     * @return
     */
    public List<House> queryByCategory(Integer catId, int offset, int limit) {
        HouseExample example = new HouseExample();
        example.or().andCategoryIdEqualTo(catId).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return houseMapper.selectByExampleSelective(example, columns);
    }


    public List<House> querySelective(Integer catId, String keywords, Boolean isHot, Boolean isNew, Integer offset, Integer limit, String sort, String order,
                                      String province,String city,String  county,String address_detail) {
        HouseExample example = new HouseExample();
        HouseExample.Criteria criteria1 = example.or();
        HouseExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(catId) && catId != 0) {
            criteria1.andCategoryIdEqualTo(catId);
            criteria2.andCategoryIdEqualTo(catId);
        }
        if (!StringUtils.isEmpty(isNew)) {
            criteria1.andIsNewEqualTo(isNew);
            criteria2.andIsNewEqualTo(isNew);
        }
        if (!StringUtils.isEmpty(isHot)) {
            criteria1.andIsHotEqualTo(isHot);
            criteria2.andIsHotEqualTo(isHot);
        }
        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        if (!StringUtils.isEmpty(province)) {
            criteria1.andProvinceLike(province);
            criteria2.andProvinceLike(province);
        }
        if (!StringUtils.isEmpty(city)) {
            criteria1.andCityLike(city);
            criteria2.andCityLike(city);
        }
        if (!StringUtils.isEmpty(county)) {
            criteria1.andCountyLike(county);
            criteria2.andCountyLike(county);
        }
        if (!StringUtils.isEmpty(address_detail)) {
            criteria1.andAddressDetailLike(address_detail);
            criteria2.andAddressDetailLike(address_detail);
        }


        criteria1.andIsOnSaleEqualTo(true);
        criteria2.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(offset, limit);

        return houseMapper.selectByExampleSelective(example, columns);
    }

    public List<House> querySelective(Integer houseId, String houseSn, String name, Integer page, Integer size, String sort, String order) {
        HouseExample example = new HouseExample();
        HouseExample.Criteria criteria = example.createCriteria();

        if (houseId != null) {
            criteria.andIdEqualTo(houseId);
        }

        if (!StringUtils.isEmpty(houseSn)) {
            criteria.andHouseSnEqualTo(houseSn);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return houseMapper.selectByExampleWithBLOBs(example);
    }

    /**
     * 获取某个房源信息,包含完整信息
     *
     * @param id
     * @return
     */
    public House findById(Integer id) {
        HouseExample example = new HouseExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return houseMapper.selectOneByExampleWithBLOBs(example);
    }

    /**
     * 获取某个房源信息，仅展示相关内容
     *
     * @param id
     * @return
     */
    public House findByIdVO(Integer id) {
        HouseExample example = new HouseExample();
        example.or().andIdEqualTo(id).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return houseMapper.selectOneByExampleSelective(example, columns);
    }


    /**
     * 获取所有在售房源总数
     *
     * @return
     */
    public Integer queryOnSale() {
        HouseExample example = new HouseExample();
        example.or().andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return (int) houseMapper.countByExample(example);
    }

    public int updateById(House house) {
        house.setUpdateTime(LocalDateTime.now());
        return houseMapper.updateByPrimaryKeySelective(house);
    }

    public void deleteById(Integer id) {
        houseMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(House house) {
        house.setAddTime(LocalDateTime.now());
        house.setUpdateTime(LocalDateTime.now());
        houseMapper.insertSelective(house);
    }

    /**
     * 获取所有房源总数，包括在售的和下架的，但是不包括已删除的房源
     *
     * @return
     */
    public int count() {
        HouseExample example = new HouseExample();
        example.or().andDeletedEqualTo(false);
        return (int) houseMapper.countByExample(example);
    }

    public List<Integer> getCatIds( String keywords, Boolean isHot, Boolean isNew) {
        HouseExample example = new HouseExample();
        HouseExample.Criteria criteria1 = example.or();
        HouseExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(isNew)) {
            criteria1.andIsNewEqualTo(isNew);
            criteria2.andIsNewEqualTo(isNew);
        }
        if (!StringUtils.isEmpty(isHot)) {
            criteria1.andIsHotEqualTo(isHot);
            criteria2.andIsHotEqualTo(isHot);
        }
        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andIsOnSaleEqualTo(true);
        criteria2.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        List<House> houseList = houseMapper.selectByExampleSelective(example, Column.categoryId);
        List<Integer> cats = new ArrayList<Integer>();
        for (House house : houseList) {
            cats.add(house.getCategoryId());
        }
        return cats;
    }

    public boolean checkExistByName(String name) {
        HouseExample example = new HouseExample();
        example.or().andNameEqualTo(name).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return houseMapper.countByExample(example) != 0;
    }

    public List<House> queryByIds(Integer[] ids) {
        HouseExample example = new HouseExample();
        example.or().andIdIn(Arrays.asList(ids)).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return houseMapper.selectByExampleSelective(example, columns);
    }
}
