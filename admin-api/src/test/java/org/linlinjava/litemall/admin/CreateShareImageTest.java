package org.linlinjava.litemall.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.core.qcode.QCodeService;
import org.linlinjava.litemall.db.domain.House;
import org.linlinjava.litemall.db.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CreateShareImageTest {
    @Autowired
    QCodeService qCodeService;
    @Autowired
    HouseService houseService;


    @Test
    public void test() {
        House house = houseService.findById(1181010);
        qCodeService.createGoodShareImage(house.getId().toString(), house.getPicUrl(), house.getName());
    }
}
