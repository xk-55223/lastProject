package com.stylefeng.guns.rest;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.stylefeng.guns.rest.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.rest.order.bean.FieldSeatsInfoVO;
import com.stylefeng.guns.rest.order.vo.OrderVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GunsOrderApplication.class)
public class GunsOrderApplicationTests {
	@Autowired
	RedisTemplate redisTemplate;

	@Autowired
	private MoocOrderTMapper orderTMapper;
	@Test
	public void contextLoads() {

		Gson gson = new Gson();
		FieldSeatsInfoVO field4d = gson.fromJson("{\"limit\":5,\"ids\":\"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42\",\"single\":[[{\"seatId\":1,\"row\":1,\"column\":1},{\"seatId\":2,\"row\":1,\"column\":2},{\"seatId\":3,\"row\":1,\"column\":3},{\"seatId\":4,\"row\":1,\"column\":4},{\"seatId\":5,\"row\":1,\"column\":5},{\"seatId\":6,\"row\":1,\"column\":6},{\"seatId\":7,\"row\":1,\"column\":7}],[{\"seatId\":8,\"row\":2,\"column\":1},{\"seatId\":9,\"row\":2,\"column\":2},{\"seatId\":10,\"row\":2,\"column\":3},{\"seatId\":11,\"row\":2,\"column\":4},{\"seatId\":12,\"row\":2,\"column\":5},{\"seatId\":13,\"row\":2,\"column\":6},{\"seatId\":14,\"row\":2,\"column\":7}],[{\"seatId\":15,\"row\":3,\"column\":1},{\"seatId\":16,\"row\":3,\"column\":2},{\"seatId\":17,\"row\":3,\"column\":3},{\"seatId\":18,\"row\":3,\"column\":4},{\"seatId\":19,\"row\":3,\"column\":5},{\"seatId\":20,\"row\":3,\"column\":6},{\"seatId\":21,\"row\":3,\"column\":7}],[{\"seatId\":22,\"row\":4,\"column\":1},{\"seatId\":23,\"row\":4,\"column\":2},{\"seatId\":24,\"row\":4,\"column\":3},{\"seatId\":25,\"row\":4,\"column\":4},{\"seatId\":26,\"row\":4,\"column\":5},{\"seatId\":27,\"row\":4,\"column\":6},{\"seatId\":28,\"row\":4,\"column\":7}],[{\"seatId\":29,\"row\":5,\"column\":1},{\"seatId\":30,\"row\":5,\"column\":2},{\"seatId\":31,\"row\":5,\"column\":3},{\"seatId\":32,\"row\":5,\"column\":4},{\"seatId\":33,\"row\":5,\"column\":5},{\"seatId\":34,\"row\":5,\"column\":6},{\"seatId\":35,\"row\":5,\"column\":7}],[{\"seatId\":36,\"row\":6,\"column\":1},{\"seatId\":37,\"row\":6,\"column\":2},{\"seatId\":38,\"row\":6,\"column\":3},{\"seatId\":39,\"row\":6,\"column\":4},{\"seatId\":40,\"row\":6,\"column\":5},{\"seatId\":41,\"row\":6,\"column\":6},{\"seatId\":42,\"row\":6,\"column\":7}]]}", FieldSeatsInfoVO.class);
		FieldSeatsInfoVO field4dx = gson.fromJson("{\"limit\":5,\"ids\":\"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22\",\"single\":[[{\"seatId\":1,\"row\":1,\"column\":1},{\"seatId\":2,\"row\":1,\"column\":2},{\"seatId\":3,\"row\":1,\"column\":3},{\"seatId\":4,\"row\":1,\"column\":4},{\"seatId\":5,\"row\":1,\"column\":5}],[{\"seatId\":6,\"row\":2,\"column\":1},{\"seatId\":7,\"row\":2,\"column\":2},{\"seatId\":8,\"row\":2,\"column\":3},{\"seatId\":9,\"row\":2,\"column\":4},{\"seatId\":10,\"row\":2,\"column\":5}]],\"couple\":[[{\"seatId\":11,\"row\":3,\"column\":1},{\"seatId\":12,\"row\":3,\"column\":2},{\"seatId\":13,\"row\":3,\"column\":3},{\"seatId\":14,\"row\":3,\"column\":4},{\"seatId\":15,\"row\":3,\"column\":5},{\"seatId\":16,\"row\":3,\"column\":6}],[{\"seatId\":17,\"row\":4,\"column\":1},{\"seatId\":18,\"row\":4,\"column\":2},{\"seatId\":19,\"row\":4,\"column\":3},{\"seatId\":20,\"row\":4,\"column\":4},{\"seatId\":21,\"row\":4,\"column\":5},{\"seatId\":22,\"row\":4,\"column\":6}]]}", FieldSeatsInfoVO.class);
		FieldSeatsInfoVO field4k = gson.fromJson("{\"limit\":5,\"ids\":\"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40\",\"single\":[[{\"seatId\":1,\"row\":1,\"column\":1},{\"seatId\":2,\"row\":1,\"column\":2},{\"seatId\":3,\"row\":1,\"column\":3},{\"seatId\":4,\"row\":1,\"column\":4},{\"seatId\":5,\"row\":1,\"column\":5},{\"seatId\":6,\"row\":1,\"column\":6},{\"seatId\":7,\"row\":1,\"column\":7},{\"seatId\":8,\"row\":1,\"column\":8}],[{\"seatId\":9,\"row\":2,\"column\":1},{\"seatId\":10,\"row\":2,\"column\":2},{\"seatId\":11,\"row\":2,\"column\":3},{\"seatId\":12,\"row\":2,\"column\":4},{\"seatId\":13,\"row\":2,\"column\":5},{\"seatId\":14,\"row\":2,\"column\":6},{\"seatId\":15,\"row\":2,\"column\":7},{\"seatId\":16,\"row\":2,\"column\":8}],[{\"seatId\":17,\"row\":3,\"column\":1},{\"seatId\":18,\"row\":3,\"column\":2},{\"seatId\":19,\"row\":3,\"column\":3},{\"seatId\":20,\"row\":3,\"column\":4},{\"seatId\":21,\"row\":3,\"column\":5},{\"seatId\":22,\"row\":3,\"column\":6},{\"seatId\":23,\"row\":3,\"column\":7},{\"seatId\":24,\"row\":3,\"column\":8}],[{\"seatId\":25,\"row\":4,\"column\":1},{\"seatId\":26,\"row\":4,\"column\":2},{\"seatId\":27,\"row\":4,\"column\":3},{\"seatId\":28,\"row\":4,\"column\":4},{\"seatId\":29,\"row\":4,\"column\":5},{\"seatId\":30,\"row\":4,\"column\":6},{\"seatId\":31,\"row\":4,\"column\":7},{\"seatId\":32,\"row\":4,\"column\":8}],[{\"seatId\":33,\"row\":5,\"column\":1},{\"seatId\":34,\"row\":5,\"column\":2},{\"seatId\":35,\"row\":5,\"column\":3},{\"seatId\":36,\"row\":5,\"column\":4},{\"seatId\":37,\"row\":5,\"column\":5},{\"seatId\":38,\"row\":5,\"column\":6},{\"seatId\":39,\"row\":5,\"column\":7},{\"seatId\":40,\"row\":5,\"column\":8}]]}", FieldSeatsInfoVO.class);
		FieldSeatsInfoVO fieldcgs = gson.fromJson("{\"limit\":5,\"ids\":\"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24\",\"single\":[[{\"seatId\":1,\"row\":1,\"column\":1},{\"seatId\":2,\"row\":1,\"column\":2},{\"seatId\":3,\"row\":1,\"column\":3},{\"seatId\":4,\"row\":1,\"column\":4},{\"seatId\":5,\"row\":1,\"column\":5},{\"seatId\":6,\"row\":1,\"column\":6}],[{\"seatId\":7,\"row\":2,\"column\":1},{\"seatId\":8,\"row\":2,\"column\":2},{\"seatId\":9,\"row\":2,\"column\":3},{\"seatId\":10,\"row\":2,\"column\":4},{\"seatId\":11,\"row\":2,\"column\":5},{\"seatId\":12,\"row\":2,\"column\":6}]],\"couple\":[[{\"seatId\":13,\"row\":3,\"column\":1},{\"seatId\":14,\"row\":3,\"column\":2},{\"seatId\":15,\"row\":3,\"column\":3},{\"seatId\":16,\"row\":3,\"column\":4},{\"seatId\":17,\"row\":3,\"column\":5},{\"seatId\":18,\"row\":3,\"column\":6}],[{\"seatId\":19,\"row\":4,\"column\":1},{\"seatId\":20,\"row\":4,\"column\":2},{\"seatId\":21,\"row\":4,\"column\":3},{\"seatId\":22,\"row\":4,\"column\":4},{\"seatId\":23,\"row\":4,\"column\":5},{\"seatId\":24,\"row\":4,\"column\":6}]]}", FieldSeatsInfoVO.class);
		FieldSeatsInfoVO fielddolbyCinema = gson.fromJson("{\"limit\":5,\"ids\":\"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23\",\"single\":[[{\"seatId\":1,\"row\":1,\"column\":1},{\"seatId\":2,\"row\":1,\"column\":2},{\"seatId\":3,\"row\":1,\"column\":3},{\"seatId\":4,\"row\":1,\"column\":4},{\"seatId\":5,\"row\":1,\"column\":5},{\"seatId\":6,\"row\":1,\"column\":6},{\"seatId\":7,\"row\":1,\"column\":7},{\"seatId\":8,\"row\":1,\"column\":8}],[{\"seatId\":9,\"row\":2,\"column\":1},{\"seatId\":10,\"row\":2,\"column\":2},{\"seatId\":11,\"row\":2,\"column\":3},{\"seatId\":12,\"row\":2,\"column\":4},{\"seatId\":13,\"row\":2,\"column\":5},{\"seatId\":14,\"row\":2,\"column\":6},{\"seatId\":15,\"row\":2,\"column\":7}]],\"couple\":[[{\"seatId\":16,\"row\":3,\"column\":1},{\"seatId\":17,\"row\":3,\"column\":2},{\"seatId\":18,\"row\":3,\"column\":3},{\"seatId\":19,\"row\":3,\"column\":4}],[{\"seatId\":20,\"row\":4,\"column\":1},{\"seatId\":21,\"row\":4,\"column\":2},{\"seatId\":22,\"row\":4,\"column\":3},{\"seatId\":23,\"row\":4,\"column\":4}]]}", FieldSeatsInfoVO.class);
		FieldSeatsInfoVO fielddts = gson.fromJson("{\"limit\":5,\"ids\":\"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22\",\"single\":[[{\"seatId\":1,\"row\":1,\"column\":1},{\"seatId\":2,\"row\":1,\"column\":2},{\"seatId\":3,\"row\":1,\"column\":3},{\"seatId\":4,\"row\":1,\"column\":4},{\"seatId\":5,\"row\":1,\"column\":5}],[{\"seatId\":6,\"row\":2,\"column\":1},{\"seatId\":7,\"row\":2,\"column\":2},{\"seatId\":8,\"row\":2,\"column\":3},{\"seatId\":9,\"row\":2,\"column\":4},{\"seatId\":10,\"row\":2,\"column\":5}]],\"couple\":[[{\"seatId\":11,\"row\":3,\"column\":1},{\"seatId\":12,\"row\":3,\"column\":2},{\"seatId\":13,\"row\":3,\"column\":3},{\"seatId\":14,\"row\":3,\"column\":4},{\"seatId\":15,\"row\":3,\"column\":5},{\"seatId\":16,\"row\":3,\"column\":6}],[{\"seatId\":17,\"row\":4,\"column\":1},{\"seatId\":18,\"row\":4,\"column\":2},{\"seatId\":19,\"row\":4,\"column\":3},{\"seatId\":20,\"row\":4,\"column\":4},{\"seatId\":21,\"row\":4,\"column\":5},{\"seatId\":22,\"row\":4,\"column\":6}]]}", FieldSeatsInfoVO.class);
		redisTemplate.opsForValue().set("hall1",field4d);
		redisTemplate.opsForValue().set("hall2",field4dx);
		redisTemplate.opsForValue().set("hall3",field4k);
		redisTemplate.opsForValue().set("hall4",fieldcgs);
		redisTemplate.opsForValue().set("hall5",fielddolbyCinema);
		redisTemplate.opsForValue().set("hall6",fielddts);
		FieldSeatsInfoVO hall1 = (FieldSeatsInfoVO) redisTemplate.opsForValue().get("hall1");
		System.out.println(hall1);
		System.out.println(hall1.getIds());
		System.out.println(hall1.getSingle());
		System.out.println(hall1.getCouple());
		System.out.println(hall1.getLimit());
		/*String s = "{\"limit\":5,\"ids\":\"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22\",\"single\":[[{\"seatId\":1,\"row\":1,\"column\":1},{\"seatId\":2,\"row\":1,\"column\":2},{\"seatId\":3,\"row\":1,\"column\":3},{\"seatId\":4,\"row\":1,\"column\":4},{\"seatId\":5,\"row\":1,\"column\":5}],[{\"seatId\":6,\"row\":2,\"column\":1},{\"seatId\":7,\"row\":2,\"column\":2},{\"seatId\":8,\"row\":2,\"column\":3},{\"seatId\":9,\"row\":2,\"column\":4},{\"seatId\":10,\"row\":2,\"column\":5}]],\"couple\":[[{\"seatId\":11,\"row\":3,\"column\":1},{\"seatId\":12,\"row\":3,\"column\":2},{\"seatId\":13,\"row\":3,\"column\":3},{\"seatId\":14,\"row\":3,\"column\":4},{\"seatId\":15,\"row\":3,\"column\":5},{\"seatId\":16,\"row\":3,\"column\":6}],[{\"seatId\":17,\"row\":4,\"column\":1},{\"seatId\":18,\"row\":4,\"column\":2},{\"seatId\":19,\"row\":4,\"column\":3},{\"seatId\":20,\"row\":4,\"column\":4},{\"seatId\":21,\"row\":4,\"column\":5},{\"seatId\":22,\"row\":4,\"column\":6}]]}";
		FieldSeatsInfoVO fieldSeatsInfoVO = new Gson().fromJson(s, FieldSeatsInfoVO.class);*/
		System.out.println(123456);


	}

	@Test
	public void test01(){
		List<OrderVo> orderVos = orderTMapper.selectOrderByFiledId("1");

		System.out.println(JSON.toJSONString(orderVos));
	}

}
