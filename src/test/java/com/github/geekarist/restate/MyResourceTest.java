/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.geekarist.restate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cp
 */
public class MyResourceTest {
    
    public MyResourceTest() {
    }

    @org.junit.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.junit.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getIt method, of class MyResource.
     */
    @org.junit.Test
    public void testGetIt() {
        System.out.println("getIt");
        MyResource instance = new MyResource();
        String expResult = "";
        String result = instance.getIt();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
