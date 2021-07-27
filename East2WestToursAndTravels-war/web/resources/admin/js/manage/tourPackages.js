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
    $('#modalTitle').text("New Tour Package");
    $('#packageName').val("");
    MyEditor.setData("");
    $('#packageStatus').prop("checked", true);
    $('#btnAddNew').show();
});

$('#newItem').on('hidden.bs.modal', function (e) {
    $('#btnSaveChange').hide();
    $('#modalTitle').text("New Tour Package");
    $('#packageName').val("");
    MyEditor.setData("");
    $('#packageStatus').prop("checked", true);
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
function editObject(id, name, description, status) {
    $('#packageID').val(id);
    $('#packageName').val(name);
    MyEditor.setData(description);
    if(status == 1){
        $('#packageStatus').prop("checked", true);
    } else {
        $('#packageStatus').prop("checked", false);
    }
    $('#newItem').modal();
    $('#modalTitle').text("Edit The Tour Package");
    $('#btnSaveChange').show();
    $('#btnAddNew').hide();
}

// Detail object
function detailObject(id, name, description, status) {
    $('#dtID').val(id);
    $('#dtName').val(name);
    DetailEditor.setData(description);
    if(status == 1){
        $('#dtStatus').prop("checked", true);
    } else {
        $('#dtStatus').prop("checked", false);
    }
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