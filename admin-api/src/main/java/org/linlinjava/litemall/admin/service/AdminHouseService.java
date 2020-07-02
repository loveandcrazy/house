package org.linlinjava.litemall.admin.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.dto.HouseAllinone;
import org.linlinjava.litemall.admin.vo.CatVo;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.Category;
import org.linlinjava.litemall.db.domain.House;
import org.linlinjava.litemall.db.domain.HouseAttribute;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.linlinjava.litemall.admin.util.AdminResponseCode.GOODS_NAME_EXIST;

@Service
public class AdminHouseService {
    private final Log logger = LogFactory.getLog(AdminHouseService.class);

    @Autowired
    private HouseService houseService;

    @Autowired
    private HouseAttributeService attributeService;

    @Autowired
    private CategoryService categoryService;




    public Object list(Integer goodsId, String houseSn, String name,
                       Integer page, Integer limit, String sort, String order) {
        List<House> houseList = houseService.querySelective(goodsId, houseSn, name, page, limit, sort, order);
        return ResponseUtil.okList(houseList);
    }

    private Object validate(HouseAllinone houseAllinone) {
        House house = houseAllinone.getHouse();
        String name = house.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        int houseId = house.getId();
        if (StringUtils.isEmpty(houseId)) {
            return ResponseUtil.badArgument();
        }

        // 分类可以不设置，如果设置则需要验证分类存在
        Integer categoryId = house.getCategoryId();
        if (categoryId != null && categoryId != 0) {
            if (categoryService.findById(categoryId) == null) {
                return ResponseUtil.badArgumentValue();
            }
        }

        HouseAttribute[] attributes = houseAllinone.getAttributes();
        for (HouseAttribute attribute : attributes) {
            String attr = attribute.getAttribute();
            if (StringUtils.isEmpty(attr)) {
                return ResponseUtil.badArgument();
            }
            String value = attribute.getValue();
            if (StringUtils.isEmpty(value)) {
                return ResponseUtil.badArgument();
            }
        }

        return null;
    }

    /**
     * 编辑房源
     *
     * NOTE：
     *
     * （1）house表可以编辑字段；
     *
     * （4）house_attribute表支持编辑、添加和删除操作。

     *
     */
    @Transactional
    public Object update(HouseAllinone houseAllinone) {
        Object error = validate(houseAllinone);
        if (error != null) {
            return error;
        }

        House house = houseAllinone.getHouse();
        HouseAttribute[] attributes = houseAllinone.getAttributes();


//        //将生成的分享图片地址写入数据库
//        String url = qCodeService.createGoodShareImage(house.getId().toString(), house.getPicUrl(), house.getName());
//        house.setShareUrl(url);

        
        // 房源基本信息表litemall_goods
        if (houseService.updateById(house) == 0) {
            throw new RuntimeException("更新数据失败");
        }

        Integer gid = house.getId();



        // 房源参数表litemall_goods_attribute
        for (HouseAttribute attribute : attributes) {
            if (attribute.getId() == null || attribute.getId().equals(0)){
                attribute.setHouseId(house.getId());
                attributeService.add(attribute);
            }
            else if(attribute.getDeleted()){
                attributeService.deleteById(attribute.getId());
            }
            else if(attribute.getUpdateTime() == null){
                attributeService.updateById(attribute);
            }
        }

        return ResponseUtil.ok();
    }

    @Transactional
    public Object delete(House house) {
        Integer id = house.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }

        Integer gid = house.getId();
        houseService.deleteById(gid);
        attributeService.deleteByGid(gid);
        return ResponseUtil.ok();
    }

    @Transactional
    public Object create(HouseAllinone houseAllinone) {
        Object error = validate(houseAllinone);
        if (error != null) {
            return error;
        }

        House house = houseAllinone.getHouse();
        HouseAttribute[] attributes = houseAllinone.getAttributes();

        String name = house.getName();
        if (houseService.checkExistByName(name)) {
            return ResponseUtil.fail(GOODS_NAME_EXIST, "商品名已经存在");
        }


        // 房源基本信息表house
        houseService.add(house);

//        //将生成的分享图片地址写入数据库
//        String url = qCodeService.createGoodShareImage(house.getId().toString(), house.getPicUrl(), house.getName());
//        if (!StringUtils.isEmpty(url)) {
//            house.setShareUrl(url);
//            if (houseService.updateById(house) == 0) {
//                throw new RuntimeException("更新数据失败");
//            }
//        }



        // 房源参数表house_attribute
        for (HouseAttribute attribute : attributes) {
            attribute.setHouseId(house.getId());
            attributeService.add(attribute);
        }


        return ResponseUtil.ok();
    }

    public Object list2() {
        // http://element-cn.eleme.io/#/zh-CN/component/cascader
        // 管理员设置“所属分类”
        List<Category> l1CatList = categoryService.queryL1();
        List<CatVo> categoryList = new ArrayList<>(l1CatList.size());

        for (Category l1 : l1CatList) {
            CatVo l1CatVo = new CatVo();
            l1CatVo.setValue(l1.getId());
            l1CatVo.setLabel(l1.getName());

            List<Category> l2CatList = categoryService.queryByPid(l1.getId());
            List<CatVo> children = new ArrayList<>(l2CatList.size());
            for (Category l2 : l2CatList) {
                CatVo l2CatVo = new CatVo();
                l2CatVo.setValue(l2.getId());
                l2CatVo.setLabel(l2.getName());
                children.add(l2CatVo);
            }
            l1CatVo.setChildren(children);

            categoryList.add(l1CatVo);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("categoryList", categoryList);

        return ResponseUtil.ok(data);
    }

    public Object detail(Integer id) {
        House house = houseService.findById(id);
        List<HouseAttribute> attributes = attributeService.queryByGid(id);

        Integer categoryId = house.getCategoryId();
        Category category = categoryService.findById(categoryId);
        Integer[] categoryIds = new Integer[]{};
        if (category != null) {
            Integer parentCategoryId = category.getPid();
            categoryIds = new Integer[]{parentCategoryId, categoryId};
        }

        Map<String, Object> data = new HashMap<>();
        data.put("house", house);

        data.put("attributes", attributes);
        data.put("categoryIds", categoryIds);

        return ResponseUtil.ok(data);
    }

}
