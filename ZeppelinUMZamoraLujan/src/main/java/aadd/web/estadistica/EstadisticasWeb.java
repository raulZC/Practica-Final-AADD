package aadd.web.estadistica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

import aadd.persistencia.dto.EstadisticaImporteDTO;
import aadd.persistencia.dto.EstadisticaPedidoEstadosDTO;
import aadd.persistencia.dto.EstadisticaOpinionDTO;
import aadd.persistencia.dto.EstadisticaPedidosDTO;
import aadd.persistencia.jpa.bean.TipoUsuario;
import aadd.web.usuario.UserSessionWeb;
import aadd.zeppelinum.ServicioGestionPlataforma;

@Named
@ViewScoped
public class EstadisticasWeb implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1866316118541958986L;
	@Inject
	protected UserSessionWeb usuarioSesion;
	protected ServicioGestionPlataforma servicioPlataforma;
	private LineChartModel lineModel;
	private LineChartModel lineModel2;
	private String header1;
	private String header2;

	public EstadisticasWeb() {
		servicioPlataforma = ServicioGestionPlataforma.getServicioGestionPlataforma();
	}

	@PostConstruct
	public void initLineChart() {
		if (usuarioSesion.getUsuario().getTipo().equals(TipoUsuario.ADMIN)) {
			createLineModelValoraciones();
			createLineModelPedidosEstados();
			header1 = "Valoraciones";
			header2 = "Estado Pedidos";
		} else {
			createLineModelNumPedidos();
			createLineModelDineroGastado();
			header1 = "Pedidos Realizados";
			header2 = "Importe Gastado";
		}
	}

	private void createLineModelValoraciones() {
		List<EstadisticaOpinionDTO> estadisticas = servicioPlataforma
				.getEstadisticasOpinion(usuarioSesion.getUsuario().getId());
		lineModel = new LineChartModel();
		ChartData data = new ChartData();

		LineChartDataSet dataSet = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			values.add(0);
		}
		for (EstadisticaOpinionDTO e : estadisticas) {
			values.set(e.getNota().intValue(), e.getTotal());
		}

		dataSet.setData(values);
		dataSet.setFill(false);
		dataSet.setLabel("Valoraciones a restaurantes");
		dataSet.setBorderColor("rgb(75, 192, 192)");

		data.addChartDataSet(dataSet);

		List<String> labels = new ArrayList<>();
		labels.add("0");
		labels.add("1");
		labels.add("2");
		labels.add("3");
		labels.add("4");
		labels.add("5");
		labels.add("6");
		labels.add("7");
		labels.add("8");
		labels.add("9");
		labels.add("10");
		data.setLabels(labels);

		// Options
		LineChartOptions options = new LineChartOptions();
		Title title = new Title();
		title.setDisplay(true);
		title.setText("Line Chart");
		options.setTitle(title);

		lineModel.setOptions(options);
		lineModel.setData(data);
	}

	private void createLineModelPedidosEstados() {
		List<EstadisticaPedidoEstadosDTO> estados = servicioPlataforma
				.getEstadisticasEstados(usuarioSesion.getUsuario().getId());

		lineModel2 = new LineChartModel();
		ChartData data = new ChartData();

		LineChartDataSet dataSet = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			values.add(0);
		}

		for (EstadisticaPedidoEstadosDTO e : estados) {
			values.set(e.getNum(), e.getTotal());
		}

		dataSet.setData(values);
		dataSet.setFill(false);
		dataSet.setLabel("Estado de los Pedidos");
		dataSet.setBorderColor("rgb(75, 192, 192)");

		data.addChartDataSet(dataSet);

		List<String> labels = new ArrayList<>();
		labels.add("INICIO");
		labels.add("ACEPTADO");
		labels.add("PREPARADO");
		labels.add("RECOGIDO");
		labels.add("CANCELADO");
		data.setLabels(labels);

		// Options
		LineChartOptions options = new LineChartOptions();
		Title title = new Title();
		title.setDisplay(true);
		title.setText("Line Chart");
		options.setTitle(title);

		lineModel2.setOptions(options);
		lineModel2.setData(data);
	}

	private void createLineModelNumPedidos() {
		List<EstadisticaPedidosDTO> pedidos = servicioPlataforma
				.getEstadisticasPedidos(usuarioSesion.getUsuario().getId());
		lineModel = new LineChartModel();
		ChartData data = new ChartData();

		LineChartDataSet dataSet = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			values.add(0);
		}
		List<String> labels = new ArrayList<>();
		int cont = 0;
		for (EstadisticaPedidosDTO p : pedidos) {
			values.set(cont, p.getTotal());
			labels.add(p.getRestaurante());
			cont++;
		}

		dataSet.setData(values);
		dataSet.setFill(false);
		dataSet.setLabel("Numero de Pedidos Realizados");
		dataSet.setBorderColor("rgb(75, 192, 192)");

		data.addChartDataSet(dataSet);

		data.setLabels(labels);

		// Options
		LineChartOptions options = new LineChartOptions();
		Title title = new Title();
		title.setDisplay(true);
		title.setText("Line Chart");
		options.setTitle(title);

		lineModel.setOptions(options);
		lineModel.setData(data);
	}

	private void createLineModelDineroGastado() {
		List<EstadisticaImporteDTO> gastos = servicioPlataforma
				.getEstadisticasImporte(usuarioSesion.getUsuario().getId());
		lineModel2 = new LineChartModel();
		ChartData data = new ChartData();

		LineChartDataSet dataSet = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		for (int i = 0; i < 11; i++) {
			values.add(0);
		}
		List<String> labels = new ArrayList<>();
		int cont = 0;
		for (EstadisticaImporteDTO g : gastos) {
			values.set(cont, g.getTotal());
			labels.add(g.getRestaurante());
			cont++;
		}

		dataSet.setData(values);
		dataSet.setFill(false);
		dataSet.setLabel("Dinero Gastado");
		dataSet.setBorderColor("rgb(75, 192, 192)");

		data.addChartDataSet(dataSet);

		data.setLabels(labels);

		// Options
		LineChartOptions options = new LineChartOptions();
		Title title = new Title();
		title.setDisplay(true);
		title.setText("Line Chart");
		options.setTitle(title);

		lineModel2.setOptions(options);
		lineModel2.setData(data);
	}

	public Integer getNumVisitas() {
		return servicioPlataforma.getNumVisitas(usuarioSesion.getUsuario().getId());
	}

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public void setLineModel(LineChartModel lineModel) {
		this.lineModel = lineModel;
	}

	public LineChartModel getLineModel2() {
		return lineModel2;
	}

	public void setLineModel2(LineChartModel lineModel2) {
		this.lineModel2 = lineModel2;
	}

	public String getHeader1() {
		return header1;
	}

	public void setHeader1(String header1) {
		this.header1 = header1;
	}

	public String getHeader2() {
		return header2;
	}

	public void setHeader2(String header2) {
		this.header2 = header2;
	}

}