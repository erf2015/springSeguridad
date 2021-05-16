const urlApi = "http://10.191.9.23:8081/Seguridad/jwt/";
let usuarios = [];
let tablaUser = document.getElementById('tabla-body');
let botonesEditar = document.getElementsByClassName('editar');
let botonesEliminar = document.getElementsByClassName('eliminar');
let botonCrearUsuario = document.getElementById('nuevo-usuario');
let botonNuevoUsuario = document.getElementById('enviar-datos');
let modalNuevoUsuario = document.getElementById('exampleModal');

let botonConfirmadoEliminar = document.getElementById("confirmado-eliminar");

let idusuario = document.getElementById("id-usuario");
let nombreUser = document.getElementById("nombre-usuario");
let apellidoUser = document.getElementById("apellido-usuario");
let descripcionUser = document.getElementById("descripcion");
let emailUser = document.getElementById("email-usuario");
let passUnoUser = document.getElementById("password1");
let passDosUser = document.getElementById("password2");
let titleModal = document.getElementById("titleModal");
let editarUsuario = false;

function listarUsuarios(){
	fetch(urlApi+"usuarios")
	.then(response => response.json())
	.then(users => {
	 usuarios = users;
	 pintarListadoUsuarios();
	});
}



function pintarListadoUsuarios(){
    const tuplasUsuarios = usuarios.map( (user,indice) => {
	return `<tr><td>${user.nombre}</td><td>${user.apellido}</td><td>${user.descripcion ? user.descripcion : '' }</td><td>${user.fechaAlta}</td><td>${user.fechaBaja ? user.fechaBaja : 'Activo' }</td><td>${user.username}</td><td>${user.enabled}</td>
	<td>
	<button type="button" class="btn btn-primary editar" data-bs-toggle="modal" data-bs-target="#exampleModal" data-indice=${indice}>Editar</button>
	</td>
	<td>
	<button type="button" class="btn btn-primary eliminar" data-indice=${indice} data-bs-toggle="modal" data-bs-target="#staticBackdrop">Eliminar</button>
	</td>
	
	</tr>`;
	}).join("");
        tablaUser.innerHTML = tuplasUsuarios;	
    	botonesEditar = document.getElementsByClassName('editar');
	Array.from(botonesEditar).forEach(boton => {
	  boton.onclick = editarModal;	
	});
	botonesEliminar = document.getElementsByClassName('eliminar');
	Array.from(botonesEliminar).forEach(boton => {
	  boton.onclick = modalBaja;
	});	
}

function limpiarModal(){
 editarUsuario = false;
 titleModal = document.getElementById("titleModal");
 titleModal.innerHTML = 'Complete los datos del nuevo usuario:';
 nombreUser.value = '';
 apellidoUser.value = '';
 descripcionUser.value = '';
 emailUser.value = '';
 passUnoUser.value = '';
 passDosUser.value = '';
 idusuario.value = '';
 botonNuevoUsuario.onclick = altaUsuario;
}

function editarModal(e){
  editarUsuario = true;	 
  idusuario = e.target.dataset.indice;
  var user = usuarios[idusuario];
  titleModal = document.getElementById("titleModal");
  titleModal.innerHTML = `Edite los datos del usuario:${user.nombre}`;  
  nombreUser.value = user.nombre;
  apellidoUser.value = user.apellido;
  descripcionUser.value = user.descripcion ? user.descripcion : '' ;
  emailUser.value = user.username;
  botonNuevoUsuario.onclick = altaUsuario;
}

function modalBaja(e){
  idusuario = e.target.dataset.indice;
  var user = usuarios[idusuario];
  document.getElementById("titleEliminarModal").innerHTML = `Eliminar el usuario: ${user.nombre}`;  
  document.getElementById("texto-eliminar").innerHTML = "Confirme que desea eliminar al usuario.";	
}

function altaUsuario(){	
   if(!editarUsuario && passDosUser.value !== passUnoUser.value){
	alert('Las contrasennas no coinciden.');
   	return; 
   }
   let user = {
        username: emailUser.value,
        nombre: nombreUser.value,
	apellido: apellidoUser.value,
	password: passUnoUser.value
   }
   let urlAction = null;
   let method = null;
   if(editarUsuario){
   	urlAction = urlApi + "edit";
   	method = 'PUT';
   	user.password = '123456789';
   }else{
      	urlAction = urlApi + "add";
   	method = 'POST';
   }
   fetch(urlAction,{
   	method,
   	headers: {
   	'Content-Type': 'application/json'	
   	},
   	body: JSON.stringify(user)
   })
   .then((response) => response.json())
   .then((data) => {
   	console.log(data);
   	var status = data.status;
   	if(status == '200'){
   		document.getElementById("cerrar-creacion").click(); 
		listarUsuarios();   	
   	}else if(status == '400' || status == '204'){
   		alert(data.datos);
   	} else if(status == "BAD_REQUEST"){
   		alert(data.mensaje);
   	}else{
	   	alert('Ocurre un error no controlado');
   	}
   });
}

function bajaUsuario(){
	let user = usuarios[idusuario];
	const urlDelete = urlApi + "delete/"+user.id;
	fetch(urlDelete,{
   		method : 'DELETE'
   	})  	
   	.then((response) => response.json())
	.then((data) => {
   	 var status = data.status;
   	 if(status == '204'){
		listarUsuarios();   	
   	 }else if(status == '400'){
   		alert(data.datos);
   	 }else if(status == '404'){
   		alert('Error interno');
   	 }else {
   		alert('Ocurre un error no controlado');
   	 }
   });
}


listarUsuarios();

botonCrearUsuario.onclick = limpiarModal;
botonConfirmadoEliminar.onclick = bajaUsuario;




