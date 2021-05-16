//Tema promesas
//Ejemplos
const ejecutorPromesa = (indice) => new Promise((resuelta,fallida) => { 
const r = Math.floor(Math.random() * 10000) + 1000; 
const f = Math.floor(Math.random() * 10000) + 1000;
//console.log("EL tiempo de resuelta es " + r + " el tiempo de fallida es " +f);
setTimeout(() => {
/* 
resuelta( () => {
 	document.getElementById(indice).checked = true;
 	document.getElementById(indice+"class").text = "Esta es la promesa con indice" +indice + " se cumple";
 	return `la promesa con id ${indice} qued[o resuelta.`;
 	}); 
 */
 resuelta(`la promesa con id ${indice} qued[o resuelta.`);
 } , r); 
setTimeout(() => { 
/*
 fallida(() => {
	document.getElementById(indice+"class").innerHTML = "Esta es la promesa con indice" +indice +" falla";
 	return `la promes con ${indice} qued[o fallida.`
 	});
 	*/
	fallida(`la promes con ${indice} qued[o fallida.`);	
 } , f); 

}); 
 
let listado = [];

for(let i = 0 ; i < 10 ; i++){
  listado = [...listado,ejecutorPromesa(i)];
} 
/* Ejecuta todas
listado.forEach((promesa) => {
	promesa.then(
	resuelta => resuelta(),
//	fallida => fallida()
	).catch(fallida => fallida()) ;   	
});
*/
/* Si una falla se corta la ejecuci[on, si llegaran a cumplirse todas retorna un listado ordenado de objectos json
Promise.all(listado).then(
	resuelta => resuelta(),
	fallida => fallida()
);
*/
/* Tuve que hacer un cambio arriba pero esta ejecuta hasta el final y retorna el listado de obj json del resultado de ejecuci[on de cada promesa.
Promise.allSettled(listado).then(
(resuelta => console.log(resuelta))
).catch(fallida => console.log(fallida));
*/
 //Ejecuta todas y solo retorna la primera que se complete sea ok o fallo y termina
// Promise.race(listado).then((exito => console.log(exito))).catch(fallo => console.log(fallo));
 
