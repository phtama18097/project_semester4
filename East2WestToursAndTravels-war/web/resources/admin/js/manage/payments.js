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
    $('#modalTitle').text("New Payment Method");
    $('#paymentName').val("");
    $('#paymentStatus').prop("checked", true);
    $('#btnAddNew').show();
});

$('#newItem').on('hidden.bs.modal', function (e) {
    $('#btnSaveChange').hide();
    $('#modalTitle').text("New Payment Method");
    $('#paymentName').val("");
    $('#paymentStatus').prop("checked", true);
    $('#btnAddNew').show();
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
function editObject(id, name, status) {
    $('#paymentID').val(id);
    $('#paymentName').val(name);
    $('#paymentStatus').prop("checked", status);
    $('#newItem').modal();
    $('#modalTitle').text("Edit The Payment Method");
    $('#btnSaveChange').show();
    $('#btnAddNew').hide();
}

// Detail object
function detailObject(id, name, status) {
    $('#dtID').val(id);
    $('#dtName').val(name);
    $('#dtStatus').prop("checked", status);
    $('#detailModal').modal();
}

// Catch 'N' Key of user to open modal
$(document).ready(function () {
    $(window).keydown(function (event) {
        if (event.keyCode === 78) {
            $('#newItem').modal();
        }
    });
});
var err = $('#javax_faces_developmentstage_messages').text();
if(err !== ""){
    $('#newItem').modal();
}