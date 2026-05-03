/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

function eliminarAutor(btn) {
  const id = btn.dataset.id;
  confirmarEliminacion("/autores/eliminar/" + id);
}
