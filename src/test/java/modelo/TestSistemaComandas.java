package modelo;

import excepciones.*;
import modelos.Mesa;
import modelos.Mozo;
import modelos.Sistema;
import modelos.Sueldo;
import modelos.enums.ModoOperacion;
import org.junit.*;

import java.util.GregorianCalendar;

public class TestSistemaComandas {

    private Sistema sistema;
    private Mesa mesa;

    @Before
    public void setup(){
        try {
            Sistema.inicializarSistema("local");
        } catch (SistemaYaInicializadoException ignored) {
            Assert.fail("Error al preparar escenario");
        }
        sistema = Sistema.getInstancia();
        sistema.setModoOperacion(ModoOperacion.ADMINISTRADOR);
        mesa = new Mesa(3,3);
        try {
            sistema.agregarMesa(mesa);
        } catch (MesaRepetidaException | OperacionNoAutorizadaException e) {
            Assert.fail("Error al preparar escenario");
        }
    }

    @After
    public void teardown(){
        ResetInstance.reseteSistemaAndAdministrador();
        mesa = null;
        sistema = null;
    }

    @Test
    public void testCrearComandaConMesaExistenteLibre(){
        try {
            sistema.crearComanda(mesa);
            Assert.assertTrue("La mesa no se ocupo correctamente",mesa.estaOcupada());
            Assert.assertTrue("La comanda no se adiciono correctamente", sistema.getComandas().containsKey(mesa));
        } catch (MesaInexistenteException | MesaOcupadaException e) {
            Assert.fail("La comanda no se creo correctamente");
        }
    }

    @Test
    public void testCrearComandaConMesaNoExistenteLibre(){
        Mesa nueva = new Mesa(4,5);
        try {
            sistema.crearComanda(nueva);
            Assert.fail("No se arrojo la excepcion esperada");
        } catch (MesaInexistenteException e) {

        } catch (MesaOcupadaException e){
            Assert.fail("Error al emitir excepcion");
        }
    }

    @Test
    public void testCrearComandaConMesaExistenteOcupada(){
        mesa.ocupar();
        try {
            sistema.crearComanda(mesa);
            Assert.fail("No se arrojo la excepcion esperada");
        } catch (MesaInexistenteException e) {
            Assert.fail("La comanda no se creo correctamente");
        } catch (MesaOcupadaException e){

        }
    }

    @Test
    public void testCerrarComandaMesaExistenteOcupada(){
        try{
            sistema.crearComanda(mesa);
        } catch (MesaOcupadaException | MesaInexistenteException e) {
            Assert.fail("Error al preparar escenario");
        }

        try{
            sistema.cerrarComanda(mesa);
            Assert.assertFalse("La mesa no se libero correctamente", mesa.estaOcupada());
            Assert.assertFalse("La mesa no se retiro correctamente de la coleccion", sistema.getComandas().containsKey(mesa));
        } catch (MesaNoOcupadaException | MesaInexistenteException e) {
            Assert.fail("Error al cerrar la comanda");
        }
    }

    @Test
    public void testCerrarComandaMesaInexistente(){
        Mesa nueva = new Mesa(8,5);
        nueva.ocupar();
        try {
            sistema.cerrarComanda(nueva);
            Assert.fail("Excepcion no emitida");
        } catch (MesaInexistenteException e) {

        } catch (MesaNoOcupadaException e) {
            Assert.fail("Excepcion incorrecta emitida");
        }
    }

    @Test
    public void testCerrarComandaMesaNoOcupada(){
        mesa.desocupar();
        try{
            sistema.cerrarComanda(mesa);
            Assert.fail("Excepcion no emitida");
        } catch (MesaNoOcupadaException e) {

        } catch (MesaInexistenteException e) {
            Assert.fail("Excepcion incorrecta emitida");
        }
    }

    /**
     * Test creado apartir de analisis de cobertura y caja blanca
     */
    @Test
    public void testCerrarComandaMesaEnListaDeAsignaciones() {
        Mozo mozo = new Mozo("carlos", "Gomez", new GregorianCalendar(2000,5,12).getTime(), 0, new Sueldo(100));
        try {
            sistema.asignarMesa(mozo, mesa);
            sistema.crearComanda(mesa);
        } catch (MesaOcupadaException | MozoInexistenteException | MesaInexistenteException e) {
            Assert.fail("Error al preparar el escenario");
        }
        try{
            sistema.cerrarComanda(mesa);
        } catch (MesaNoOcupadaException | MesaInexistenteException e) {
            Assert.fail("Se emitio incorrectamente una excepcion");
        }

    }
}
