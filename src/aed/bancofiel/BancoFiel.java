package aed.bancofiel;

import java.util.Comparator;
import es.upm.aedlib.indexedlist.IndexedList;
import es.upm.aedlib.indexedlist.ArrayIndexedList;

/**
 * Implements the code for the bank application. Implements the client and the
 * "gestor" interfaces.
 */
public class BancoFiel implements ClienteBanco, GestorBanco {

	// NOTAD. No se deberia cambiar esta declaracion.
	public IndexedList<Cuenta> cuentas;

	// NOTAD. No se deberia cambiar esta constructor.
	public BancoFiel() {
		this.cuentas = new ArrayIndexedList<Cuenta>();
	}

	// ----------------------------------------------------------------------
	// Anadir metodos aqui ...

	public IndexedList<Cuenta> getCuentasOrdenadas(Comparator<Cuenta> cmp) {
		// TODO Auto-generated method stub
		IndexedList<Cuenta> cuentasOrdenadas = new ArrayIndexedList<>();
		cuentasOrdenadas.add(0, cuentas.get(0));
		boolean parar;
		for (int i = 1; i < cuentas.size(); i++) {
			parar = false;
			for (int j = 0; j <= cuentasOrdenadas.size() && !parar; j++) {
				if (cmp.compare(cuentasOrdenadas.get(j), cuentas.get(i)) >= 0) {
					cuentasOrdenadas.add(j, cuentas.get(i));
					parar = true;
				}
				if (j == cuentasOrdenadas.size() - 1) {
					cuentasOrdenadas.add(cuentasOrdenadas.size(), cuentas.get(i));
					parar = true;
				}
			}
		}
		return cuentasOrdenadas;
	}

	public String crearCuenta(String dni, int saldoIncial) {
		// TODO Auto-generated method stub
		Cuenta newCuenta = new Cuenta(dni, saldoIncial);
		boolean salir = false;
		if (cuentas.isEmpty()) {
			cuentas.add(0, newCuenta);
			salir = true;
		}
		if (!salir && (newCuenta.getId().compareTo(cuentas.get(cuentas.size() - 1).getId()) > 0)) {
			cuentas.add(cuentas.size(), newCuenta);
			salir = true;
		}
		for (int i = 0; i < cuentas.size() && !salir; i++) {
			if (cuentas.get(i).getId().compareTo(newCuenta.getId()) > 0) {
				cuentas.add(i, newCuenta);
				salir = true;
			}
		}
		return newCuenta.getId();
	}

	public void borrarCuenta(String id) throws CuentaNoExisteExc, CuentaNoVaciaExc {
		// TODO Auto-generated method stub
		if (buscarCuenta(id) == -1)
			throw new CuentaNoExisteExc();
		if (cuentas.get(buscarCuenta(id)).getSaldo() != 0)
			throw new CuentaNoVaciaExc();
		cuentas.removeElementAt(buscarCuenta(id));
	}

	public int ingresarDinero(String id, int cantidad) throws CuentaNoExisteExc {
		// TODO Auto-generated method stub
		if (buscarCuenta(id) == -1)
			throw new CuentaNoExisteExc();
		cuentas.get(buscarCuenta(id)).ingresar(cantidad);
		return cuentas.get(buscarCuenta(id)).getSaldo();
	}

	public int retirarDinero(String id, int cantidad) throws CuentaNoExisteExc, InsuficienteSaldoExc {
		// TODO Auto-generated method stub
		if (buscarCuenta(id) == -1)
			throw new CuentaNoExisteExc();
		cuentas.get(buscarCuenta(id)).retirar(cantidad);
		return cuentas.get(buscarCuenta(id)).getSaldo();
	}

	public int consultarSaldo(String id) throws CuentaNoExisteExc {
		// TODO Auto-generated method stub
		if (buscarCuenta(id) == -1)
			throw new CuentaNoExisteExc();
		return cuentas.get(buscarCuenta(id)).getSaldo();
	}

	public void hacerTransferencia(String idFrom, String idTo, int cantidad)
			throws CuentaNoExisteExc, InsuficienteSaldoExc {
		// TODO Auto-generated method stub
		if (buscarCuenta(idFrom) == -1 || buscarCuenta(idTo) == -1)
			throw new CuentaNoExisteExc();
		cuentas.get(buscarCuenta(idFrom)).retirar(cantidad);
		cuentas.get(buscarCuenta(idTo)).ingresar(cantidad);
	}

	public IndexedList<String> getIdCuentas(String dni) {
		// TODO Auto-generated method stub
		IndexedList<String> idList = new ArrayIndexedList<>();
		int pos = 0;
		for (int i = 0; i < cuentas.size(); i++) {
			if (cuentas.get(i).getDNI().compareTo(dni) == 0) {
				idList.add(pos, cuentas.get(i).getId());
				pos++;
			}
		}
		return idList;
	}

	public int getSaldoCuentas(String dni) {
		// TODO Auto-generated method stub
		int saldoTotal = 0;
		for (int i = 0; i < cuentas.size(); i++) {
			if (cuentas.get(i).getDNI().compareTo(dni) == 0) {
				saldoTotal += cuentas.get(i).getSaldo();
			}
		}
		return saldoTotal;
	}

	// Metodos privados

	private int buscarCuenta(String id) {
		boolean enc = false;
		int centro = 0;
		int infim = 0;
		int supr = cuentas.size() - 1;
		if (cuentas.size() == 0) {
			return -1;
		}
		while (infim <= supr && !enc) {
			centro = (supr + infim) / 2;
			if (cuentas.get(centro).getId().compareTo(id) == 0) {
				enc = true;
			} else if (cuentas.get(centro).getId().compareTo(id) > 0) {
				supr = centro - 1;
			} else {
				infim = centro + 1;
			}
		}
		if (!enc)
			return -1;
		return centro;

	}

	// ----------------------------------------------------------------------
	// NOTAD. No se deberia cambiar este metodo.
	public String toString() {
		return "banco";
	}

}
