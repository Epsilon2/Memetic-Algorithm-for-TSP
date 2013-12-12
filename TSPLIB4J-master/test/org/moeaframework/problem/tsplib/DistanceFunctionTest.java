/* Copyright 2012 David Hadka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package org.moeaframework.problem.tsplib;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the distance functions against the cases provided in the official
 * TSPLIB documentation.
 */
public class DistanceFunctionTest {

	@Test
	public void testPCB442() throws IOException {
		TSPInstance problem = new TSPInstance(new File("./data/tsp/pcb442.tsp"));
		problem.addTour(Tour.createCanonicalTour(problem.getDimension()));
		
		Assert.assertEquals(221440, problem.getTours().get(0).distance(problem), 0.5);
	}
	
	@Test
	public void testGR666() throws IOException {
		TSPInstance problem = new TSPInstance(new File("./data/tsp/gr666.tsp"));
		problem.addTour(Tour.createCanonicalTour(problem.getDimension()));
		
		Assert.assertEquals(423710, problem.getTours().get(0).distance(problem), 0.5);
	}
	
	@Test
	public void testATT532() throws IOException {
		TSPInstance problem = new TSPInstance(new File("./data/tsp/att532.tsp"));
		problem.addTour(Tour.createCanonicalTour(problem.getDimension()));
		
		Assert.assertEquals(309636, problem.getTours().get(0).distance(problem), 0.5);
	}
	
}
