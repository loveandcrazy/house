package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.AdminContractService;

import org.linlinjava.litemall.core.validator.Contract;
import org.linlinjava.litemall.core.validator.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/contract")
@Validated
public class AdminContractController {
    private final Log logger = LogFactory.getLog(AdminContractController.class);

    @Autowired
    private AdminContractService adminContractService;


    /**
     * 查询合同
     *
     * @param userId
     * @param orderSn
     * @param orderStatusArray
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:order:list")
    @RequiresPermissionsDesc(menu = {"平台管理", "合同管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(Integer userId, String orderSn,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                       @RequestParam(required = false) List<Short> orderStatusArray,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Contract @RequestParam(defaultValue = "desc") String order) {
        return adminContractService.list(userId, orderSn, start, end, orderStatusArray, page, limit, sort, order);
    }


    /**
     * 合同详情
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:contract:read")
    @RequiresPermissionsDesc(menu = {"平台管理", "合同管理"}, button = "详情")
    @GetMapping("/detail")
    public Object detail(@NotNull Integer id) {
        return adminContractService.detail(id);
    }


    /**
     * 删除合同
     *
     * @param body 合同信息，{ contractId：xxx }
     * @return 合同操作结果
     */
    @RequiresPermissions("admin:contract:delete")
    @RequiresPermissionsDesc(menu = {"平台管理", "合同管理"}, button = "合同删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody String body) {
        return adminContractService.delete(body);
    }

    /**
     * 回复合同评价
     *
     * @param body 合同信息，{ contractId：xxx }
     * @return 合同操作结果
     */
    @RequiresPermissions("admin:contract:reply")
    @RequiresPermissionsDesc(menu = {"平台管理", "合同管理"}, button = "合同评价回复")
    @PostMapping("/reply")
    public Object reply(@RequestBody String body) {
        return adminContractService.reply(body);
    }
}
