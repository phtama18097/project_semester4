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

// Detail object
function detailObject(id, name, type, message) {
    $('#dtID').val(id);
    $('#dtCustomer').val(name);
    $('#dtType').val(type);
    $('#dtMessage').val(message);
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