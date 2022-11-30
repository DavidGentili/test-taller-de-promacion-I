package modelo;

import excepciones.OperacionNoAutorizadaException;
import excepciones.OperarioExistenteException;
import excepciones.OperarioInexistenteException;
import excepciones.SistemaYaInicializadoException;
import modelos.Operario;
import modelos.Sistema;
import modelos.enums.ModoOperacion;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

public class TestSistemaOperarios {

    private Sistema sistema;
    private Operario operario;
    private final String nombreLocal = "Nombrelocal";

    @Before
    public void setup(){
        try {
            if(!Sistema.isInicializado())
                Sistema.inicializarSistema(nombreLocal);
            sistema = Sistema.getInstancia();
        } catch (SistemaYaInicializadoException e) {
            //Se da por supuesto que la inicializacion del sistema ya esta testeada
        }
        operario = new Operario("Carlos", "Gomez", "CarlosGomez", "Carlos123");
    }

    @After
    public void turndown(){
        Field instance;

    }

    private void escenario1(){
        Assert.assertNotNull("No se inicializo correctamente el sistema", sistema);
        sistema.setModoOperacion(ModoOperacion.ADMINISTRADOR);
        sistema.getOperarios().clear();
    }

    private void escenario2(){
        Assert.assertNotNull("No se inicializo correctamente el sistema", sistema);
        sistema.setModoOperacion(ModoOperacion.OPERARIO);
    }

    private void agregaOperario(Operario operario){
        Assert.assertNotNull("No se inicializo correctamente el sistema", sistema);
        ModoOperacion aux = sistema.getModoOperacion();
        sistema.setModoOperacion(ModoOperacion.ADMINISTRADOR);
        try {
            sistema.agregarOperario(operario);
            sistema.setModoOperacion(aux);
        } catch (OperarioExistenteException | OperacionNoAutorizadaException e) {
            Assert.fail("Error al preparar el escenario");
        }

    }

    @Test
    public void testAgregarOperarioNuevoEscenario1(){
        escenario1();
        try {
            sistema.agregarOperario(operario);
            Assert.assertTrue("No se agrego correctamente el operario a la coleccion", sistema.getOperarios().contains(operario));
        } catch (OperarioExistenteException | OperacionNoAutorizadaException e) {
            Assert.fail("Se emitio una excepcion que no corresponde");
        }
    }

    @Test
    public void testAgregarOperarioExistenteEscenario1(){
        escenario1();
        agregaOperario(operario);
        try{
            sistema.agregarOperario(operario);
            Assert.fail("No se emitio la excepcion correspondiente");
        } catch (OperarioExistenteException e) {

        } catch (OperacionNoAutorizadaException e) {
            Assert.fail("Error al preparar el escenario");
        }
    }

    @Test
    public void testAgregarOperarioNuevoEscenario2(){
        escenario2();
        try {
            sistema.agregarOperario(operario);
            Assert.fail("No se emitio la excepcion correspondiente");
        } catch (OperarioExistenteException e) {
            Assert.fail("Se emitio una excepcion que no corresponde");
        } catch (OperacionNoAutorizadaException e){

        }
    }

    @Test
    public void testAgregarOperarioExistenteEscenario2(){
        escenario2();
        agregaOperario(operario);
        try{
            sistema.agregarOperario(operario);
            Assert.fail("No se emitio la excepcion correspondiente");
        } catch (OperarioExistenteException | OperacionNoAutorizadaException e) {

        }
    }

    @Test
    public void testEliminaOperarioExistenteEscenario1(){
        escenario1();
        agregaOperario(operario);
        try{
            sistema.eliminarOperario(operario);
            Assert.assertFalse("No se elimino correctamente el operario", sistema.getOperarios().contains(operario));
        } catch (OperacionNoAutorizadaException | OperarioInexistenteException e) {
            Assert.fail("Se emitio una excepcion que no correspondia");
        }
    }

    @Test
    public void testEliminarOperarioInexistenteEscenario1(){

    }



}