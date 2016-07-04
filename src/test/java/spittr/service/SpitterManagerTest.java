package spittr.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import spittr.config.TestConfig;
import spittr.entity.Spitter;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SpitterManagerTest {

  @Autowired
  SpitterManager spitterManager;

  @Test
  @Transactional
  public void count() {
    assertEquals(4, spitterManager.count());
  }

  @Test
  @Transactional
  public void findAll() {
    List<Spitter> spitters = spitterManager.findAll();
    assertEquals(4, spitters.size());
    assertSpitter(0, spitters.get(0));
    assertSpitter(1, spitters.get(1));
    assertSpitter(2, spitters.get(2));
    assertSpitter(3, spitters.get(3));
  }

  @Test
  @Transactional
  public void findByUsername() {
    assertSpitter(0, spitterManager.findByUsername("habuma"));
    assertSpitter(1, spitterManager.findByUsername("mwalls"));
    assertSpitter(2, spitterManager.findByUsername("chuck"));
    assertSpitter(3, spitterManager.findByUsername("artnames"));
  }

  @Test
  @Transactional
  public void findOne() {
    assertSpitter(0, spitterManager.findOne(1L));
    assertSpitter(1, spitterManager.findOne(2L));
    assertSpitter(2, spitterManager.findOne(3L));
    assertSpitter(3, spitterManager.findOne(4L));
  }

  @Test
  @Transactional
  public void save_newSpitter() {
    assertEquals(4, spitterManager.count());
    Spitter spitter = new Spitter(null, "newbee", "letmein", "New", "Bee",
        "newbee@habuma.com");
    Spitter saved = spitterManager.save(spitter);
    assertEquals(5, spitterManager.count());
    assertSpitter(4, saved);
    assertSpitter(4, spitterManager.findOne(5L));
  }


  @Test
  @Transactional
  public void update_Spitter() {
    Spitter spitter = spitterManager.findOne(1L);
    spitter.setEmail("test@aalto.fi");
    spitterManager.update(spitter);
    assertEquals(spitter, spitterManager.findOne(1L));

  }

  private static void assertSpitter(int expectedSpitterIndex, Spitter actual) {
    assertSpitter(expectedSpitterIndex, actual, "Newbie");
  }

  private static void assertSpitter(int expectedSpitterIndex, Spitter actual,
      String expectedStatus) {
    Spitter expected = SPITTERS[expectedSpitterIndex];
    assertEquals(expected, actual);
  }

  private static Spitter[] SPITTERS = new Spitter[6];

  @BeforeClass
  public static void before() {
    SPITTERS[0] = new Spitter(1L, "habuma", "password", "Craig", "Walls",
        "craig@habuma.com");
    SPITTERS[1] = new Spitter(2L, "mwalls", "password", "Michael", "Walls",
        "mwalls@habuma.com");
    SPITTERS[2] = new Spitter(3L, "chuck", "password", "Chuck", "Wagon",
        "chuck@habuma.com");
    SPITTERS[3] = new Spitter(4L, "artnames", "password", "Art", "Names",
        "art@habuma.com");
    SPITTERS[4] = new Spitter(5L, "newbee", "letmein", "New", "Bee",
        "newbee@habuma.com");
    SPITTERS[5] = new Spitter(4L, "arthur", "letmein", "Arthur", "Names",
        "arthur@habuma.com");
  }

}
