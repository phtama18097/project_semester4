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
// CKEditor
let MyEditor;
ClassicEditor
  .create(document.querySelector('#txaDescription'))
  .then(editor => {
    window.editor = editor;
    MyEditor = editor;
});

let DetailEditor;
ClassicEditor
  .create(document.querySelector('#dtDescription'))
  .then(editor => {
    window.editor = editor;
    editor.isReadOnly = true;
    DetailEditor = editor;
});
// Hide save button
$('#btnSaveChange').hide();
// Catch closed modal event
$("#btnCloseChange").click(function () {
    $('#btnSaveChange').hide();
    $('#modalTitle').text("New Cars");
    $('#carName').val("");
    $('#plate').val("");
    $('select').val(0);
    $('#unitPrice').val("");
    $('#shortDescription').val("");
    MyEditor.setData("");
    $('#customFile').val("");
    $('#btnAddNew').show();
});

$('#newItem').on('hidden.bs.modal', function (e) {
    $("#btnCloseChange").click();
});

function editImages(id){
    $('#btnImages-' + id).click();
}

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
function editObject(id, name, plate, type, model, uPrice, sDescription, description, thumbnail, status) {
    $('#carID').val(id);
    $('#carName').val(name);
    $('#plate').val(plate);
    $('#unitPrice').val(uPrice);
    $('#shortDescription').val(sDescription);
    $('#slType').val(type);
    $('#slModel').val(model);
    MyEditor.setData(description);
    $('#slStatus').val(status);
    
    $('#newItem').modal();
    $('#modalTitle').text("Edit The Car");
    $('#btnSaveChange').show();
    $('#btnAddNew').hide();
}

// Detail object
function detailObject(id, name, plate, type, model, uPrice, sDescription, description, thumbnail, status) {
    $('#dtID').val(id);
    $('#dtName').val(name);
    $('#dtPlate').val(plate);
    $('#dtType').val(type);
    $('#dtModel').val(model);
    $('#dtPrice').val(uPrice);
    $('#dtShort').val(sDescription);
    DetailEditor.setData(description);
    $('#dtThumbnail').attr('src', "/East2WestToursAndTravels-war/uploads/imgCars/" + thumbnail);
    if(status === 0){
        $('#dtStatus').val("The car is BROKEN");
    }else if(status === 1){
        $('#dtStatus').val("The car is AVAILABLE");
    }else if(status === 2){
        $('#dtStatus').val("The car is RENTED");
    }
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