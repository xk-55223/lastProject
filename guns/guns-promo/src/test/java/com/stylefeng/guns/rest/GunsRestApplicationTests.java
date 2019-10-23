package com.stylefeng.guns.rest;

import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GunsRestApplicationTests {

	@Autowired
	MtimePromoMapper promoMapper;

	@Test
	public void contextLoads() {
		MtimePromo mtimePromo = new MtimePromo();
		mtimePromo.setCinemaId(1);
		MtimePromo mtimePromo1 = promoMapper.selectOne(mtimePromo);
		System.out.println(mtimePromo1);

	}

}
