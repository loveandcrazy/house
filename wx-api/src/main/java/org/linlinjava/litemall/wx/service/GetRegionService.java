package org.linlinjava.litemall.wx.service;

import org.linlinjava.litemall.db.domain.Region;
import org.linlinjava.litemall.db.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhy
 * @date 2019-01-17 23:07
 **/
@Component
public class GetRegionService {

	@Autowired
	private RegionService regionService;

	private static List<Region> regions;

	protected List<Region> getRegions() {
		if(regions==null){
			createRegion();
		}
		return regions;
	}

	private synchronized void createRegion(){
		if (regions == null) {
			regions = regionService.getAll();
		}
	}
}
