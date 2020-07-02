package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.dto.HouseAllinone;
import org.linlinjava.litemall.admin.service.AdminHouseService;
import org.linlinjava.litemall.core.validator.Contract;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/admin/house")
@Validated
public class AdminHouseController {
    private final Log logger = LogFactory.getLog(AdminHouseController.class);

    @Autowired
    private AdminHouseService adminHouseService;

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
    @RequiresPermissions("admin:house:list")
    @RequiresPermissionsDesc(menu = {"房源管理", "房源管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(Integer houseId, String houseSn, String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Contract @RequestParam(defaultValue = "desc") String order) {
        return adminHouseService.list(houseId, houseSn, name, page, limit, sort, order);
    }

    @GetMapping("/catAndBrand")
    public Object list2() {
        return adminHouseService.list2();
    }

    /**
     * 编辑房源
     *
     * @param houseAllinone
     * @return
     */
    @RequiresPermissions("admin:house:update")
    @RequiresPermissionsDesc(menu = {"房源管理", "房源管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody HouseAllinone houseAllinone) {
        return adminHouseService.update(houseAllinone);
    }

    /**
     * 删除房源
     *
     * @param house
     * @return
     */
    @RequiresPermissions("admin:house:delete")
    @RequiresPermissionsDesc(menu = {"房源管理", "房源管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody House house) {
        return adminHouseService.delete(house);
    }

    /**
     * 添加房源
     *
     * @param houseAllinone
     * @return
     */
    @RequiresPermissions("admin:house:create")
    @RequiresPermissionsDesc(menu = {"房源管理", "房源管理"}, button = "上架")
    @PostMapping("/create")
    public Object create(@RequestBody HouseAllinone houseAllinone) {
        return adminHouseService.create(houseAllinone);
    }

    /**
     * 房源详情
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:house:read")
    @RequiresPermissionsDesc(menu = {"房源管理", "房源管理"}, button = "详情")
    @GetMapping("/detail")
    public Object detail(@NotNull Integer id) {
        return adminHouseService.detail(id);

    }

}
