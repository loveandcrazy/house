package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.validator.Contract;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.House;

import org.linlinjava.litemall.wx.dto.HouseAllinone;
import org.linlinjava.litemall.wx.service.WxHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * 用户添加房源服务
 */
@RestController
@RequestMapping("/wx/houseadd")
@Validated
public class WxHouseAddController {
    private final Log logger = LogFactory.getLog(WxHouseAddController.class);

    @Autowired
    private WxHouseService wxHouseService;

    /**
     * 查询房源
     *
     * @param houseId
     * @param houseSn
     * @param name
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */

    @GetMapping("/list")
    public Object list(Integer houseId, String houseSn, String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Contract @RequestParam(defaultValue = "desc") String order) {
        return wxHouseService.list(houseId, houseSn, name, page, limit, sort, order);
    }


    /**
     * 编辑房源
     *
     * @param houseAllinone
     * @return
     */
    @PostMapping("/update")
    public Object update(@RequestBody HouseAllinone houseAllinone) {
        return wxHouseService.update(houseAllinone);
    }

    /**
     * 删除房源
     *
     * @param house
     * @return
     */

    @PostMapping("/delete")
    public Object delete(@RequestBody House house) {
        return wxHouseService.delete(house);
    }

    /**
     * 添加房源
     *
     * @param houseAllinone
     * @return
     */

    @PostMapping("/create")
    public Object create(@RequestBody HouseAllinone houseAllinone) {
        return wxHouseService.create(houseAllinone);
    }

    /**
     * 房源详情
     *
     * @param id
     * @return
     */

    @GetMapping("/detail")
    public Object detail(@NotNull Integer id) {
        return wxHouseService.detail(id);

    }
}