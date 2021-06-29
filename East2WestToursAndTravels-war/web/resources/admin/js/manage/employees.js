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
$('#admin').prop("checked", false);
// Catch closed modal event
$("#btnCloseChange").click(function () {
    $('#btnSaveChange').hide();
    $('#passfield').show();
    $('#userfield').show();
    $('#modalTitle').text("New Employee");

    $('#username').val("");
    $('#password').val("");
    $('#firstname').val("");
    $('#lastname').val("");
    $("input:radio[value='true']").prop('checked', true);
    $('#birthdate').val("");
    $('#email').val("");
    $('#phone').val("");
    $('#address').val("");
    $('#status').prop("checked", true);
    $('#admin').prop("checked", false);
    $('#customFile').val("");

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
function editObject(id, username, firstname, lastname, gender, birthdate, email, phone, address, status, admin) {
    $('#passfield').hide();
    $('#userfield').hide();
    $('#username').val(username);
    $('#customerID').val(id);
    $('#firstname').val(firstname);
    $('#lastname').val(lastname);
    if (gender) {
        $("input:radio[value='true']").prop('checked', true);
    } else {
        $("input:radio[value='false']").prop('checked', true);
    }

    $('#birthdate').val(convertDate(birthdate));
    $('#email').val(email);
    $('#phone').val(phone);
    $('#address').val(address);
    $('#status').prop("checked", status);
    $('#admin').prop("checked", admin);
    $('#customFile').val("");

    $('#newItem').modal();
    $('#modalTitle').text("Edit The Employee");
    $('#btnSaveChange').show();
    $('#btnAddNew').hide();
}

// Detail object
function detailObject(id, username, firstname, lastname, gender, birthdate, email, phone, address, avatar, point, status, admin) {
    $('#dtID').val(id);
    $('#dtUsername').val(username);
    $('#dtFirstname').val(firstname);
    $('#dtLastname').val(lastname);
    if (gender === true) {
        $('#dtGender').val('Male');
    } else {
        $('#dtGender').val('Female');
    }
    $('#dtBirthdate').val(convertDate(birthdate));
    $('#dtEmail').val(email);
    $('#dtPhone').val(phone);
    $('#dtAddress').val(address);
    $('#dtStatus').prop("checked", status);
    $('#dtAdmin').prop("checked", admin);
    $('#dtPoint').val(point);
    $('#dtAvatar').attr('src', "/East2WestToursAndTravels-war/uploads/imgEmployees/" + avatar);

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
if (err !== "") {
    $('#newItem').modal();
}