package modelo;

import excepciones.OperacionNoAutorizadaException;
import excepciones.ProductoExistenteException;
import excepciones.ProductoInexistenteException;
import excepciones.SistemaYaInicializadoException;
import modelos.Producto;
import modelos.PromocionProducto;
import modelos.Sistema;
import modelos.enums.Dia;
import modelos.enums.ModoOperacion;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class testSistemaPromocionProducto {

    private PromocionProducto promo;
    private Sistema sistema;
    private Producto prod1;
    private Producto prod2;

    @Before
    public void setup(){
        try{
            Sistema.inicializarSistema("local");
        } catch (SistemaYaInicializadoException e) {
            Assert.fail("Error al inicializar el escenario");
        }
        sistema = Sistema.getInstancia();
        sistema.setModoOperacion(ModoOperacion.ADMINISTRADOR);
        List<Dia> dias = new ArrayList<>();
        dias.add(Dia.LUNES);
        promo = new PromocionProducto(true, false, 0, 0, dias);
        prod1 = new Producto("Producto1",100,200,50);
        prod2 = new Producto("Producto2", 150, 250, 20);
        try {
            sistema.agregarProducto(prod1);
        } catch (ProductoExistenteException | OperacionNoAutorizadaException e) {
            Assert.fail("Error al preparar escenario");
        }
    }

    @After
    public void teardown(){
        ResetInstance.reseteSistemaAndAdministrador();
        sistema = null;
        promo = null;
    }

    private void agregarPromocionProducto(PromocionProducto promo, Producto prod){
        try{
            sistema.agregarPromocionProducto(prod1, promo);
        }catch(ProductoInexistenteException e) {
            Assert.fail("No se inicializo correctamente el escenario");
        }
    }

    @Test
    public void testAgregarPromocionProducto(){
        try{
            sistema.agregarPromocionProducto(prod1, promo);
            Assert.assertTrue("No se ha añadido a la colección", sistema.getPromocionesProducto().get(prod1).contains(promo));
        }catch(ProductoInexistenteException e) {
            Assert.fail("Se emitio una excepción no correspondida");
        }

    }

    @Test
    public void testAgregarPromocionProductoInexistente(){
        try{
            sistema.agregarPromocionProducto(prod2, promo);
            Assert.fail("Se agregó un producto que no corresponde");
        }catch(ProductoInexistenteException e) {

        }

    }

    @Test
    public void testEliminarPromocionProducto(){
        agregarPromocionProducto(promo, prod1);
        try{
            sistema.eliminarPromocionProducto(prod1);
            Assert.assertFalse("No se ha eliminado de la colección", sistema.getPromocionesProducto().get(prod1).contains(promo));
        }catch(ProductoInexistenteException e) {
            Assert.fail("Se emitio una excepción no correspondida");
        }
    }

    @Test
    public void testEliminarPromocionProductoInexistente() {
        try {
            sistema.eliminarPromocionProducto(prod2);
            Assert.fail("No se emitio una excepción no correspondida");
        } catch (ProductoInexistenteException e) {


        }

    }
}
