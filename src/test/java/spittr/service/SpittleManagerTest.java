package spittr.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spittr.config.TestConfig;
import spittr.entity.Spittle;

import java.util.Date;

import static org.junit.Assert.*;

;
;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SpittleManagerTest {
	
	@Autowired
	SpittleManager spittleManager;

	@Test
	@Transactional
	public void findRecentSpittles(){
         assertEquals(10, spittleManager.findRecentSpittles().size());
	};

	@Test
	@Transactional
	public void findAll(){
		assertEquals(15, spittleManager.findAll().size());
	};


	@Test
	@Transactional
	public void count() {
		assertEquals(15, spittleManager.count());
	}


	@Test
	@Transactional
	public void findOne() {
		assertNotNull(spittleManager.findOne(1));
	}

	
	@Test
	@Transactional
	public void save() {
		assertEquals(15, spittleManager.count());
		Spittle spittle = new Spittle("Un Nuevo Spittle from Art", new Date());
		Spittle saved = spittleManager.save(spittle);
		assertEquals(16, spittleManager.count());
		assertNewSpittle(saved);
		assertNewSpittle(spittleManager.findOne(16L));
	}

	@Test
	@Transactional
	public void delete() {
		assertEquals(15, spittleManager.count());
		assertNotNull(spittleManager.findOne(13));
		spittleManager.delete(13L);
		assertEquals(14, spittleManager.count());
		assertNull(spittleManager.findOne(13));
	}

	private void assertNewSpittle(Spittle spittle) {
		assertEquals(16, spittle.getId().longValue());
	}
	
}
