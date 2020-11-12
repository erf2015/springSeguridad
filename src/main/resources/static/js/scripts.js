function hideToast() {
	var data = window.localStorage.getItem('mensaje');
	if (data !== null) {
		$('p.pMensaje').text(data);
		$('.toast').toast({
			delay : 3000,
			autohide : true,
			animation : true
		});
		$('.toast').toast('show');
		window.localStorage.removeItem('mensaje');
	} else {
		$('.toast').toast('hide');
	}
}

$("#nuevaLineaForm").submit(function(event) {
	event.preventDefault();
	crearLinea();
});

$("#editarLineaForm").submit(function(event) {
	modificarLinea();
});

$("#bajaLineaForm").submit(function(event) {
	bajaLinea();
});

function crearLinea() {
	var nombreLinea = $('#nombrePublico').val();
	var subsistema = $('#subsistemasSelect').val();
	var tipoLinea = $('#tipoLineaSelect').val();
	var fecha = $('#fechaPublicableWeb').val();
	var contexPath = $('#path').val();
	$.ajax({
		type : "GET",
		url : contexPath + "/servicio/linea/add?nombre=" + nombreLinea
				+ "&codigoSub=" + subsistema + "&tipoLinea=" + tipoLinea
				+ "&fecha=" + fecha,
		success : function(data, statusText, xhr) {
			var status = data.status;
			switch (status) {
			case 200:
				$('#nuevaLinea').modal('hide');
				window.localStorage.setItem('mensaje', 'Línea ' + nombreLinea
						+ ' fue creada exitosamente.');
				document.location.href = contexPath + "/consultaLineas";
				break;
			case 409:
				$('#nuevaLinea').modal('hide');
				$('p.pMensaje').text(
						'Ya existe una línea con nombre público  '
								+ nombreLinea);
				$('.toast').toast('show');
				break;
			case 500:
				alert('El status es. 500');
				break;
			default:
				alert('El status es otro');
				break;
			}
		},
		error : function(e) {
			alert('Error Interno: ' + e);
		}
	});
}

function modificarLinea() {
	var id = $("#editLineaID").val();
	var nombreLinea = $('#editNombrePublico').val();
	var tarifa = $('#editTrifaSelect').val();
	var fechaPwDesde = $('#editarFechaPWebDesde').val();
	var fechaPwHasta = $('#editarFechaPWebHasta').val();
	var subsistema = $('#editSubsistemaSelect').val();
	var tipoLinea = $('#editTipoLineaSelect').val();
	var fechaVigencia = $('#editarFechaVigencia').val();
	var fechaBaja = $('#editarFechaBaja').val();
	var contexPath = $('#path').val();
	var error = false;
	if (Date.parse(fechaVigencia) > Date.parse(fechaPwDesde)) {
		$("#errorFechaPWDesde")
				.text(
						'La fecha publicable desde debe ser mayor o igual a la fecha de vigencia');
		$('#editarFechaPWebDesde').addClass('is-invalid');
		error = true;
	} else {
		$("#errorFechaPWDesde").text('');
		$('#editarFechaPWebDesde').removeClass('is-invalid');
	}
	if (fechaPwHasta !== null) {
		$('#editarFechaPWebHasta').removeClass('is-invalid');
		if (Date.parse(fechaPwDesde) > Date.parse(fechaPwHasta)) {
			$("#errorFechaPWHasta").text(
					'La fecha hasta debe ser mayor que desde');
			$('#editarFechaPWebHasta').addClass('is-invalid');
			error = true;
		} else {
			$("#errorFechaPWHasta").text('');
		}
	}
	if (fechaBaja !== null) {
		$('#editarFechaBaja').removeClass('is-invalid');
		if (Date.parse(fechaVigencia) > Date.parse(fechaBaja)) {
			$("#errorFechaBaja").text(
					'La fecha de baja debe ser mayor que la vigencia');
			$('#editarFechaBaja').addClass('is-invalid');
			error = true;
		} else {
			$("#errorFechaBaja").text('');
		}
	}
	if (error) {
		event.preventDefault();
	} else {
		$('#editarLinea').modal('hide');
		window.localStorage.setItem('mensaje', 'Línea ' + nombreLinea
				+ ' fue editada exitosamente.');
	}
}

function bajaLinea() {
	var fechaBaja = $('#deleteFechaBaja').val();
	var hoy = new Date();
	baja = new Date(fechaBaja);
	baja.setDate(baja.getDate() + 1);
	console.log(hoy + ' ' + baja);
	if (hoy > baja) {
		$("#errorDeleteFechaBaja").text(
				'La fecha de baja debe ser igual o posterior al día actual');
		$("#deleteFechaBaja").addClass('is-invalid');
		event.preventDefault();
	} else {
		$("#errorDeleteFechaBaja").text('');
		$("#deleteFechaBaja").removeClass('is-invalid');
		$('#bajaLineaForm').modal('hide');
		window.localStorage.setItem('mensaje',
				'La fecha de baja fue actualizada correctamente.');
	}
}
/*
 * function listarLineas() { var contexPath = $('#path').val(); $ .ajax({ type :
 * "GET", url : contexPath + "/servicio/linea/all", success : function(data,
 * statusText, xhr) { $(function() { var tablaLineas = $('tbody.tablaLineas');
 * tablaLineas.empty(); $ .each( data, function(i, item) { var tr = '<tr>' + '<td>' +
 * item.nombre + '</td>' + '<td>' + item.id + '</td>' + '<td>' +
 * item.subsistema.descripcion + '</td>' + '<td>' +
 * item.tipoLinea.descripcion + '</td>' + '<td>' + item.tarifaXKm + '</td>' + '<td>' +
 * item.fechaVigencia + '</td>' + '<td>' + item.publicableWebDesde + '</td>' + '<td>' +
 * item.publicableWebHasta + '</td>' + '<td>' + item.fechaBaja + '</td>' + '<td><a
 * class="btn btn-outline-primary" role="button" href="' + contexPath +
 * '/admin/entidad/' + item.id + '">View</a></td>' + '<td> <button
 * type="button" class="btn btn-primary" data-toggle="modal"
 * data-target="#editarLinea" th:attrappend="data-target=${' + item.id + '}"
 * title="Editar línea">Modificar</button></td>'; +'<td> <button
 * type="button" class="btn btn-primary" data-toggle="modal"
 * data-target="#bajaLinea" th:attrappend="data-target=${' + item.id + '}"
 * title="Baja línea">Baja</button></td></tr>'; tablaLineas.append(tr); });
 * }); }, error : function(e) { alert('Error en el get líneas ' + e); } }); }
 */
/*
 * $.ajax({ type : "POST", url : contexPath + "/servicio/linea/editar", data: {
 * editLineaID: id, editNombrePublico: nombreLinea, editarFechaPWebDesde:
 * fechaPwDesde, editarFechaPWebHasta: fechaPwHasta, editSubsistemaSelect:
 * subsistema, editTipoLineaSelect: tipoLinea, editarFechaVigencia:
 * fechaVigencia }, success : function(data,statusText, xhr) { var status =
 * data.status; switch (status) { case 200: $('#editarLinea').modal('hide');
 * window.localStorage.setItem('mensaje','Línea ' + editNombrePublico + ' fue
 * editada exitosamente.'); document.location.href = contexPath +
 * "/consultaLineas"; break; case 500: alert('Ocurrió un error interno ' +
 * statusText); break; default: alert('Ocurrió un error interno ' + statusText);
 * break; } }, error : function(e){ alert('Error Interno: ' + e); } });
 */
