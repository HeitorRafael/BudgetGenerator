/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author raffinoh
 */
public class OrcamentoIT {
    
    public OrcamentoIT() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of removerMarcaDagua method, of class orcamento.
     */
    @Test
    public void testRemoverMarcaDagua() {
        System.out.println("removerMarcaDagua");
        Orcamento instance = null;
        instance.removerMarcaDagua();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class orcamento.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Orcamento instance = null;
        String expResult = "";
        String result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isComMarcaDagua method, of class orcamento.
     */
    @Test
    public void testIsComMarcaDagua() {
        System.out.println("isComMarcaDagua");
        Orcamento instance = null;
        boolean expResult = false;
        boolean result = instance.isComMarcaDagua();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class orcamento.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Orcamento instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
