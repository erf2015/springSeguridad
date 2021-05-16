//Tema Async y Await
// se utiliza para diferencia las funciones, este t[ermino del lenguaje permite que se usen dentro de las funciones la opci[on await, la cual permite, detener el programa hasta la culminaci[on de la funci[on que esta a continuaci[on de esta palabra. 
//Ejemplo:

const promesa = new Promise( (exito,fallo) =>  {
	const tExito = Math.floor(Math.random() * 10000) +1000;
	const tFallo = Math.floor(Math.random() * 10000) +1000;
	setTimeout(() => {
		exito("Ejecuci[on completa");		
	},tExito);
	setTimeout(() => {
		fallo("Ejecuci[on fallida");		
	},tFallo);
});
//la palabra await solo se puede usar en funciones de tipo async, sino dan error por usarla
//el console no se va a mostrar hasta que no termine la ejecuci[on de lo que esta a la derecha de await
//si no se pone corre completo sin esperar el resultado de la operaci[on.
async function sincrona(){
   console.log("Arranca a: "+new Date());		
   const ejecutar = await promesa;
   console.log(ejecutar);
   console.log("Termina a: "+new Date());		
}

//var http = new XMLHttpRequest();




