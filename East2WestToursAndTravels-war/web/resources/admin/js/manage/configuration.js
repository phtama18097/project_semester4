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

// Edit object
function editObject(id, name, fileLocation) {
    $('#configID').val(id);
    $('#name').val(name);
    
    $('#newItem').modal();
    $('#modalTitle').text("Edit The Destination");
    $('#btnSaveChange').show();
    $('#btnAddNew').hide();
}

// Detail object
function detailObject(id, name, fileLocation) {
    $('#dtID').val(id);
    $('#dtName').val(name);

    $('#dtThumbnail').attr('src', "/East2WestToursAndTravels-war/uploads/imgConfigurations/"+fileLocation);
    
    $('#detailModal').modal();
}

var err = $('#javax_faces_developmentstage_messages').text();
if(err !== ""){
    $('#newItem').modal();
}