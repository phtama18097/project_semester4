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
    $('#modalTitle').text("New Accommodation");
    $('#resName').val("");
    MyEditor.setData("");
    $('#minPrice').val("");
    $('#maxPrice').val("");
    $('#location').val("");
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
function editObject(id, name, description, minp, maxp, location, thumbnail, town) {
    $('#restaurantID').val(id);
    $('#resName').val(name);
    MyEditor.setData(description);
    $('#minPrice').val(minp);
    $('#maxPrice').val(maxp);
    $('#location').val(location);
    $('select').val(town);
    
    $('#newItem').modal();
    $('#modalTitle').text("Edit The Accommodation");
    $('#btnSaveChange').show();
    $('#btnAddNew').hide();
}

// Detail object
function detailObject(id, name, description, minp, maxp, location, thumbnail, town) {
    $('#dtID').val(id);
    $('#dtName').val(name);
    DetailEditor.setData(description);
    $('#dtMin').val(minp);
    $('#dtMax').val(maxp);
    $('#dtLocation').val(location);
    $('#dtTown').val(town);
    $('#dtThumbnail').attr('src', "/East2WestToursAndTravels-war/uploads/imgAccommodations/"+thumbnail);
    
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