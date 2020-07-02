package org.linlinjava.litemall.db;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.db.dao.SystemMapper;
import org.linlinjava.litemall.db.domain.System;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperReturnTest {

    @Autowired
    private SystemMapper systemMapper;

    @Test
    public void test() {
        System system = new System();
        system.setKeyName("test-system-key");
        system.setKeyValue("test-system-value");
        int updates = systemMapper.insertSelective(system);
        Assert.assertEquals(updates, 1);

        updates = systemMapper.deleteByPrimaryKey(system.getId());
        Assert.assertEquals(updates, 1);

        updates = systemMapper.updateByPrimaryKey(system);
        Assert.assertEquals(updates, 0);
    }

}
