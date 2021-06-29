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
    $('#modalTitle').text("New Destination");
    $('#name').val("");
    MyEditor.setData("");
    $('#customFile').val("");
    $('select').val(0);
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
function editObject(id, name, description, type, thumbnail, town) {
    $('#destinationID').val(id);
    $('#name').val(name);
    MyEditor.setData(description);
    $('#slType').val(type);
    $('#slTown').val(town);
    
    $('#newItem').modal();
    $('#modalTitle').text("Edit The Destination");
    $('#btnSaveChange').show();
    $('#btnAddNew').hide();
}

// Detail object
function detailObject(id, name, description, type, thumbnail, town) {
    $('#dtID').val(id);
    $('#dtName').val(name);
    DetailEditor.setData(description);
    $('#dtType').val(type);
    $('#dtTown').val(town);
    $('#dtThumbnail').attr('src', "/East2WestToursAndTravels-war/uploads/imgDestinations/"+thumbnail);
    
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