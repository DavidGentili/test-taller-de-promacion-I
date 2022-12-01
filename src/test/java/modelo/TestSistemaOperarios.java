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
        } catch (SistemaYaInicializadoException e) {
            //Se da por supuesto que la inicializacion del sistema ya esta testeada
        }
        sistema = Sistema.getInstancia();
        operario = new Operario("Carlos", "Gomez", "CGomez", "Carlos123");
    }

    @After
    public void turndown(){
        ResetInstance.reseteSistemaAndAdministrador();
        operario = null;
        sistema = null;

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
        escenario1();
        try{
            sistema.eliminarOperario(operario);
            Assert.fail("No se emitio la excepcion esperada");
        } catch (OperacionNoAutorizadaException e) {
            Assert.fail("Se emitio la excepcion incorrecta");
        } catch (OperarioInexistenteException e) {

        }
    }

    @Test
    public void testEliminarOperarioExistenteEscenario2(){
        escenario2();
        agregaOperario(operario);
        try{
            sistema.eliminarOperario(operario);
            Assert.fail("No se emitio la excepcion esperada");
        } catch (OperacionNoAutorizadaException e) {

        } catch (OperarioInexistenteException e) {
            Assert.fail("Se emitio la excepcion incorrecta");
        }
    }

    @Test
    public void testEliminarOperarioInexistenteEscenario2(){
        escenario2();
        try{
            sistema.eliminarOperario(operario);
            Assert.fail("No se emitio la excepcion esperada");
        } catch (OperacionNoAutorizadaException | OperarioInexistenteException e) {
        }
    }

    @Test
    public void testBuscarOperarioExistente(){
        agregaOperario(operario);
        try{
            Operario buscado = sistema.buscarOperario(operario.getNombreUsuario());
            Assert.assertEquals("No se retorno el operario esperado", operario, buscado);
        } catch (OperarioInexistenteException e) {
            Assert.fail("Se emitio una excepcion no esperada");
        }
    }

    @Test
    public void testBuscaOperarioInexistente(){
        try{
            Operario buscado = sistema.buscarOperario(operario.getNombreUsuario());
            Assert.fail("No se emitio la excepcion esperada");
        } catch (OperarioInexistenteException e) {

        }
    }

    @Test
    public void testBuscarOperarioAdministrador(){
        try{
            Operario buscado = sistema.buscarOperario("ADMIN");
            Assert.assertEquals("No se retorno el operario esperado", "ADMIN", buscado.getNombre());
            Assert.assertEquals("No se retorno el operario esperado", "ADMIN", buscado.getApellido());

        } catch (OperarioInexistenteException e) {
            Assert.fail("Se emitio una excepcion no esperada");
        }
    }


}
