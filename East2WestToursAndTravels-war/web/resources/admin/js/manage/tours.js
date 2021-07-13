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
    $('#modalTitle').text("New Tour");
    $('#tourName').val("");
    $('select').val(0);
    $('#unitPrice').val("");
    $('#shortDescription').val("");
    $('#departure').val("");
    $('#returnDate').val("");
    $('#minQty').val("");
    $('#maxQty').val("");
    MyEditor.setData("");
    $('#tourStatus').prop("checked", true);
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
//convert date
function convertDate(str) {
  var date = new Date(str),
    mnth = ("0" + (date.getMonth() + 1)).slice(-2),
    day = ("0" + date.getDate()).slice(-2);
  return [date.getFullYear(), mnth, day].join("-");
}
// Edit object
function editObject(id, name, pkgID, uPrice, sDescription, description, dDate, rDate, minQty, maxQty, status) {
    $('#tourID').val(id);
    $('#tourName').val(name);
    $('#unitPrice').val(uPrice);
    $('#shortDescription').val(sDescription);
    $('#departure').val(convertDate(dDate));
    $('#returnDate').val(convertDate(rDate));
    $('#minQty').val(minQty);
    $('#maxQty').val(maxQty);
    $('select').val(pkgID);
    MyEditor.setData(description);
    if(status == 1){
        $('#tourStatus').prop("checked", true);
    } else {
        $('#tourStatus').prop("checked", false);
    }
    $('#newItem').modal();
    $('#modalTitle').text("Edit The Tour");
    $('#btnSaveChange').show();
    $('#btnAddNew').hide();
}

// Detail object
function detailObject(id, name, pkg, uPrice, sDescription, description, dDate, rDate, minQty, maxQty, status) {
    $('#dtID').val(id);
    $('#dtName').val(name);
    $('#dtPrice').val(uPrice);
    $('#dtShort').val(sDescription);
    $('#dtDeparture').val(convertDate(dDate));
    $('#dtReturn').val(convertDate(rDate));
    $('#dtMin').val(minQty);
    $('#dtMax').val(maxQty);
    $('#dtPackage').val(pkg);
    DetailEditor.setData(description);
    if(status == 1){
        $('#dtStatus').prop("checked", true);
    } else {
        $('#dtStatus').prop("checked", false);
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