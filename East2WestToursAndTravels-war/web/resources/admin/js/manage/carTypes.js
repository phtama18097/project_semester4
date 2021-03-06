/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Prvent Users use enter key to submit form
$(document).ready(function () {
    $(window).keydown(function (event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            return false;
        }
    });
});
// Hide save button
$('#btnSaveChange').hide();

// Catch closed modal event
$("#btnCloseChange").click(function () {
    $('#btnSaveChange').hide();
    $('#modalTitle').text("New Car Type");
    $('#typeName').val("");
    $('#seat').val("");
    $('#btnAddNew').show();
});

$('#newItem').on('hidden.bs.modal', function (e) {
    $("#btnCloseChange").click();
});

// Confirm delete object
function confirmDelete(id) {
    Swal.fire({
        title: "Are you sure?",
        text: 'You won"t be able to revert this!',
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes, delete it!",
        cancelButtonText: "No, cancel!",
        reverseButtons: true
    }).then(function (result) {
        if (result.value) {
            $('#btnDelete-' + id).click();
        }
    });
}
// Edit object
function editObject(id, name, seat) {
    $('#typeID').val(id);
    $('#typeName').val(name);
    $('#seat').val(seat);
    $('#newItem').modal();
    $('#modalTitle').text("Edit The Car Type");
    $('#btnSaveChange').show();
    $('#btnAddNew').hide();
}

// Detail object
function detailObject(id, name, seat) {
    $('#dtID').val(id);
    $('#dtName').val(name);
     $('#dtSeat').val(seat);
    $('#detailModal').modal();
}

// Catch 'Ctrl + I' Key of user to open modal
$(window).keydown(function (e) {
    if (e.which === 17)
        $(window).bind('keydown.ctrlI', function (e) {
            if (e.which === 73) {
                e.preventDefault();
                $('#newItem').modal();
            }
        });
});
var err = $('#javax_faces_developmentstage_messages').text();
if(err !== ""){
    $('#newItem').modal();
}