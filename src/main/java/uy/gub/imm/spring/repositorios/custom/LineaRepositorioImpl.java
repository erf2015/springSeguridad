package uy.gub.imm.spring.repositorios.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import uy.gub.imm.spring.jpa.Linea;
import uy.gub.imm.spring.utiles.Estados;

@Repository
public class LineaRepositorioImpl implements LineaRepositorioCustom {

	@PersistenceContext
	private EntityManager em;

	private Logger logger = LoggerFactory.getLogger(LineaRepositorioImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Linea> obtenerLineasPorNombre(String nombre) {
		String method = "obtenerLineasPorNombre";
		logger.info(Estados.BEGIN + " " + method + " parámetros {nombre} {" + nombre + "}");
		TypedQuery<Linea> proc = (TypedQuery<Linea>) em.createNamedQuery("Lineas.byName");
		proc.setParameter("nombre", nombre);
		List<Linea> resultados = proc.getResultList();
		if (resultados != null && resultados.size() != 0) {
			logger.info(Estados.SUCCES + " " + method + " Cantidad de resultados " + resultados.size());
			return resultados;
		}
		logger.info(Estados.WARNING + " " + method + "NOT FOUND");
		return null;
	}

	@Override
	public List<Linea> obtenerLineasPorCriterios(String nombre, String codigo, int estado, String subsistema,
			String Empresa) {
		String method = "obtenerLineasPorCriterios";
		logger.info(Estados.BEGIN + " " + method + " parámetros {nombre,codigo,estado,subsistema,Empresa} {" + nombre
				+ "," + codigo + "," + estado + "," + subsistema + "," + Empresa + "}");
		String sql = "SELECT l FROM Linea l ";
		String where = " WHERE 1=1 ";
		if (!nombre.equals("-1")) {
			where += " AND l.nombre =:nombre ";
		}
		if (!codigo.equals("-1")) {
			where += " AND l.id =:codigo ";
		}
		switch (estado) {
		case -1:
			break;
		case 0:
			where += " AND CURRENT_DATE BETWEEN l.fechaVigencia AND l.fechaBaja ";
			break;
		case 1:
			where += " AND CURRENT_DATE > l.fechaBaja ";
			break;
		}
		if (!subsistema.equals("-1")) {
			where += " AND l.subsistema.id =:subsistema ";
		}
		if (!Empresa.equals("-1")) {
			where += " AND l.empresa.id =:Empresa ";
		}
		logger.info(Estados.INFO + " " + method + " {SQL} {" + sql + where + "}");
		TypedQuery<Linea> query = em.createQuery(sql + where, Linea.class);
		if (!nombre.equals("-1")) {
			query.setParameter("nombre", nombre);
		}
		if (!subsistema.equals("-1")) {
			query.setParameter("subsistema", new Long(subsistema));
		}
		if (!Empresa.equals("-1")) {
			query.setParameter("Empresa", new Long(Empresa));
		}
		if (!codigo.equals("-1")) {
			query.setParameter("codigo", new Long(codigo));
		}
		List<Linea> resultado = query.getResultList();
		logger.info(Estados.SUCCES + " " + method + " Cantidad de resultados " + resultado.size());
		return resultado;
	}

}
