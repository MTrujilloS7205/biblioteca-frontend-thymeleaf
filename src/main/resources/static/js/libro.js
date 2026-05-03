/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

function eliminarLibro(btn) {
  const id = btn.dataset.id;
  confirmarEliminacion("/libros/eliminar/" + id);
}

function filtrarPorAutor() {
  const autorId = document.getElementById("filtroAutor").value;

  if (!autorId) {
    alert("Selecciona un autor");
    return;
  }

  window.location.href = "/libros/autor/" + autorId;
}