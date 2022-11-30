package modelos;

import modelos.enums.FormaPago;

import java.util.Date;
import java.util.List;

/**
 * Clase que representa una factura. <br>
 * <b>Inv:</b> <br>
 * fecha != null <br>
 * mesa != null <br>
 * pedidos != null <br>
 * total > 0 <br>
 * formaPago != null <br>
 * mozo != null <br>
 */
public class Factura {
    private Date fecha;
    private Mesa mesa;
    private List<Pedido> pedidos;
    private double total;
    private FormaPago formaPago;
    private List<Promocion> promocionesAplicadas;
    private Mozo mozo;

    /**
     * Constructor de la clase Factura. <br>
     * <b>Pre:</b> <br>
     * fecha != null <br>
     * mesa != null <br>
     * pedidos != null <br>
     * total > 0 <br>
     * formaPago != null <br>
     * mozo != null <br>
     * <b>Post:</b> <br>
     * Se crea una nueva factura con los datos ingresados. <br>
     *
     * @param fecha                Fecha de la factura.
     * @param mesa                 Mesa de la factura.
     * @param pedidos              Pedidos de la factura.
     * @param total                Total de la factura.
     * @param formaPago            Forma de pago de la factura.
     * @param promocionesAplicadas Promociones aplicadas a la factura.
     * @param mozo                 Mozo de la factura.
     */
    public Factura(Date fecha, Mesa mesa, List<Pedido> pedidos, double total, FormaPago formaPago, List<Promocion> promocionesAplicadas, Mozo mozo) {
        assert fecha != null : "La fecha no puede ser nula";
        assert mesa != null : "La mesa no puede ser nula";
        assert pedidos != null : "La lista de pedidos no puede ser nula";
        assert total > 0 : "El total debe ser mayor a 0";
        assert formaPago != null : "La forma de pago no puede ser nula";
        assert mozo != null : "El mozo no puede ser nulo";

        this.fecha = fecha;
        this.mesa = mesa;
        this.pedidos = pedidos;
        this.total = total;
        this.formaPago = formaPago;
        this.promocionesAplicadas = promocionesAplicadas;
        this.mozo = mozo;

        assert this.fecha == fecha : "La fecha no se asignó correctamente";
        assert this.mesa == mesa : "La mesa no se asignó correctamente";
        assert this.pedidos == pedidos : "La lista de pedidos no se asignó correctamente";
        assert this.total == total : "El total no se asignó correctamente";
        assert this.formaPago == formaPago : "La forma de pago no se asignó correctamente";
        assert this.promocionesAplicadas == promocionesAplicadas : "La lista de promociones no se asignó correctamente";
        assert this.mozo == mozo : "El mozo no se asignó correctamente";
    }

    //METODOS AGREGADOS PARA EL TESTEO

    public Date getFecha() {
        return fecha;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public List<Promocion> getPromocionesAplicadas() {
        return promocionesAplicadas;
    }

    public Mozo getMozo() {
        return mozo;
    }
}
