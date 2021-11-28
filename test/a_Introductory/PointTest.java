package a_Introductory;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {
	Point p1, p2, p3;
	
	@Before
	public void setUp() throws Exception {
		p1 = new Point(7, 9);
		p2 = new Point(-3, -30);
		p3 = new Point(-10, 3);
	}

	@Test
	public void testAdd() {
		Point res1 = p1.add(p2);
		Point res2 = p1.add(p3);
		
		assertEquals(Long.valueOf(4), Long.valueOf(res1.x));
		assertEquals(Long.valueOf(-21), Long.valueOf(res1.y));
		assertEquals(Long.valueOf(-3), Long.valueOf(res2.x));
		assertEquals(Long.valueOf(12), Long.valueOf(res2.y));
	}

	@Test
	public void testSub() {
		Point res1 = p1.sub(p2);
		Point res2 = p1.sub(p3);
		
		assertEquals(Long.valueOf(10), Long.valueOf(res1.x));
		assertEquals(Long.valueOf(39), Long.valueOf(res1.y));
		assertEquals(Long.valueOf(17), Long.valueOf(res2.x));
		assertEquals(Long.valueOf(6), Long.valueOf(res2.y));
	}

}
