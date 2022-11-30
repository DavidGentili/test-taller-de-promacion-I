package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import modelos.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import excepciones.SistemaYaInicializadoException;
import modelos.enums.FormaPago;

public class testFactura {

    private Pedido pedido;
    private Mesa mesa;
    private Sistema sistema;
    private Producto prod;
    private Date fecha;
    private List<Pedido> pedidos = new ArrayList<Pedido>();;
    private List<Promocion> promociones = new ArrayList<Promocion>();
    private Mozo mozo;

    
    @Before
    public void setup(){
        try{
            Sistema.inicializarSistema("local");
        } catch (SistemaYaInicializadoException e) {
            Assert.fail("Error al inicializar el escenario");
        }
        sistema = Sistema.getInstancia();
        fecha = new GregorianCalendar(2022,10,10).getTime();
        
        String nombre= "jose";
		String apellido= "perez";
		Date fechaNacimiento = new GregorianCalendar(2000, 10,10).getTime();
		int hijosACargo = 2;
		double remuneracionbasica = 1000;
		Sueldo sueldo = new Sueldo(remuneracionbasica);
		mozo = new Mozo(nombre,apellido,fechaNacimiento,hijosACargo,sueldo);
		
		mesa = new Mesa(3,4);
		
		prod = new Producto("prod1",100,200,30);
		pedido = new Pedido(prod,5);
		pedidos.add(pedido);      
		
        //Factura(Date fecha, Mesa mesa, List<Pedido> pedidos, double total, FormaPago formaPago, List<Promocion> promocionesAplicadas, Mozo mozo)
        
 


    }

    @After
    public void teardown(){
        ResetInstance.reseteSistemaAndAdministrador();
        sistema = null;
        pedidos = null;
        promociones = null;
        fecha = null;
        mozo = null;
        pedido = null;
        mesa = null;
        
    }

    @Test
    public void ConstructorFactura(){
    	Factura factura = new Factura(fecha,mesa,pedidos, 100,FormaPago.EFECTIVO,promociones,mozo);
    	
    	Assert.assertEquals("No se Asigno correctamente la fecha", fecha, factura.getFecha());
    	Assert.assertEquals("No se Asigno correctamente la mesa", mesa, factura.getMesa());
    	Assert.assertEquals("No se Asigno correctamente los pedidos", pedidos, factura.getPedidos());
    	Assert.assertEquals("No se Asigno correctamente el total", 100,factura.getTotal(),0.5);
    	Assert.assertEquals("No se Asigno correctamente la forma de pago", FormaPago.EFECTIVO, factura.getFormaPago());
    	Assert.assertEquals("No se Asigno correctamente las promociones", promociones, factura.getPromocionesAplicadas());
    	Assert.assertEquals("No se Asigno correctamente el mozo", mozo, factura.getMozo());
    	
    }

}
