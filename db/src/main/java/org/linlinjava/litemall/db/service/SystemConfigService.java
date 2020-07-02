package org.linlinjava.litemall.db.service;




import org.linlinjava.litemall.db.dao.SystemMapper;
import org.linlinjava.litemall.db.domain.System;
import org.linlinjava.litemall.db.domain.SystemExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemConfigService {
    @Resource
    private SystemMapper systemMapper;

    public Map<String, String> queryAll() {
        SystemExample example = new SystemExample();
        example.or().andDeletedEqualTo(false);

        List<System> systemList = systemMapper.selectByExample(example);
        Map<String, String> systemConfigs = new HashMap<>();
        for (System item : systemList) {
            systemConfigs.put(item.getKeyName(), item.getKeyValue());
        }

        return systemConfigs;
    }

    public Map<String, String> listMail() {
        SystemExample example = new SystemExample();
        example.or().andKeyNameLike("litemall_mall_%").andDeletedEqualTo(false);
        List<System> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(System system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public Map<String, String> listWx() {
        SystemExample example = new SystemExample();
        example.or().andKeyNameLike("litemall_wx_%").andDeletedEqualTo(false);
        List<System> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(System system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public Map<String, String> listOrder() {
        SystemExample example = new SystemExample();
        example.or().andKeyNameLike("litemall_order_%").andDeletedEqualTo(false);
        List<System> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(System system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public Map<String, String> listExpress() {
        SystemExample example = new SystemExample();
        example.or().andKeyNameLike("litemall_express_%").andDeletedEqualTo(false);
        List<System> systemList = systemMapper.selectByExample(example);
        Map<String, String> data = new HashMap<>();
        for(System system : systemList){
            data.put(system.getKeyName(), system.getKeyValue());
        }
        return data;
    }

    public void updateConfig(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            SystemExample example = new SystemExample();
            example.or().andKeyNameEqualTo(entry.getKey()).andDeletedEqualTo(false);

            System system = new System();
            system.setKeyName(entry.getKey());
            system.setKeyValue(entry.getValue());
            system.setUpdateTime(LocalDateTime.now());
            systemMapper.updateByExampleSelective(system, example);
        }

    }

    public void addConfig(String key, String value) {
        System system = new System();
        system.setKeyName(key);
        system.setKeyValue(value);
        system.setAddTime(LocalDateTime.now());
        system.setUpdateTime(LocalDateTime.now());
        systemMapper.insertSelective(system);
    }
}
