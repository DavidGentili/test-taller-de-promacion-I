package modelo;

import excepciones.*;
import modelos.Mesa;
import modelos.Pedido;
import modelos.Producto;
import modelos.Sistema;
import modelos.enums.ModoOperacion;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;

public class testSistemaPedidos {

    private Pedido pedido;
    private Mesa mesa;
    private Sistema sistema;
    private Producto prod;

    @Before
    public void setup(){
        try{
            Sistema.inicializarSistema("local");
        } catch (SistemaYaInicializadoException e) {
            Assert.fail("Error al inicializar el escenario");
        }
        sistema = Sistema.getInstancia();
        sistema.setModoOperacion(ModoOperacion.ADMINISTRADOR);
        mesa = new Mesa(3,4);
        prod = new Producto("prod1",100,200,30);
        pedido = new Pedido(prod,5);
        try {
            sistema.agregarMesa(mesa);
            sistema.agregarProducto(prod);
        } catch (MesaRepetidaException | OperacionNoAutorizadaException | ProductoExistenteException e) {
            Assert.fail("Error al inicializar escenario");
        }
    }

    @After
    public void teardown(){
        ResetInstance.reseteSistemaAndAdministrador();
        sistema = null;
        pedido = null;
        mesa = null;
    }

    private void crearComanda(Mesa mesa){
        try {
            sistema.crearComanda(mesa);
        } catch (MesaInexistenteException | MesaOcupadaException e) {
            Assert.fail("Error al crear escenario");
        }
    }

    @Test
    public void testAgregarPedido(){
    	crearComanda(mesa);
    	try{
    		sistema.agregarPedido(mesa,pedido);
    		Assert.assertTrue("No se ha añadido a la colección", sistema.getComandas().containsValue(pedido));
    	}catch(ComandaInexistenteException | StockInsuficienteException e) {
    		Assert.fail("Se emitio una excepción no correspondida");
    		
    	}
    }

    @Test
    public void testAgregarPedidoComandaInexistente(){
    	try{
    		sistema.agregarPedido(mesa, pedido);
    		Assert.fail("No se emitio la excepción correspondiente");
    	}catch(ComandaInexistenteException e) {

    		
    	}catch(StockInsuficienteException e) {
    		Assert.fail("Se emitio una excepción no correspondida");
    	}

    }

    @Test
    public void testAgregarPedidoStockInsuficiente(){
    	crearComanda(mesa);
    	Pedido pedido1 = new Pedido(prod,100);
    	try{
    		sistema.agregarPedido(mesa,pedido1);
    		Assert.fail("No se emitio la excepción correspondiente");
    	}catch(ComandaInexistenteException e) {
    		Assert.fail("Se emitio una excepción no correspondida");
    	}catch(StockInsuficienteException e) {
    		
    	}
    }
}
